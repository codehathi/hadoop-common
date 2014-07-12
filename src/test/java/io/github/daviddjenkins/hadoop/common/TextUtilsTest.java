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
        Text text_1 = new Text("\u00c0\u00e0");
        Text text_2 = new Text("\u00e0\u00c0");

        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(null, null));
        Assert.assertEquals(false, TextUtils.equalsIgnoreCase(null, text_2));
        Assert.assertEquals(false, TextUtils.equalsIgnoreCase(text_1, null));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_abc));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_abc2));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_AbC));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_abc, text_ABC));
        Assert.assertEquals(true, TextUtils.equalsIgnoreCase(text_1, text_2));
        Assert.assertEquals(false, TextUtils.equalsIgnoreCase(text_1, text_abc));
    }

    @Test
    public void TestIndexOf() {
        Text text_abc = new Text("abcabcabc");
        Text text_toFind = new Text("abc");
        Text text_toFind2 = new Text("bcab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");

        Text text_ascii = new Text("a");
        Text text_16 = new Text("€");
        Text text_16_2 = new Text("\u0800");
        Text text_asciiAnd16 = new Text("ac€\u069A");
        Text text_16AndAscii = new Text("€a");
        Text text_a = new Text("a");
        Text text_b = new Text("a\u0e32asd\u0e32asdf");

        Assert.assertEquals(-1, TextUtils.indexOf(null, 'a'));
        Assert.assertEquals(0, TextUtils.indexOf(text_a, 'a'));
        Assert.assertEquals(-1, TextUtils.indexOf(null, (int) 'a'));
        Assert.assertEquals(0, TextUtils.indexOf(text_a, (int) 'a'));
        Assert.assertEquals(-1, TextUtils.indexOf(null, (byte) 'a'));
        Assert.assertEquals(0, TextUtils.indexOf(text_a, (byte) 'a'));
        Assert.assertEquals(1, TextUtils.indexOf(text_b, '\u0e32'));
        Assert.assertEquals(1, TextUtils.indexOf(text_b, (int) '\u0e32'));
        Assert.assertEquals(1, TextUtils.indexOf(text_b, 0x0e32, 1));

        Assert.assertEquals(-1, TextUtils.indexOf(null, '\u0e32', 1));
        Assert.assertEquals(1, TextUtils.indexOf(text_b, '\u0e32', 1));
        Assert.assertEquals(7, TextUtils.indexOf(text_b, 0x0e32, 2));
        Assert.assertEquals(1, TextUtils.indexOf(text_b, 0x0e32, -2));
        Assert.assertEquals(-1, TextUtils.indexOf(text_b, 0x0e32, 100));


        Assert.assertEquals(0, TextUtils.indexOf(text_abc, (char) text_abc.getBytes()[0]));
        Assert.assertEquals(3, TextUtils.indexOf(text_abc, (char) text_abc.getBytes()[3], 1));

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

        Assert.assertEquals(-1, TextUtils.indexOf(null, 'a'));
        Assert.assertEquals(0, TextUtils.indexOf(text_ascii, 'a'));
        Assert.assertEquals(-1, TextUtils.indexOf(text_16, 'a'));
        Assert.assertEquals(0, TextUtils.indexOf(text_asciiAnd16, 'a'));
        Assert.assertEquals(3, TextUtils.indexOf(text_16AndAscii, 'a'));

        Assert.assertEquals(0, TextUtils.indexOf(text_16, '€'));
        Assert.assertEquals(5, TextUtils.indexOf(text_asciiAnd16, 'ښ'));
        Assert.assertEquals(0, TextUtils.indexOf(text_16AndAscii, '€'));

        Assert.assertEquals(0, TextUtils.indexOf(text_16_2, '\u0800'));
        Assert.assertEquals(-1, TextUtils.indexOf(text_16_2, 'अ'));
    }

    @Test
    public void TestOrdinalIndexOf() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");
        Text text_a = new Text("a");
        Text text_b = new Text("a\u0e32asd\u0e32asdf");

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
        Assert.assertEquals(0, TextUtils.ordinalIndexOf(text_abc, text_empty2, 0, 1));
        Assert.assertEquals(5, TextUtils.ordinalIndexOf(text_abc, text_empty2, 5, 1));
        Assert.assertEquals(8, TextUtils.ordinalIndexOf(text_abc, text_empty2, 300, 1));

        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(null, 'a', 1));
        Assert.assertEquals(0, TextUtils.ordinalIndexOf(text_a, 'a', 1));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_a, 'a', 2));

        Assert.assertEquals(1, TextUtils.ordinalIndexOf(text_b, '\u0e32', 1));
        Assert.assertEquals(7, TextUtils.ordinalIndexOf(text_b, '\u0e32', 2));

        Assert.assertEquals(1, TextUtils.ordinalIndexOf(text_b, (int) '\u0e32', 1));
        Assert.assertEquals(7, TextUtils.ordinalIndexOf(text_b, (int) '\u0e32', 2));
        Assert.assertEquals(-1, TextUtils.ordinalIndexOf(text_b, (int) '\u0e32', 3));

        Assert.assertEquals(7, TextUtils.ordinalIndexOf(text_b, (int) '\u0e32', 2, 1));
    }

    @Test
    public void TestLastIndexOf() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");
        Text text_a = new Text("a");
        Text text_b = new Text("a\u0e32asd\u0e32asdf");


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

        Assert.assertEquals(0, TextUtils.lastIndexOf(text_a, 'a'));
        Assert.assertEquals(0, TextUtils.lastIndexOf(text_a, (int) 'a'));
        Assert.assertEquals(0, TextUtils.lastIndexOf(text_a, (byte) 'a'));
        Assert.assertEquals(7, TextUtils.lastIndexOf(text_b, '\u0e32'));
        Assert.assertEquals(7, TextUtils.lastIndexOf(text_b, (int) '\u0e32'));
        Assert.assertEquals(7, TextUtils.lastIndexOf(text_b, 0x0e32));

        Assert.assertEquals(1, TextUtils.lastIndexOf(text_b, '\u0e32', 1));
        Assert.assertEquals(1, TextUtils.lastIndexOf(text_b, 0x0e32, 2));
        Assert.assertEquals(-1, TextUtils.lastIndexOf(text_b, 0x0e32, 0));
    }

    @Test
    public void TestLastOrdinalIndexOf() {
        Text text_abc = new Text("aabaabaa");
        Text text_toFind = new Text("a");
        Text text_toFind2 = new Text("b");
        Text text_toFind3 = new Text("ab");
        Text text_empty = new Text("");
        Text text_empty2 = new Text("");
        Text text_a = new Text("a");
        Text text_b = new Text("a\u0e32asd\u0e32asdf");

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

        Assert.assertEquals(0, TextUtils.lastOrdinalIndexOf(text_a, 'a', 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_a, 'a', 2));

        Assert.assertEquals(7, TextUtils.lastOrdinalIndexOf(text_b, '\u0e32', 1));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_b, '\u0e32', 2));

        Assert.assertEquals(7, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', 1));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', 2));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', 3));

        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', -2));

        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', 2, 1));
        Assert.assertEquals(-1, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', 2, 2));
        Assert.assertEquals(1, TextUtils.lastOrdinalIndexOf(text_b, (int) '\u0e32', 6, 1));
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
        char[] arr1 = new char[]{ 'z', 'a' };
        char[] arr2 = new char[]{ 'b', 'y' };
        char[] arr3 = new char[]{ 'q' };

        Text[] abcd = new Text[]{ new Text("ab"), new Text("cd") };
        Text[] cdab = new Text[]{ new Text("cd"), new Text("ab") };
        Text[] mnop = new Text[]{ new Text("mn"), new Text("op") };
        Text[] zababy = new Text[]{ new Text("zab"), new Text("aby") };
        Text[] empty = new Text[]{ new Text("") };
        Text[] a = new Text[]{ new Text("a") };

        Assert.assertEquals(-1, TextUtils.indexOfAny(null, arr1));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_empty, arr1));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, (char[]) null));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, new char[]{ }));
        Assert.assertEquals(0, TextUtils.indexOfAny(text_in, arr1));
        Assert.assertEquals(3, TextUtils.indexOfAny(text_in, arr2));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, arr3));

        Assert.assertEquals(-1, TextUtils.indexOfAny(null, abcd));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, (Text[]) null));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, new Text[]{ }));
        Assert.assertEquals(2, TextUtils.indexOfAny(text_in, abcd));
        Assert.assertEquals(2, TextUtils.indexOfAny(text_in, cdab));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_in, mnop));
        Assert.assertEquals(1, TextUtils.indexOfAny(text_in, zababy));
        Assert.assertEquals(0, TextUtils.indexOfAny(text_in, empty));
        Assert.assertEquals(0, TextUtils.indexOfAny(text_empty, empty));
        Assert.assertEquals(-1, TextUtils.indexOfAny(text_empty, a));
    }

    @Test
    public void TestContainsAny() {
        Text text_in = new Text("zzabyycdxx");
        Text text_empty = new Text("");
        char[] arr1 = new char[]{ 'z', 'a' };
        char[] arr2 = new char[]{ 'b', 'y' };
        char[] arr3 = new char[]{ 'q' };

        Assert.assertEquals(false, TextUtils.containsAny(null, arr1));
        Assert.assertEquals(false, TextUtils.containsAny(text_empty, arr1));
        Assert.assertEquals(false, TextUtils.containsAny(text_in, null));
        Assert.assertEquals(false, TextUtils.containsAny(text_in, new char[]{ }));
        Assert.assertEquals(true, TextUtils.containsAny(text_in, arr1));
        Assert.assertEquals(true, TextUtils.containsAny(text_in, arr2));
        Assert.assertEquals(false, TextUtils.containsAny(text_in, arr3));
    }

    @Test
    public void TestContainsNone() {
        Text test_ab = new Text("ab");
        Text test_empty = new Text("");
        Text test_abab = new Text("abab");
        Text test_ab1 = new Text("ab1");
        Text test_abz = new Text("abz");
        char[] xyz = new char[]{ 'x', 'y', 'z' };
        char[] empty = new char[]{ };

        Assert.assertEquals(true, TextUtils.containsNone(null, xyz));
        Assert.assertEquals(true, TextUtils.containsNone(test_ab, null));
        Assert.assertEquals(true, TextUtils.containsNone(test_empty, xyz));
        Assert.assertEquals(true, TextUtils.containsNone(test_ab, empty));
        Assert.assertEquals(true, TextUtils.containsNone(test_abab, xyz));
        Assert.assertEquals(true, TextUtils.containsNone(test_ab1, xyz));
        Assert.assertEquals(false, TextUtils.containsNone(test_abz, xyz));
    }

    @Test
    public void TestLastIndexOfAny() {
        Text text_in = new Text("zzabyycdxx");
        Text text_empty = new Text("");

        Text[] abcd = new Text[]{ new Text("ab"), new Text("cd") };
        Text[] cdab = new Text[]{ new Text("cd"), new Text("ab") };
        Text[] mnop = new Text[]{ new Text("mn"), new Text("op") };
        Text[] mnempty = new Text[]{ new Text("mn"), new Text("") };
        Text[] zababy = new Text[]{ new Text("zab"), new Text("aby") };
        Text[] empty = new Text[]{ new Text("") };
        Text[] arrNull = new Text[]{ null };
        Text[] a = new Text[]{ new Text("a") };

        Assert.assertEquals(-1, TextUtils.lastIndexOfAny(null, abcd));
        Assert.assertEquals(-1, TextUtils.lastIndexOfAny(text_in, null));
        Assert.assertEquals(-1, TextUtils.lastIndexOfAny(text_in, new Text[]{ }));
        Assert.assertEquals(-1, TextUtils.lastIndexOfAny(text_in, arrNull));
        Assert.assertEquals(6, TextUtils.lastIndexOfAny(text_in, abcd));
        Assert.assertEquals(6, TextUtils.lastIndexOfAny(text_in, cdab));
        Assert.assertEquals(-1, TextUtils.lastIndexOfAny(text_in, mnop));
        Assert.assertEquals(10, TextUtils.lastIndexOfAny(text_in, mnempty));
        Assert.assertEquals(10, TextUtils.lastIndexOfAny(text_in, empty));
        Assert.assertEquals(0, TextUtils.lastIndexOfAny(text_empty, empty));
        Assert.assertEquals(-1, TextUtils.lastIndexOfAny(text_empty, a));
    }

    @Test
    public void TestSubtext() {
        Text text_in = new Text("abc");
        Text text_empty = new Text("");

        Text out = new Text();

        Assert.assertEquals(null, TextUtils.subtext(null, 0, out));
        Assert.assertEquals("", TextUtils.subtext(text_empty, 0, out).toString());
        Assert.assertEquals("abc", TextUtils.subtext(text_in, 0, out).toString());
        Assert.assertEquals("c", TextUtils.subtext(text_in, 2, out).toString());
        Assert.assertEquals("", TextUtils.subtext(text_in, 4, out).toString());
        Assert.assertEquals("bc", TextUtils.subtext(text_in, -2, out).toString());
        Assert.assertEquals("abc", TextUtils.subtext(text_in, -4, out).toString());

        Assert.assertEquals(null, TextUtils.subtext(null, 0, 1, out));
        Assert.assertEquals("", TextUtils.subtext(text_empty, 0, 1, out).toString());
        Assert.assertEquals("ab", TextUtils.subtext(text_in, 0, 2, out).toString());
        Assert.assertEquals("", TextUtils.subtext(text_in, 2, 0, out).toString());
        Assert.assertEquals("c", TextUtils.subtext(text_in, 2, 4, out).toString());
        Assert.assertEquals("", TextUtils.subtext(text_in, 4, 6, out).toString());
        Assert.assertEquals("", TextUtils.subtext(text_in, 2, 2, out).toString());
        Assert.assertEquals("b", TextUtils.subtext(text_in, -2, -1, out).toString());
        Assert.assertEquals("ab", TextUtils.subtext(text_in, -4, 2, out).toString());

    }

    @Test
    public void TestLeftRightMiddle() {
        Text text_in = new Text("abc");
        Text text_empty = new Text("");

        Text out = new Text();

        Assert.assertEquals(null, TextUtils.left(null, 0, out));
        Assert.assertEquals("", TextUtils.left(text_in, -2, out).toString());
        Assert.assertEquals("", TextUtils.left(text_empty, 10, out).toString());
        Assert.assertEquals("", TextUtils.left(text_in, 0, out).toString());
        Assert.assertEquals("ab", TextUtils.left(text_in, 2, out).toString());
        Assert.assertEquals("abc", TextUtils.left(text_in, 4, out).toString());

        Assert.assertEquals(null, TextUtils.right(null, 0, out));
        Assert.assertEquals("", TextUtils.right(text_in, -2, out).toString());
        Assert.assertEquals("", TextUtils.right(text_empty, 10, out).toString());
        Assert.assertEquals("", TextUtils.right(text_in, 0, out).toString());
        Assert.assertEquals("bc", TextUtils.right(text_in, 2, out).toString());
        Assert.assertEquals("abc", TextUtils.right(text_in, 4, out).toString());

        Assert.assertEquals(null, TextUtils.middle(null, 0, 1, out));
        Assert.assertEquals("", TextUtils.middle(text_in, 0, -1, out).toString());
        Assert.assertEquals("", TextUtils.middle(text_empty, 0, -1, out).toString());
        Assert.assertEquals("ab", TextUtils.middle(text_in, 0, 2, out).toString());
        Assert.assertEquals("abc", TextUtils.middle(text_in, 0, 4, out).toString());
        Assert.assertEquals("c", TextUtils.middle(text_in, 2, 4, out).toString());
        Assert.assertEquals("", TextUtils.middle(text_in, 4, 2, out).toString());
        Assert.assertEquals("ab", TextUtils.middle(text_in, -2, 2, out).toString());
    }

    @Test
    public void TestSubtextBeforeAfter() {
        Text text_in = new Text("abc");
        Text text_in2 = new Text("abcba");
        Text text_empty = new Text("");
        Text a = new Text("a");
        Text a2 = new Text("a");
        Text b = new Text("b");
        Text c = new Text("c");
        Text d = new Text("d");
        Text cb = new Text("cb");

        Text out = new Text();

        Assert.assertEquals(null, TextUtils.subtextBefore(null, a, out));
        Assert.assertEquals("", TextUtils.subtextBefore(text_empty, a, out).toString());
        Assert.assertEquals("", TextUtils.subtextBefore(text_in, a, out).toString());
        Assert.assertEquals("a", TextUtils.subtextBefore(text_in2, b, out).toString());
        Assert.assertEquals("ab", TextUtils.subtextBefore(text_in, c, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextBefore(text_in, d, out).toString());
        Assert.assertEquals("", TextUtils.subtextBefore(text_in, text_empty, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextBefore(text_in, null, out).toString());

        Assert.assertEquals(null, TextUtils.subtextAfter(null, a, out));
        Assert.assertEquals("", TextUtils.subtextAfter(text_empty, a, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfter(text_in, null, out).toString());
        Assert.assertEquals("bc", TextUtils.subtextAfter(text_in, a, out).toString());
        Assert.assertEquals("cba", TextUtils.subtextAfter(text_in2, b, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfter(text_in, c, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfter(text_in, d, out).toString());
        Assert.assertEquals("a", TextUtils.subtextAfter(text_in2, cb, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextAfter(text_in, text_empty, out).toString());

        Assert.assertEquals(null, TextUtils.subtextBeforeLast(null, a, out));
        Assert.assertEquals("", TextUtils.subtextBeforeLast(text_empty, a, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextBeforeLast(text_in2, b, out).toString());
        Assert.assertEquals("ab", TextUtils.subtextBeforeLast(text_in, c, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextBeforeLast(text_in2, b, out).toString());
        Assert.assertEquals("", TextUtils.subtextBeforeLast(a, a2, out).toString());
        Assert.assertEquals("a", TextUtils.subtextBeforeLast(a, b, out).toString());
        Assert.assertEquals("a", TextUtils.subtextBeforeLast(a, null, out).toString());
        Assert.assertEquals("a", TextUtils.subtextBeforeLast(a, text_empty, out).toString());

        Assert.assertEquals(null, TextUtils.subtextAfterLast(null, a, out));
        Assert.assertEquals("", TextUtils.subtextAfterLast(text_empty, a, out).toString());
        Assert.assertEquals("bc", TextUtils.subtextAfterLast(text_in, a, out).toString());
        Assert.assertEquals("a", TextUtils.subtextAfterLast(text_in2, b, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfterLast(text_in, c, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfterLast(a, a2, out).toString());
        Assert.assertEquals("a", TextUtils.subtextAfterLast(a, b, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfterLast(a, null, out).toString());
        Assert.assertEquals("", TextUtils.subtextAfterLast(a, text_empty, out).toString());
    }

    @Test
    public void TestSubtextBetween() {
        Text text_in = new Text("wx[b]yz");
        Text text_in2 = new Text("yabcz");
        Text text_in3 = new Text("yabczyabcz");
        Text left_b = new Text("[");
        Text right_b = new Text("]");
        Text empty = new Text("");
        Text empty2 = new Text("");
        Text y = new Text("y");
        Text z = new Text("z");

        Text out = new Text();

        Assert.assertEquals("b", TextUtils.subtextBetween(text_in, left_b, right_b, out).toString());
        Assert.assertEquals(null, TextUtils.subtextBetween(null, left_b, right_b, out));
        Assert.assertEquals(null, TextUtils.subtextBetween(text_in, null, right_b, out));
        Assert.assertEquals(null, TextUtils.subtextBetween(text_in, left_b, null, out));
        Assert.assertEquals("", TextUtils.subtextBetween(empty, empty2, empty2, out).toString());
        Assert.assertEquals(null, TextUtils.subtextBetween(empty, empty2, right_b, out));
        Assert.assertEquals(null, TextUtils.subtextBetween(empty, left_b, right_b, out));
        Assert.assertEquals("", TextUtils.subtextBetween(text_in2, empty2, empty2, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextBetween(text_in2, y, z, out).toString());
        Assert.assertEquals("abc", TextUtils.subtextBetween(text_in3, y, z, out).toString());
    }

    @Test
    public void TestConcat() {
        Text text_in = new Text("abc");
        Text text_concat = new Text("_concat");

        Assert.assertEquals("abc_concat", TextUtils.concat(text_in, text_concat).toString());
        Assert.assertEquals("abc_concat_concat", TextUtils.concat(text_in, text_concat).toString());

    }

    @Test
    public void TestCountMatches() {
        Text text_in1 = new Text("abcabcabc");
        Text text_in2 = new Text("_concat");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");

        Assert.assertEquals(0, TextUtils.countMatches(null, 'c'));
        Assert.assertEquals(3, TextUtils.countMatches(text_in1, 'a'));
        Assert.assertEquals(2, TextUtils.countMatches(text_in2, 'c'));
        Assert.assertEquals(0, TextUtils.countMatches(text_in2, 'q'));
        Assert.assertEquals(3, TextUtils.countMatches(text_in3, '\u0e21'));

        Assert.assertEquals(0, TextUtils.countMatches(null, null));
        Assert.assertEquals(0, TextUtils.countMatches(text_in1, null));
        Assert.assertEquals(0, TextUtils.countMatches(null, text_in1));
        Assert.assertEquals(3, TextUtils.countMatches(text_in1, new Text("abc")));
        Assert.assertEquals(2, TextUtils.countMatches(text_in1, new Text("bca")));

    }

    @Test
    public void TestLowerCase() {
        Text text_in1 = new Text("111123abcABCabc");
        Text text_in2 = new Text("111123abcabcabc");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in5 = new Text("\u00c0\u00c0");
        Text out = new Text();

        Assert.assertEquals(null, TextUtils.lowerCase(null));
        try {
            Assert.assertEquals(null, TextUtils.lowerCase(null, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Assert.assertEquals(null, TextUtils.lowerCase(text_in1, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        Assert.assertEquals("111123abcabcabc", TextUtils.lowerCase(text_in1).toString());
        Assert.assertEquals("111123abcabcabc", TextUtils.lowerCase(text_in2).toString());
        Assert.assertEquals("\u0e21\u0e22\u0e21\u0e21", TextUtils.lowerCase(text_in3).toString());
        Assert.assertEquals("\u00e0\u00e0", TextUtils.lowerCase(text_in5).toString());

        Assert.assertEquals("111123abcabcabc", TextUtils.lowerCase(text_in1, out).toString());
        Assert.assertEquals("111123abcabcabc", out.toString());
    }

    @Test
    public void TestUpperCase() {
        Text text_in1 = new Text("111123abcABCabc");
        Text text_in2 = new Text("111123abcabcabc");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in5 = new Text("\u00e0\u00e0");
        Text out = new Text();

        Assert.assertEquals(null, TextUtils.upperCase(null));
        try {
            Assert.assertEquals(null, TextUtils.upperCase(null, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Assert.assertEquals(null, TextUtils.upperCase(text_in1, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        Assert.assertEquals("111123ABCABCABC", TextUtils.upperCase(text_in1).toString());
        Assert.assertEquals("111123ABCABCABC", TextUtils.upperCase(text_in2).toString());
        Assert.assertEquals("\u0e21\u0e22\u0e21\u0e21", TextUtils.upperCase(text_in3).toString());
        Assert.assertEquals("\u00c0\u00c0", TextUtils.upperCase(text_in5).toString());

        Assert.assertEquals("111123ABCABCABC", TextUtils.upperCase(text_in1, out).toString());
        Assert.assertEquals("111123ABCABCABC", out.toString());
    }

    @Test
    public void TestCapitalize() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abcabcabc");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123abcabcabc");
        Text text_in5 = new Text("\u00e0\u00e0");
        Text out = new Text();

        Assert.assertEquals(null, TextUtils.capitalize(null));
        try {
            Assert.assertEquals(null, TextUtils.capitalize(null, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Assert.assertEquals(null, TextUtils.capitalize(text_in1, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        Assert.assertEquals("AbcABCabc", TextUtils.capitalize(text_in1).toString());
        Assert.assertEquals("Abcabcabc", TextUtils.capitalize(text_in2).toString());
        Assert.assertEquals("\u0e21\u0e22\u0e21\u0e21", TextUtils.capitalize(text_in3).toString());
        Assert.assertEquals("\u00c0\u00e0", TextUtils.capitalize(text_in5).toString());

        Assert.assertEquals("111123abcabcabc", TextUtils.capitalize(text_in4, out).toString());
        Assert.assertEquals("111123abcabcabc", out.toString());
    }

    @Test
    public void TestUncapitalize() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abcabcabc");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123abcabcabc");
        Text text_in5 = new Text("\u00c0\u00e0");
        Text out = new Text();

        Assert.assertEquals(null, TextUtils.uncapitalize(null));
        try {
            Assert.assertEquals(null, TextUtils.uncapitalize(null, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Assert.assertEquals(null, TextUtils.uncapitalize(text_in1, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        Assert.assertEquals("abcABCabc", TextUtils.uncapitalize(text_in1).toString());
        Assert.assertEquals("abcabcabc", TextUtils.uncapitalize(text_in2).toString());
        Assert.assertEquals("\u0e21\u0e22\u0e21\u0e21", TextUtils.uncapitalize(text_in3).toString());
        Assert.assertEquals("\u00e0\u00e0", TextUtils.uncapitalize(text_in5).toString());

        Assert.assertEquals("111123abcabcabc", TextUtils.uncapitalize(text_in4, out).toString());
        Assert.assertEquals("111123abcabcabc", out.toString());
    }

    @Test
    public void TestSwapCase() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abcabcabc");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123abcabcabc");
        Text text_in5 = new Text("\u00c0\u00e0");
        Text out = new Text();

        Assert.assertEquals(null, TextUtils.swapCase(null));
        try {
            Assert.assertEquals(null, TextUtils.swapCase(null, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            Assert.assertEquals(null, TextUtils.swapCase(text_in1, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        Assert.assertEquals("ABCabcABC", TextUtils.swapCase(text_in1).toString());
        Assert.assertEquals("aBCABCABC", TextUtils.swapCase(text_in2).toString());
        Assert.assertEquals("\u0e21\u0e22\u0e21\u0e21", TextUtils.swapCase(text_in3).toString());
        Assert.assertEquals("\u00e0\u00c0", TextUtils.swapCase(text_in5).toString());

        Assert.assertEquals("111123ABCABCABC", TextUtils.swapCase(text_in4, out).toString());
        Assert.assertEquals("111123ABCABCABC", out.toString());
    }

    @Test
    public void TestIsAlpha() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abcabcabc");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123abcabcabc");
        Text text_in5 = new Text("\u00c0\u00e0-");

        Assert.assertEquals(false, TextUtils.isAlpha(null));
        Assert.assertEquals(true, TextUtils.isAlpha(text_in1));
        Assert.assertEquals(true, TextUtils.isAlpha(text_in2));
        Assert.assertEquals(true, TextUtils.isAlpha(text_in3));
        Assert.assertEquals(false, TextUtils.isAlpha(text_in4));
        Assert.assertEquals(false, TextUtils.isAlpha(text_in5));
    }

    @Test
    public void TestIsAlphaSpace() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abc abcabc     ");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123abcabcabc");
        Text text_in5 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isAlphaSpace(null));
        Assert.assertEquals(true, TextUtils.isAlphaSpace(empty));
        Assert.assertEquals(true, TextUtils.isAlphaSpace(text_in1));
        Assert.assertEquals(true, TextUtils.isAlphaSpace(text_in2));
        Assert.assertEquals(true, TextUtils.isAlphaSpace(text_in3));
        Assert.assertEquals(false, TextUtils.isAlphaSpace(text_in4));
        Assert.assertEquals(false, TextUtils.isAlphaSpace(text_in5));
    }

    @Test
    public void TestIsAlphanumeric() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abc abcabc     ");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123abcabcabc");
        Text text_in5 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isAlphanumeric(null));
        Assert.assertEquals(false, TextUtils.isAlphanumeric(empty));
        Assert.assertEquals(true, TextUtils.isAlphanumeric(text_in1));
        Assert.assertEquals(false, TextUtils.isAlphanumeric(text_in2));
        Assert.assertEquals(true, TextUtils.isAlphanumeric(text_in3));
        Assert.assertEquals(true, TextUtils.isAlphanumeric(text_in4));
        Assert.assertEquals(false, TextUtils.isAlphanumeric(text_in5));
    }

    @Test
    public void TestIsAlphanumericSpace() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abc abcabc     ");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123ab cabcabc");
        Text text_in5 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isAlphanumericSpace(null));
        Assert.assertEquals(true, TextUtils.isAlphanumericSpace(empty));
        Assert.assertEquals(true, TextUtils.isAlphanumericSpace(text_in1));
        Assert.assertEquals(true, TextUtils.isAlphanumericSpace(text_in2));
        Assert.assertEquals(true, TextUtils.isAlphanumericSpace(text_in3));
        Assert.assertEquals(true, TextUtils.isAlphanumericSpace(text_in4));
        Assert.assertEquals(false, TextUtils.isAlphanumericSpace(text_in5));
    }

    @Test
    public void TestIsAsciiPrintable() {
        Text text_in1 = new Text("abcABCabc");
        Text text_in2 = new Text("Abc abcabc     ");
        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
        Text text_in4 = new Text("111123ab cabcabc");
        Text text_in5 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isAsciiPrintable(null));
        Assert.assertEquals(true, TextUtils.isAsciiPrintable(empty));
        Assert.assertEquals(true, TextUtils.isAsciiPrintable(text_in1));
        Assert.assertEquals(true, TextUtils.isAsciiPrintable(text_in2));
        Assert.assertEquals(false, TextUtils.isAsciiPrintable(text_in3));
        Assert.assertEquals(true, TextUtils.isAsciiPrintable(text_in4));
        Assert.assertEquals(false, TextUtils.isAsciiPrintable(text_in5));
    }

    @Test
    public void TestIsNumeric() {
        Text text_in1 = new Text("123");
        Text text_in2 = new Text("12.3");
        Text text_in3 = new Text("\u0967\u0968\u0969");
        Text text_in4 = new Text("111123ab cabcabc");
        Text text_in5 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isNumeric(null));
        Assert.assertEquals(false, TextUtils.isNumeric(empty));
        Assert.assertEquals(true, TextUtils.isNumeric(text_in1));
        Assert.assertEquals(false, TextUtils.isNumeric(text_in2));
        Assert.assertEquals(true, TextUtils.isNumeric(text_in3));
        Assert.assertEquals(false, TextUtils.isNumeric(text_in4));
        Assert.assertEquals(false, TextUtils.isNumeric(text_in5));
    }

    @Test
    public void TestIsNumericSpace() {
        Text text_in1 = new Text("123");
        Text text_in2 = new Text("12 3");
        Text text_in3 = new Text("\u0967\u0968\u0969");
        Text text_in4 = new Text("\u0967 \u0968\u0969");
        Text text_in5 = new Text("111123ab cabcabc");
        Text text_in6 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isNumericSpace(null));
        Assert.assertEquals(true, TextUtils.isNumericSpace(empty));
        Assert.assertEquals(true, TextUtils.isNumericSpace(text_in1));
        Assert.assertEquals(true, TextUtils.isNumericSpace(text_in2));
        Assert.assertEquals(true, TextUtils.isNumericSpace(text_in3));
        Assert.assertEquals(true, TextUtils.isNumericSpace(text_in4));
        Assert.assertEquals(false, TextUtils.isNumericSpace(text_in5));
        Assert.assertEquals(false, TextUtils.isNumericSpace(text_in6));
    }

    @Test
    public void TestIsWhitespace() {
        Text text_in1 = new Text("     ");
        Text text_in2 = new Text("12 3");
        Text text_in3 = new Text("\u0967\u0968\u0969");
        Text text_in4 = new Text("\u0967 \u0968\u0969");
        Text text_in5 = new Text("111123ab cabcabc");
        Text text_in6 = new Text("\u00c0\u00e0-");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isWhitespace(null));
        Assert.assertEquals(true, TextUtils.isWhitespace(empty));
        Assert.assertEquals(true, TextUtils.isWhitespace(text_in1));
        Assert.assertEquals(false, TextUtils.isWhitespace(text_in2));
        Assert.assertEquals(false, TextUtils.isWhitespace(text_in3));
        Assert.assertEquals(false, TextUtils.isWhitespace(text_in4));
        Assert.assertEquals(false, TextUtils.isWhitespace(text_in5));
        Assert.assertEquals(false, TextUtils.isWhitespace(text_in6));
    }

    @Test
    public void TestIsAllLowercase() {
        Text text_in1 = new Text("     ");
        Text text_in2 = new Text("alskjnkhnkjhbjhbjhgh");
        Text text_in3 = new Text("askdjhkjhgsdfH");
        Text text_in4 = new Text("\u0967 \u0968\u0969");
        Text text_in5 = new Text("111123ab cabcabc");
        Text text_in6 = new Text("\u00e0\u00e0");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isAllLowerCase(null));
        Assert.assertEquals(false, TextUtils.isAllLowerCase(empty));
        Assert.assertEquals(false, TextUtils.isAllLowerCase(text_in1));
        Assert.assertEquals(true, TextUtils.isAllLowerCase(text_in2));
        Assert.assertEquals(false, TextUtils.isAllLowerCase(text_in3));
        Assert.assertEquals(false, TextUtils.isAllLowerCase(text_in4));
        Assert.assertEquals(false, TextUtils.isAllLowerCase(text_in5));
        Assert.assertEquals(true, TextUtils.isAllLowerCase(text_in6));
    }

    @Test
    public void TestIsAllUppercase() {
        Text text_in1 = new Text("     ");
        Text text_in2 = new Text("BKJBKLJNLKJNHJGVHJGV");
        Text text_in3 = new Text("KJNLAKJSNDLFKJNj");
        Text text_in4 = new Text("\u0967 \u0968\u0969");
        Text text_in5 = new Text("111123ab cabcabc");
        Text text_in6 = new Text("\u00C0\u00C0");
        Text empty = new Text("");

        Assert.assertEquals(false, TextUtils.isAllUpperCase(null));
        Assert.assertEquals(false, TextUtils.isAllUpperCase(empty));
        Assert.assertEquals(false, TextUtils.isAllUpperCase(text_in1));
        Assert.assertEquals(true, TextUtils.isAllUpperCase(text_in2));
        Assert.assertEquals(false, TextUtils.isAllUpperCase(text_in3));
        Assert.assertEquals(false, TextUtils.isAllUpperCase(text_in4));
        Assert.assertEquals(false, TextUtils.isAllUpperCase(text_in5));
        Assert.assertEquals(true, TextUtils.isAllUpperCase(text_in6));
    }

    @Test
    public void TestDifference() {
        Text text_in1 = new Text("abc");
        Text text_in2 = new Text("ab");
        Text text_in3 = new Text("abxyz");
        Text text_in4 = new Text("abcde");
        Text text_in5 = new Text("xyz");
        Text empty1 = new Text("");
        Text empty2 = new Text("");

        Text text_in6 = new Text("\u00C0\u00C0\u00C0\u00E0");
        Text text_in7 = new Text("\u00C0\u00C0");

        Text out = new Text("");

        Assert.assertEquals(null, TextUtils.difference(null, null, out));
        try {
            Assert.assertEquals(null, TextUtils.difference(empty1, empty2, null));
            Assert.fail();
        } catch (IllegalArgumentException e) {
        }
        Assert.assertEquals("", TextUtils.difference(empty1, empty2, out).toString());
        Assert.assertEquals("abc", TextUtils.difference(empty1, text_in1, out).toString());
        Assert.assertEquals("", TextUtils.difference(text_in1, empty1, out).toString());
        Assert.assertEquals("", TextUtils.difference(text_in1, text_in1, out).toString());
        Assert.assertEquals("", TextUtils.difference(text_in1, text_in2, out).toString());
        Assert.assertEquals("xyz", TextUtils.difference(text_in2, text_in3, out).toString());
        Assert.assertEquals("xyz", TextUtils.difference(text_in4, text_in3, out).toString());
        Assert.assertEquals("xyz", TextUtils.difference(text_in4, text_in5, out).toString());
        Assert.assertEquals("\u00C0\u00E0", TextUtils.difference(text_in7, text_in6, out).toString());
    }

    @Test
    public void TestCharAt() {
        Text text_in1 = new Text("abxyz");
        Text text_in2 = new Text("\u00C0\u00C0");

        Assert.assertEquals(-1, TextUtils.charAt(null, 0));
        Assert.assertEquals(text_in1.charAt(6), TextUtils.charAt(text_in1, 6));

        for (int i = 0; i < text_in1.getLength(); i++) {
            Assert.assertEquals(text_in1.charAt(i), TextUtils.charAt(text_in1, i));
        }

        for (int i = 0; i < text_in2.getLength(); i++) {
            Assert.assertEquals(text_in2.charAt(i), TextUtils.charAt(text_in2, i));
        }

    }


//    @Test
//    public void TestNumBytes() {
//        Text text_in3 = new Text("\u0e21\u0e22\u0e21\u0e21");
//        Text text_in4 = new Text("111123abcabcabc");
//
//        for (int i = 0; i < text_in4.getLength(); i++) {
//            Assert.assertEquals(1, TextUtils.getNumBytesWithStartingByte(text_in4.getBytes()[i]));
//        }
//
//        for (int i = 0; i < text_in3.getLength(); i++) {
//            if (i % 3 == 0) {
//                Assert.assertEquals(3, TextUtils.getNumBytesWithStartingByte(text_in3.getBytes()[i]));
//            } else {
//                Assert.assertEquals(-1, TextUtils.getNumBytesWithStartingByte(text_in3.getBytes()[i]));
//            }
//        }
//
//        Text euro = new Text("¢");
//
//        System.out.println(Integer.toHexString(TextUtils.bytesToUnicodeInt(euro.getBytes(), 0)));
//
//    }

}
