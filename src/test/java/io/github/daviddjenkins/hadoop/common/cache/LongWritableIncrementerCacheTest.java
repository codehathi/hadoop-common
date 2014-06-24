package io.github.daviddjenkins.hadoop.common.cache;

import org.junit.Assert;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class LongWritableIncrementerCacheTest {
    MapDriver<LongWritable, LongWritable, LongWritable, LongWritable> mapDriver;
    ReduceDriver<LongWritable, LongWritable, LongWritable, LongWritable> reduceDriver;
    MapReduceDriver<LongWritable, LongWritable, LongWritable, LongWritable, LongWritable, LongWritable> mapReduceDriver;


    public class TestMapper extends Mapper<LongWritable, LongWritable, LongWritable, LongWritable> {
        LongWritableIncrementerCache<LongWritable> cache;
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);

            try {
                cache = new LongWritableIncrementerCache<LongWritable>(LongWritable.class, context, 3);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        protected void map(LongWritable key, LongWritable value, Context context)
                throws java.io.IOException, InterruptedException {

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

        LongWritableIncrementerCache<LongWritable> cache = new LongWritableIncrementerCache<LongWritable>(LongWritable.class, context, 3);

        boolean ret;

        ret = cache.insert(new LongWritable(1), new LongWritable(1));
        ret |= cache.insert(new LongWritable(1), new LongWritable(1));
        ret |= cache.insert(new LongWritable(2), new LongWritable(1));
        ret |= cache.insert(new LongWritable(3), new LongWritable(1));

        Assert.assertEquals(false, ret);
        Assert.assertEquals(3, cache.size());

        ret |= cache.insert(new LongWritable(4), new LongWritable(1));

        Assert.assertEquals(true, ret);
        Assert.assertEquals(1, cache.size());

        cache.flush();
        Assert.assertEquals(0, cache.size());
    }

    @Test
    public void testMapper() throws IOException {
        mapDriver.withInput(new LongWritable(1), new LongWritable(1));
        mapDriver.withInput(new LongWritable(1), new LongWritable(1));
        mapDriver.withInput(new LongWritable(2), new LongWritable(1));
        mapDriver.withInput(new LongWritable(3), new LongWritable(1));
        mapDriver.withInput(new LongWritable(4), new LongWritable(1));

        mapDriver.withOutput(new LongWritable(1), new LongWritable(2));
        mapDriver.withOutput(new LongWritable(2), new LongWritable(1));
        mapDriver.withOutput(new LongWritable(3), new LongWritable(1));
        mapDriver.withOutput(new LongWritable(4), new LongWritable(1));

        mapDriver.runTest();
    }
}
