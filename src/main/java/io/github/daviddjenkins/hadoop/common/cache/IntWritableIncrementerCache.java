package io.github.daviddjenkins.hadoop.common.cache;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Extends the {@link io.github.daviddjenkins.hadoop.common.cache.AbstractAccumulatorMapCache}
 * to increment the int value in the cache
 *
 * @param <KeyType> Type of the key to store
 * @author David D. Jenkins
 */
public class IntWritableIncrementerCache<KeyType extends WritableComparable> extends AbstractAccumulatorMapCache<KeyType, IntWritable> {

    /**
     * Initializes the cache
     *
     * @param kcls         Key class that extends WritableComparable
     * @param context      Mapper.Context that allows for updating counters and
     *                     writing results
     * @param maxCacheSize integer saying how large the cache can be before
     *                     writing the results
     */
    public IntWritableIncrementerCache(Class kcls, Mapper.Context context, int maxCacheSize) throws IllegalAccessException, InstantiationException {
        super(kcls, IntWritable.class, context, maxCacheSize);
    }

    /**
     * Increments the value by one
     *
     * @param key         The key of the value being accumlated
     * @param value       The new value to insert
     * @param storedValue The value that's already stored in the cache
     */
    @Override
    protected void accumulate(final KeyType key, final IntWritable value, final IntWritable storedValue) {
        storedValue.set(storedValue.get() + 1);
    }

}
