hadoop-common
=============

Some code that I use when developing hadoop applications

Below is a list of what's available
* AbstractAccumulatorMapCache - Map side cache.  Extend this to implement how the cache is to be updated when an entry already exists.  Automatically writes out the cache when it gets too big.
..* LongWritableIncrementerCache - Stores a LongWritable value and extends AbstractAccumulatorMapCache to update the cache entry by 1.
..* IntWritableIncrementerCache - Same as the LongWritableIncrementerCache except it stores an IntWritable