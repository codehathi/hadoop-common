package io.github.daviddjenkins.hadoop.common.cache;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Extends the {@link io.github.daviddjenkins.hadoop.common.cache.AbstractAccumulatorMapCache}
 * to increment the long value in the cache
 *
 * @param <KeyType> Type of the key to store
 * @author David D. Jenkins
 */
public class LongWritableIncrementerCache<KeyType extends WritableComparable> extends AbstractAccumulatorMapCache<KeyType, LongWritable> {

    /**
     * Initializes the cache
     *
     * @param kcls         Key class that extends WritableComparable
     * @param context      Mapper.Context that allows for updating counters and
     *                     writing results
     * @param maxCacheSize integer saying how large the cache can be before
     *                     writing the results
     */
    public LongWritableIncrementerCache(Class kcls, Mapper.Context context, int maxCacheSize) throws IllegalAccessException, InstantiationException {
        super(kcls, LongWritable.class, context, maxCacheSize);
    }

    /**
     * Increments the value by one
     *
     * @param key         The key of the value being accumlated
     * @param value       The new value to insert
     * @param storedValue The value that's already stored in the cache
     */
    @Override
    protected void accumulate(final KeyType key, final LongWritable value, final LongWritable storedValue) {
        storedValue.set(storedValue.get() + 1);
    }

}
