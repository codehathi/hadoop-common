package io.github.daviddjenkins.hadoop.common.cache;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class IntWritableIncrementerCacheTest {
    MapDriver<IntWritable, IntWritable, IntWritable, IntWritable> mapDriver;
    ReduceDriver<IntWritable, IntWritable, IntWritable, IntWritable> reduceDriver;
    MapReduceDriver<IntWritable, IntWritable, IntWritable, IntWritable, IntWritable, IntWritable> mapReduceDriver;


    public class TestMapper extends Mapper<IntWritable, IntWritable, IntWritable, IntWritable> {
        IntWritableIncrementerCache<IntWritable> cache;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);

            try {
                cache = new IntWritableIncrementerCache<IntWritable>(IntWritable.class, context, 3);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        protected void map(IntWritable key, IntWritable value, Context context)
                throws IOException, InterruptedException {

            cache.insert(key, value);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
            cache.flush();
        }
    }

    @Before
    public void setUp() {
        TestMapper mapper = new TestMapper();
        Reducer reducer = new Reducer();
        mapDriver = MapDriver.newMapDriver(mapper);;
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testCache() throws InstantiationException, IllegalAccessException, IOException, InterruptedException {
        Mapper.Context context = mapDriver.getContext();

        IntWritableIncrementerCache<IntWritable> cache = new IntWritableIncrementerCache<IntWritable>(IntWritable.class, context, 3);

        boolean ret;

        ret = cache.insert(new IntWritable(1), new IntWritable(1));
        ret |= cache.insert(new IntWritable(1), new IntWritable(1));
        ret |= cache.insert(new IntWritable(2), new IntWritable(1));
        ret |= cache.insert(new IntWritable(3), new IntWritable(1));

        Assert.assertEquals(false, ret);
        Assert.assertEquals(3, cache.size());

        ret |= cache.insert(new IntWritable(4), new IntWritable(1));

        Assert.assertEquals(true, ret);
        Assert.assertEquals(1, cache.size());

        cache.flush();
        Assert.assertEquals(0, cache.size());
    }

    @Test
    public void testMapper() throws IOException {
        mapDriver.withInput(new IntWritable(1), new IntWritable(1));
        mapDriver.withInput(new IntWritable(1), new IntWritable(1));
        mapDriver.withInput(new IntWritable(2), new IntWritable(1));
        mapDriver.withInput(new IntWritable(3), new IntWritable(1));
        mapDriver.withInput(new IntWritable(4), new IntWritable(1));

        mapDriver.withOutput(new IntWritable(1), new IntWritable(2));
        mapDriver.withOutput(new IntWritable(2), new IntWritable(1));
        mapDriver.withOutput(new IntWritable(3), new IntWritable(1));
        mapDriver.withOutput(new IntWritable(4), new IntWritable(1));

        mapDriver.runTest();
    }
}
