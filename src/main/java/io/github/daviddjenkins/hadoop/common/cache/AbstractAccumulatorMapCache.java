package io.github.daviddjenkins.hadoop.common.cache;

import io.github.daviddjenkins.common.ObjectFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.TaskInputOutputContext;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class serves to provide a class that can be used as a mapper side cache.
 * This serves to reduce the amount of stuff that is written out resulting in
 * less to sort into the reduce phase.  You can think of this as a reducer in
 * the mapper.
 * <p/>
 * To use, create a class that extends this one to implement the accumulate
 * function. This provides the ability to update the value where the key is
 * already in the cache.
 *
 * @param <KeyType>   Type of the key to store
 * @param <ValueType> Type of the value to store
 * @author David D. Jenkins
 * @see io.github.daviddjenkins.hadoop.common.cache.LongWritableIncrementerCache
 */
public abstract class AbstractAccumulatorMapCache<KeyType extends WritableComparable, ValueType extends Writable> {
    private Map<KeyType, ValueType> cache = new HashMap<KeyType, ValueType>();
    private ObjectFactory<KeyType> keyObjectFactory;
    private ObjectFactory<ValueType> valueObjectFactory;

    private Class<KeyType> keyClass;
    private Class<ValueType> valueClass;

    private Configuration conf;
    private TaskInputOutputContext context = null;

    private int maxCacheSize = 0;

    private static final String CACHE_COUNTER_GROUP = "Map Cache";

    /**
     * @param kcls         Key class that extends WritableComparable
     * @param vcls         Value class that extends Writable
     * @param context      Mapper.Context that allows for updating counters and
     *                     writing results
     * @param maxCacheSize integer saying how large the cache can be before
     *                     writing the results
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public AbstractAccumulatorMapCache(Class<KeyType> kcls, Class<ValueType> vcls, Mapper.Context context, int maxCacheSize) throws IllegalAccessException, InstantiationException {
        this.keyClass = kcls;
        this.valueClass = vcls;
        this.context = context;
        this.maxCacheSize = maxCacheSize;
        this.conf = context.getConfiguration();

        // Create new object factories for the keys and values
        keyObjectFactory = new ObjectFactory<KeyType>(keyClass);
        valueObjectFactory = new ObjectFactory<ValueType>(valueClass);

    }

    /**
     * Writes the data from cache then empties it.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void flush() throws IOException, InterruptedException {
        for (Map.Entry<KeyType, ValueType> e : cache.entrySet()) {
            context.write(e.getKey(), e.getValue());
        }
        context.getCounter(CACHE_COUNTER_GROUP, "Flushed cache").increment(1);
        clear();
    }

    /**
     * Empties the cache without writing out the results
     */
    public void clear() {
        cache.clear();
        keyObjectFactory.reclaimAll();
        valueObjectFactory.reclaimAll();
    }

    /**
     * Inserts a key and value into the cache.  If the key already exists,
     * the accumulate function is called.
     *
     * @param key   Key to insert
     * @param value Value to insert
     * @return boolean on whether or not the cache was flushed on insert
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean insert(KeyType key, ValueType value) throws IOException, InterruptedException {
        boolean ret = false;

        ValueType newValue = cache.get(key);

        if (newValue != null) {
            accumulate(key, value, newValue);
        } else {
            if (cache.size() >= maxCacheSize) {
                ret = true;
                flush();
            }
            context.getCounter(CACHE_COUNTER_GROUP, "New to cache").increment(1);
            KeyType newKey = ReflectionUtils.copy(conf, key, keyObjectFactory.getInstance());
            newValue = ReflectionUtils.copy(conf, value, valueObjectFactory.getInstance());

            cache.put(newKey, newValue);
        }
        return ret;
    }

    /**
     * Returns the size of the cache
     *
     * @return size of the cache
     */
    public int size() {
        return this.cache.size();
    }

    /**
     * Abstract method to be defined byt extending class.
     *
     * @param key         The key of the value being accumlated
     * @param value       The new value to insert
     * @param storedValue The value that's already stored in the cache
     */
    protected abstract void accumulate(final KeyType key, final ValueType value, final ValueType storedValue);
}
