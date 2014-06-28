package io.github.daviddjenkins.hadoop.common;

import org.apache.hadoop.io.Text;
import org.junit.Assert;
import org.junit.Test;

public class TextUtilsTest {

    Text[] toArray(Text... obj) {
        return obj;
    }

    @Test
    public void TestEmpty() {
        Text empty_input = new Text("");
        Text blank_input = new Text(" ");
        Text text_bob = new Text("bob");
        Text text_spacebob = new Text("  bob  ");
        Text text_foo = new Text("foo");
        Text text_bar = new Text("bar");


        Assert.assertEquals(true, TextUtils.isEmpty(null));
        Assert.assertEquals(true, TextUtils.isEmpty(empty_input));
        Assert.assertEquals(false, TextUtils.isEmpty(blank_input));
        Assert.assertEquals(false, TextUtils.isEmpty(text_bob));
        Assert.assertEquals(false, TextUtils.isEmpty(text_spacebob));

        Assert.assertEquals(false, TextUtils.isNotEmpty(null));
        Assert.assertEquals(false, TextUtils.isNotEmpty(empty_input));
        Assert.assertEquals(true, TextUtils.isNotEmpty(blank_input));
        Assert.assertEquals(true, TextUtils.isNotEmpty(text_bob));
        Assert.assertEquals(true, TextUtils.isNotEmpty(text_spacebob));

        Assert.assertEquals(true, TextUtils.isAnyEmpty(null));
        Assert.assertEquals(true, TextUtils.isAnyEmpty(toArray(null, text_foo)));
        Assert.assertEquals(true, TextUtils.isAnyEmpty(toArray(empty_input, text_bar)));
        Assert.assertEquals(true, TextUtils.isAnyEmpty(toArray(text_bob, empty_input)));
        Assert.assertEquals(true, TextUtils.isAnyEmpty(toArray(text_spacebob, null)));
        Assert.assertEquals(false, TextUtils.isAnyEmpty(toArray(blank_input, text_bar)));
        Assert.assertEquals(false, TextUtils.isAnyEmpty(toArray(text_foo, text_bar)));

        Assert.assertEquals(false, TextUtils.isNoneEmpty(null));
        Assert.assertEquals(false, TextUtils.isNoneEmpty(toArray(null, text_foo)));
        Assert.assertEquals(false, TextUtils.isNoneEmpty(toArray(empty_input, text_bar)));
        Assert.assertEquals(false, TextUtils.isNoneEmpty(toArray(text_bob, empty_input)));
        Assert.assertEquals(false, TextUtils.isNoneEmpty(toArray(text_spacebob, null)));
        Assert.assertEquals(true, TextUtils.isNoneEmpty(toArray(blank_input, text_bar)));
        Assert.assertEquals(true, TextUtils.isNoneEmpty(toArray(text_foo, text_bar)));
    }

    @Test
    public void TestBlank() {
        Text empty_input = new Text("");
        Text blank_input = new Text(" ");
        Text text_bob = new Text("bob");
        Text text_spacebob = new Text("  bob  ");
        Text text_foo = new Text("foo");
        Text text_bar = new Text("bar");


        Assert.assertEquals(true, TextUtils.isBlank(null));
        Assert.assertEquals(true, TextUtils.isBlank(empty_input));
        Assert.assertEquals(true, TextUtils.isBlank(blank_input));
        Assert.assertEquals(false, TextUtils.isBlank(text_bob));
        Assert.assertEquals(false, TextUtils.isBlank(text_spacebob));

        Assert.assertEquals(false, TextUtils.isNotBlank(null));
        Assert.assertEquals(false, TextUtils.isNotBlank(empty_input));
        Assert.assertEquals(false, TextUtils.isNotBlank(blank_input));
        Assert.assertEquals(true, TextUtils.isNotBlank(text_bob));
        Assert.assertEquals(true, TextUtils.isNotBlank(text_spacebob));

        Assert.assertEquals(true, TextUtils.isAnyBlank(null));
        Assert.assertEquals(true, TextUtils.isAnyBlank(toArray(null, text_foo)));
        Assert.assertEquals(true, TextUtils.isAnyBlank(toArray(empty_input, text_bar)));
        Assert.assertEquals(true, TextUtils.isAnyBlank(toArray(text_bob, empty_input)));
        Assert.assertEquals(true, TextUtils.isAnyBlank(toArray(text_spacebob, null)));
        Assert.assertEquals(true, TextUtils.isAnyBlank(toArray(blank_input, text_bar)));
        Assert.assertEquals(false, TextUtils.isAnyBlank(toArray(text_foo, text_bar)));

        Assert.assertEquals(false, TextUtils.isNoneBlank(null));
        Assert.assertEquals(false, TextUtils.isNoneBlank(toArray(null, text_foo)));
        Assert.assertEquals(false, TextUtils.isNoneBlank(toArray(empty_input, text_bar)));
        Assert.assertEquals(false, TextUtils.isNoneBlank(toArray(text_bob, empty_input)));
        Assert.assertEquals(false, TextUtils.isNoneBlank(toArray(text_spacebob, null)));
        Assert.assertEquals(false, TextUtils.isNoneBlank(toArray(blank_input, text_bar)));
        Assert.assertEquals(true, TextUtils.isNoneBlank(toArray(text_foo, text_bar)));
    }

    @Test
    public void TestTrim() {
        Text empty_input = new Text("");
        Text blank_input = new Text(" ");
        Text text_abc = new Text("abc");
        Text text_spaceabc = new Text("  abc  ");

        Assert.assertEquals(null, TextUtils.trim(null));
        Assert.assertEquals("", TextUtils.trim(empty_input).toString());
        Assert.assertEquals("", TextUtils.trim(blank_input).toString());
        Assert.assertEquals("abc", TextUtils.trim(text_abc).toString());
        Assert.assertEquals("abc", TextUtils.trim(text_spaceabc).toString());
    }

    @Test
    public void TestStrip() {
        Text empty_input = new Text("");
        Text blank_input = new Text(" ");
        Text text_abc = new Text("abc");
        Text text_spaceabc = new Text("  abc  ");

        Assert.assertEquals(null, TextUtils.strip(null));
        Assert.assertEquals("", TextUtils.strip(empty_input).toString());
        Assert.assertEquals("", TextUtils.strip(blank_input).toString());
        Assert.assertEquals("abc", TextUtils.strip(text_abc).toString());
        Assert.assertEquals("abc", TextUtils.strip(text_spaceabc).toString());
    }

    @Test
    public void TestEqualIgnoreCase() {
        Text text_abc = new Text("abc");
        Text text_abc2 = new Text("abc");
        Text text_AbC = new Text("AbC");
        Text text_ABC = new Text("ABC");

        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_abc));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_abc2));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_AbC));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_ABC));
    }

    @Test
    public void TestIndexOf() {
        Text text_abc = new Text("abcabcabc");
        Text text_toFind = new Text("abc");
        Text text_toFind2 = new Text("bcab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");


        Assert.assertEquals(0, TextUtils.indexOf(text_abc, text_abc.getBytes()[0]));
        Assert.assertEquals(3, TextUtils.indexOf(text_abc, text_abc.getBytes()[3], 1));
        Assert.assertEquals(3, TextUtils.indexOf(text_abc, (int) text_abc.getBytes()[3], 1));

        Assert.assertEquals(-1, TextUtils.indexOf(null, text_abc, 100));
        Assert.assertEquals(-1, TextUtils.indexOf(text_abc, null, 100));
        Assert.assertEquals(0, TextUtils.indexOf(text_empty, text_empty, 0));
        Assert.assertEquals(0, TextUtils.indexOf(text_empty, text_empty2, 10));
        Assert.assertEquals(-1, TextUtils.indexOf(text_empty, text_abc, 0));
        Assert.assertEquals(0, TextUtils.indexOf(text_abc, text_toFind));
        Assert.assertEquals(1, TextUtils.indexOf(text_abc, text_toFind2));
        Assert.assertEquals(-1, TextUtils.indexOf(text_abc, text_toFind2, 1000));
        Assert.assertEquals(0, TextUtils.indexOf(text_abc, text_empty, 0));
        Assert.assertEquals(-1, TextUtils.indexOf(null, text_empty, 0));
        Assert.assertEquals(-1, TextUtils.indexOf(null, null, 0));
        Assert.assertEquals(-1, TextUtils.indexOf(text_toFind, text_abc));
    }

    @Test
    public void TestOrdinalIndexOf() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");

        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(null, 'a', 100));
        Assert.assertEquals(1, TextUtils.ordinalIndexOf(text_abc, 'a', 0, 2));
        Assert.assertEquals(7, TextUtils.ordinalIndexOf(text_abc, 'a', 5, 2));
        Assert.assertEquals(5, TextUtils.ordinalIndexOf(text_abc, 'b', 2));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_abc, 'b', 5));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_abc, 'b', 100));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_abc, 'b', -1));

        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(null, text_abc, 100, 1));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_abc, null, 100, 1));
        Assert.assertEquals(0, TextUtils.ordinalIndexOf(text_empty, text_empty, 0, 1));
        Assert.assertEquals(0, TextUtils.ordinalIndexOf(text_empty, text_empty2, 10, 1));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_empty, text_abc, 0, 1));
        Assert.assertEquals(0, TextUtils.ordinalIndexOf(text_abc, text_toFind, 1));
        Assert.assertEquals(1, TextUtils.ordinalIndexOf(text_abc, text_toFind, 2));
        Assert.assertEquals(2, TextUtils.ordinalIndexOf(text_abc, text_toFind2, 1));
        Assert.assertEquals(5, TextUtils.ordinalIndexOf(text_abc, text_toFind2, 2));
        Assert.assertEquals(1, TextUtils.ordinalIndexOf(text_abc, text_toFind3, 1));
        Assert.assertEquals(4, TextUtils.ordinalIndexOf(text_abc, text_toFind3, 2));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_abc, text_toFind2, 1000, -100));

    }

    @Test
    public void TestLastIndexOf() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");


        Assert.assertEquals(-1, TextUtils.lastIndexOf(null, 'a', 100));
        Assert.assertEquals(7, TextUtils.lastIndexOf(text_abc, 'a'));
        Assert.assertEquals(5, TextUtils.lastIndexOf(text_abc, 'b'));
        Assert.assertEquals(5, TextUtils.lastIndexOf(text_abc, 'b', 5));
        Assert.assertEquals(2, TextUtils.lastIndexOf(text_abc, 'b', 4));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(text_abc, 'b', -1));


        Assert.assertEquals(-1, TextUtils.lastIndexOf(null, text_abc, 100));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(text_abc, null, 100));
        Assert.assertEquals(0, TextUtils.lastIndexOf(text_empty, text_empty, 0));
        Assert.assertEquals(0, TextUtils.lastIndexOf(text_empty, text_empty2, 10));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(text_empty, text_abc, 0));
        Assert.assertEquals(7, TextUtils.lastIndexOf(text_abc, text_toFind));
        Assert.assertEquals(5, TextUtils.lastIndexOf(text_abc, text_toFind2));
        Assert.assertEquals(2, TextUtils.lastIndexOf(text_abc, text_toFind2, 4));
        Assert.assertEquals(5, TextUtils.lastIndexOf(text_abc, text_toFind2, 1000));
        Assert.assertEquals(4, TextUtils.lastIndexOf(text_abc, text_toFind3));
        Assert.assertEquals(1, TextUtils.lastIndexOf(text_abc, text_toFind3, 3));
        Assert.assertEquals(8, TextUtils.lastIndexOf(text_abc, text_empty));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(null, text_empty, 0));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(null, null, 0));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(text_toFind, text_abc));
    }

    @Test
    public void TestLastOrdinalIndexOf() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");

        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(null, 'a', 100));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_empty, 'a', 1));
        Assert.assertEquals(3, TextUtils.lastOrdinalIndexOf(text_abc, 'a', 4));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_abc, 'a', 6, 4));
        Assert.assertEquals(2, TextUtils.lastOrdinalIndexOf(text_abc, 'b', 2));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, 'b', 3));
        Assert.assertEquals(5, TextUtils.lastOrdinalIndexOf(text_abc, 'b', 1));
        Assert.assertEquals(2, TextUtils.lastOrdinalIndexOf(text_abc, 'b', 2, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, 'b', 1, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, 'b', 4));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, 'b', -1));

        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(null, text_abc, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, null, 1));
        Assert.assertEquals(0, TextUtils.lastOrdinalIndexOf(text_empty, text_empty, 1));
        Assert.assertEquals(0, TextUtils.lastOrdinalIndexOf(text_empty, text_empty2, 10));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_empty, text_abc, 0));
        Assert.assertEquals(7, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind, 1));
        Assert.assertEquals(5, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind2, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind2, 4));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind2, 1000));
        Assert.assertEquals(4, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind3, 1));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind3, 2));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind3, 2, 1));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind3, 1, 1));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind3, 1, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_abc, text_toFind3, 3));
        Assert.assertEquals(8, TextUtils.lastOrdinalIndexOf(text_abc, text_empty, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(null, text_empty, 0));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(null, null, 0));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_toFind, text_abc, 1));
    }

    @Test
    public void TestContains() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_toFind4 = new Text("abc");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");

        Assert.assertEquals(false, TextUtils.contains(null, text_abc));
        Assert.assertEquals(false, TextUtils.contains(text_abc, (Text) null));
        Assert.assertEquals(true, TextUtils.contains(text_abc, 'a'));
        Assert.assertEquals(true, TextUtils.contains(text_abc, 'b'));
        Assert.assertEquals(false, TextUtils.contains(text_abc, 'c'));
        Assert.assertEquals(false, TextUtils.contains(text_toFind, text_abc));
        Assert.assertEquals(true, TextUtils.contains(text_abc, (int) 'b'));
        Assert.assertEquals(false, TextUtils.contains(text_abc, (int) 'c'));
        Assert.assertEquals(true, TextUtils.contains(text_abc, text_toFind));
        Assert.assertEquals(true, TextUtils.contains(text_abc, text_toFind2));
        Assert.assertEquals(true, TextUtils.contains(text_abc, text_toFind3));
        Assert.assertEquals(false, TextUtils.contains(text_abc, text_toFind4));
        Assert.assertEquals(true, TextUtils.contains(text_abc, text_empty));
        Assert.assertEquals(true, TextUtils.contains(text_empty, text_empty));
        Assert.assertEquals(true, TextUtils.contains(text_empty, text_empty2));
        Assert.assertEquals(true, TextUtils.contains(text_abc, "ab"));
        Assert.assertEquals(false, TextUtils.contains(text_abc, "abc"));
    }

    @Test
    public void TestContainsWhitespace() {
        Text text_toFind3 = new Text("ab");
        Text text_toFind4 = new Text("a bc ");
        Assert.assertEquals(false, TextUtils.containsWhitespace(null));
        Assert.assertEquals(false, TextUtils.containsWhitespace(text_toFind3));
        Assert.assertEquals(true, TextUtils.containsWhitespace(text_toFind4));
    }

    @Test
    public void TestIndexOfAny() {
        Text text_in = new Text("zzabyycdxx");
        Text text_empty = new Text("");
        char[] arr1 = new char[]{'z','a'};
        char[] arr2 = new char[]{'b','y'};
        char[] arr3 = new char[]{'q'};

        Assert.assertEquals(-1, TextUtils.indexOfAny(null, arr1));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_empty, arr1));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, null));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, new char[]{}));
        Assert.assertEquals(0, TextUtils.indexOfAny(text_in, arr1));
        Assert.assertEquals(3, TextUtils.indexOfAny(text_in, arr2));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, arr3));
    }

    @Test
    public void TestContainsAny() {
        Text text_in = new Text("zzabyycdxx");
        Text text_empty = new Text("");
        char[] arr1 = new char[]{'z','a'};
        char[] arr2 = new char[]{'b','y'};
        char[] arr3 = new char[]{'q'};

        Assert.assertEquals(false, TextUtils.containsAny(null, arr1));
        Assert.assertEquals(false, TextUtils.containsAny(text_empty, arr1));
        Assert.assertEquals(false, TextUtils.containsAny(text_in, null));
        Assert.assertEquals(false, TextUtils.containsAny(text_in, new char[]{}));
        Assert.assertEquals(true, TextUtils.containsAny(text_in, arr1));
        Assert.assertEquals(true, TextUtils.containsAny(text_in, arr2));
        Assert.assertEquals(false, TextUtils.containsAny(text_in, arr3));
    }
}
