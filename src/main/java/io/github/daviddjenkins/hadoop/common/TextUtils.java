package io.github.daviddjenkins.hadoop.common;

import org.apache.hadoop.io.Text;

/**
 * This class is meant to mimic some of the functionality of the StringUtils library from
 * Apache Commons
 * <a href="https://github.com/apache/commons-lang/blob/trunk/src/main/java/org/apache/commons/lang3/StringUtils.java">[LINK]</a>.
 * <p>Operations on {@link org.apache.hadoop.io.Text} that are {@code null} safe.</p>
 * <p/>
 * <p/>
 * <p>The {@code TextUtils} class defines certain words related to Text handling.</p>
 * <p/>
 * <p/>
 * <p>{@code TrimUtils} handles {@code null} input Texts quietly.
 * That is to say that a {@code null} input will return {@code null}.
 * Where a {@code boolean} or {@code int} is being returned
 * details vary by method.</p>
 * <p/>
 * <p>A side effect of the {@code null} handling is that a
 * {@code NullPointerException} should be considered a bug in
 * {@code TextUtils}.</p>
 * <p/>
 * <p>Methods in this class give sample code to explain their operation.
 * The symbol {@code *} is used to indicate any input including {@code null}.</p>
 * <p/>
 * <p>#ThreadSafe#</p>
 *
 * @see org.apache.hadoop.io.Text
 */

public class TextUtils {

    public static final byte[] SPACE = " ".getBytes();
    public static final byte[] EMPTY = "".getBytes();
    public static final byte[] NEW_LINE = "\n".getBytes();
    public static final byte[] CARRIAGE_RETURN = "\r".getBytes();
    public static final byte[] TAB = "\t".getBytes();
    public static final byte[] PIPE = "|".getBytes();

    public static final int NOT_FOUND = -1;

    private enum CHARTYPE {CONTROL, WHITESPACE}

    /**
     * <p>Checks if a Text is empty ("") or null.</p>
     * <p/>
     * <pre>
     * TextUtils.isEmpty(null)      = true
     * TextUtils.isEmpty("")        = true
     * TextUtils.isEmpty(" ")       = false
     * TextUtils.isEmpty("bob")     = false
     * TextUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if the Text is empty or null
     * @since 1.0
     */
    public static boolean isEmpty(final Text in) {
        return (in == null || in.getLength() == 0);
    }

    /**
     * <p>Checks if a Text is not empty ("") and not null.</p>
     * <p/>
     * <pre>
     * TextUtils.isNotEmpty(null)      = false
     * TextUtils.isNotEmpty("")        = false
     * TextUtils.isNotEmpty(" ")       = true
     * TextUtils.isNotEmpty("bob")     = true
     * TextUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if the Text is not empty and not null
     * @since 1.0
     */
    public static boolean isNotEmpty(final Text in) {
        return !isEmpty(in);
    }

    /**
     * <p>Checks if any one of the Texts are empty ("") or null.</p>
     * <p/>
     * <pre>
     * TextUtils.isAnyEmpty(null)             = true
     * TextUtils.isAnyEmpty(null, "foo")      = true
     * TextUtils.isAnyEmpty("", "bar")        = true
     * TextUtils.isAnyEmpty("bob", "")        = true
     * TextUtils.isAnyEmpty("  bob  ", null)  = true
     * TextUtils.isAnyEmpty(" ", "bar")       = false
     * TextUtils.isAnyEmpty("foo", "bar")     = false
     * </pre>
     *
     * @param ins the Texts to check, may be null or empty
     * @return {@code true} if any of the Texts are empty or null
     * @since 1.0
     */
    public static boolean isAnyEmpty(final Text[] ins) {
        if (ins == null || ins.length == 0) {
            return true;
        }

        for (Text in : ins) {
            if (isEmpty(in)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Checks if none of the Texts are empty ("") or null.</p>
     * <p/>
     * <pre>
     * TextUtils.isNoneEmpty(null)             = false
     * TextUtils.isNoneEmpty(null, "foo")      = false
     * TextUtils.isNoneEmpty("", "bar")        = false
     * TextUtils.isNoneEmpty("bob", "")        = false
     * TextUtils.isNoneEmpty("  bob  ", null)  = false
     * TextUtils.isNoneEmpty(" ", "bar")       = true
     * TextUtils.isNoneEmpty("foo", "bar")     = true
     * </pre>
     *
     * @param ins the Texts to check, may be null or empty
     * @return {@code true} if none of the Texts are empty or null
     * @since 1.0
     */
    public static boolean isNoneEmpty(final Text[] ins) {
        return !isAnyEmpty(ins);
    }

    /**
     * <p>Checks if a Text is whitespace, empty ("") or null.</p>
     * <p/>
     * <pre>
     * TextUtils.isBlank(null)      = true
     * TextUtils.isBlank("")        = true
     * TextUtils.isBlank(" ")       = true
     * TextUtils.isBlank("bob")     = false
     * TextUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if the Text is null, empty or whitespace
     * @since 1.0
     */
    public static boolean isBlank(final Text in) {
        if (isEmpty(in)) {
            return true;
        }
        for (byte b : in.getBytes()) {
            if (!Character.isWhitespace(b)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a Text is not empty (""), not null and not whitespace only.</p>
     * <p/>
     * <pre>
     * TextUtils.isNotBlank(null)      = false
     * TextUtils.isNotBlank("")        = false
     * TextUtils.isNotBlank(" ")       = false
     * TextUtils.isNotBlank("bob")     = true
     * TextUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if the Text is
     * not empty and not null and not whitespace
     * @since 1.0
     */
    public static boolean isNotBlank(final Text in) {
        return !isBlank(in);
    }

    /**
     * <p>Checks if any one of the Texts are blank ("") or null and not whitespace only.</p>
     * <p/>
     * <pre>
     * TextUtils.isAnyBlank(null)             = true
     * TextUtils.isAnyBlank(null, "foo")      = true
     * TextUtils.isAnyBlank(null, null)       = true
     * TextUtils.isAnyBlank("", "bar")        = true
     * TextUtils.isAnyBlank("bob", "")        = true
     * TextUtils.isAnyBlank("  bob  ", null)  = true
     * TextUtils.isAnyBlank(" ", "bar")       = true
     * TextUtils.isAnyBlank("foo", "bar")     = false
     * </pre>
     *
     * @param ins the Texts to check, may be null or empty
     * @return {@code true} if any of the Texts are blank or null or whitespace only
     * @since 1.0
     */
    public static boolean isAnyBlank(final Text[] ins) {
        if (ins == null || ins.length == 0) {
            return true;
        }

        for (Text in : ins) {
            if (isBlank(in)) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Checks if none of the Texts are blank ("") or null and whitespace only.</p>
     * <p/>
     * <pre>
     * TextUtils.isNoneBlank(null)             = false
     * TextUtils.isNoneBlank(null, "foo")      = false
     * TextUtils.isNoneBlank(null, null)       = false
     * TextUtils.isNoneBlank("", "bar")        = false
     * TextUtils.isNoneBlank("bob", "")        = false
     * TextUtils.isNoneBlank("  bob  ", null)  = false
     * TextUtils.isNoneBlank(" ", "bar")       = false
     * TextUtils.isNoneBlank("foo", "bar")     = true
     * </pre>
     *
     * @param ins the Texts to check, may be null or empty
     * @return {@code true} if none of the Texts are blank or null or whitespace only
     * @since 1.0
     */
    public static boolean isNoneBlank(final Text[] ins) {
        return !isAnyBlank(ins);
    }

    private static int getStartSpot(byte[] inb, int start, int length, CHARTYPE charType) {

        if (start >= length) {
            return length;
        } else if (length < 0 || start < 0) {
            return NOT_FOUND;
        }

        int startidx = start;
        if (charType == CHARTYPE.CONTROL) {

            // Skip to the first non-control
            while (startidx < length && inb[startidx] <= 32) {
                startidx++;
            }

        } else if (charType == CHARTYPE.WHITESPACE) {

            // Skip to the first non-whitespace
            while (startidx < length && Character.isWhitespace(inb[startidx])) {
                startidx++;
            }

        }

        return startidx;
    }

    private static int getLastSpot(byte[] inb, int start, int length, CHARTYPE charType) {

        if (start >= length) {
            return length;
        } else if (length < 0 || start < 0) {
            return NOT_FOUND;
        }

        int endidx = length - 1;
        if (charType == CHARTYPE.CONTROL) {

            // Skip to the last non-control
            while (endidx > start && inb[endidx] <= 32) {
                endidx--;
            }

        } else if (charType == CHARTYPE.WHITESPACE) {

            // Skip to the last non-whitespace
            while (endidx > start && Character.isWhitespace(inb[endidx])) {
                endidx--;
            }

        }

        return endidx + 1;
    }

    private static Text removeFrontBackChars(Text in, CHARTYPE charType) {
        if (in == null) {
            return null;
        }

        int inLength = in.getLength();
        byte[] inb = in.getBytes();

        int startIdx = getStartSpot(inb, 0, inLength, charType);

        // set the new byte array
        if (startIdx == inLength) {
            in.set(EMPTY);
        } else {
            int endIdx = getLastSpot(inb, startIdx, inLength, charType);
            in.set(inb, startIdx, endIdx - startIdx);
        }

        return in;
    }

    /**
     * <p>Removes control characters (char &lt;= 32) from both
     * ends of this Text, handling {@code null} by returning
     * {@code null}.</p>
     * <p/>
     * <p>Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #strip(Text)}.</p>
     * <p/>
     * <p>This method edits the passed in Text object and returns
     * the input for chaining convenience.</p>
     * <p/>
     * <pre>
     * TextUtils.trim(null)          = null
     * TextUtils.trim("")            = ""
     * TextUtils.trim("     ")       = ""
     * TextUtils.trim("abc")         = "abc"
     * TextUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param in the Text to be trimmed, may be null
     * @return the trimmed Text, {@code null} if null Text input
     */
    public static Text trim(Text in) {
        return removeFrontBackChars(in, CHARTYPE.CONTROL);
    }

    /**
     * <p>Strips whitespace from the start and end of a Text.</p>
     * <p/>
     * <p>This is similar to {@link #trim(Text)} but removes whitespace.
     * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <p>This method edits the passed in Text object and returns
     * the input for chaining convenience.</p>
     * <p/>
     * <pre>
     * TextUtils.strip(null)     = null
     * TextUtils.strip("")       = ""
     * TextUtils.strip("   ")    = ""
     * TextUtils.strip("abc")    = "abc"
     * TextUtils.strip("  abc")  = "abc"
     * TextUtils.strip("abc  ")  = "abc"
     * TextUtils.strip(" abc ")  = "abc"
     * TextUtils.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param in the Text to remove whitespace from, may be null
     * @return the stripped Text, {@code null} if null Text input
     */
    public static Text strip(Text in) {
        return removeFrontBackChars(in, CHARTYPE.WHITESPACE);
    }

    /**
     * <p>Compares two Text, returning {@code true} if they represent
     * equal sequences of characters.</p>
     * <p/>
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case sensitive.</p>
     * <p/>
     * <pre>
     * TextUtils.equals(null, null)   = true
     * TextUtils.equals(null, "abc")  = false
     * TextUtils.equals("abc", null)  = false
     * TextUtils.equals("abc", "abc") = true
     * TextUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1 the first Text, may be {@code null}
     * @param str2 the second Text, may be {@code null}
     * @return {@code true} if the Texts are equal (case-sensitive), or both {@code null}
     * @see Object#equals(Object)
     */
    public static boolean equals(Text str1, Text str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null || str2 == null) {
            return false;
        }

        return str1.equals(str2);
    }

    /**
     * <p>Compares two Text, returning {@code true} if they represent
     * equal sequences of characters, ignoring case.</p>
     * <p/>
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered equal. Comparison is case insensitive.</p>
     * <p/>
     * <pre>
     * TextUtils.equalsIgnoreCase(null, null)   = true
     * TextUtils.equalsIgnoreCase(null, "abc")  = false
     * TextUtils.equalsIgnoreCase("abc", null)  = false
     * TextUtils.equalsIgnoreCase("abc", "abc") = true
     * TextUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 the first Text, may be null
     * @param str2 the second Text, may be null
     * @return {@code true} if the Text are equal, case insensitive, or
     * both {@code null}
     */
    public static boolean equalsIgnoreCase(Text str1, Text str2) {
        if (str1 == null || str2 == null) {
            return false;
        } else if (str1 == str2) {
            return true;
        } else if (str1.getLength() != str2.getLength()) {
            return false;
        } else {
            int str1Len = str1.getLength();
            byte[] str1Bytes = str1.getBytes();
            byte[] str2Bytes = str2.getBytes();
            for (int i = 0; i < str1Len; i++) {
                if (Character.toLowerCase(str1Bytes[i]) != Character.toLowerCase(str2Bytes[i])) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * <p>Finds the first index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code NOT_FOUND (-1)}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *)         = -1
     * TextUtils.indexOf("", *)           = -1
     * TextUtils.indexOf("aabaabaa", 'a') = 0
     * TextUtils.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the character to find
     * @return the first index of the search character, -1 if no match or {@code null} Text input
     */
    public static int indexOf(Text t, char toFind) {
        return indexOf(t, toFind, 0);
    }

    /**
     * <p>Finds the first index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code NOT_FOUND (-1)}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *)         = -1
     * TextUtils.indexOf("", *)           = -1
     * TextUtils.indexOf("aabaabaa", 'a') = 0
     * TextUtils.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the character to find
     * @return the first index of the search character, -1 if no match or {@code null} Text input
     */
    public static int indexOf(Text t, int toFind) {
        return indexOf(t, toFind, 0);
    }

    /**
     * <p>Finds the first index within a Text from a start position, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code (NOT_FOUND) -1}.
     * A negative start position is treated as zero.
     * A start position greater than the string length returns {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *, *)          = -1
     * TextUtils.indexOf("", *, *)            = -1
     * TextUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * TextUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * TextUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * TextUtils.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the character to find
     * @param start  the start position, negative treated as zero
     * @return the first index of the search character (always &ge; startPos),
     * -1 if no match or {@code null} string input
     */
    public static int indexOf(Text t, int toFind, int start) {
        return indexOf(t, (char) toFind, start);
    }

    /**
     * <p>Finds the first index within a Text from a start position, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code (NOT_FOUND) -1}.
     * A negative start position is treated as zero.
     * A start position greater than the string length returns {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *, *)          = -1
     * TextUtils.indexOf("", *, *)            = -1
     * TextUtils.indexOf("aabaabaa", 'b', 0)  = 2
     * TextUtils.indexOf("aabaabaa", 'b', 3)  = 5
     * TextUtils.indexOf("aabaabaa", 'b', 9)  = -1
     * TextUtils.indexOf("aabaabaa", 'b', -1) = 2
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the character to find
     * @param start  the start position, negative treated as zero
     * @return the first index of the search character (always &ge; startPos),
     * -1 if no match or {@code null} string input
     */
    public static int indexOf(Text t, char toFind, int start) {
        if (isEmpty(t) || start >= t.getLength()) {
            return NOT_FOUND;
        } else if (start < 0) {
            start = 0;
        }

        int tLen = t.getLength();
        byte[] tb = t.getBytes();
        for (int i = start; i < tLen; i++) {
            if (tb[i] == toFind) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * <p>Finds the first index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *)          = -1
     * TextUtils.indexOf(*, null)          = -1
     * TextUtils.indexOf("", "")           = 0
     * TextUtils.indexOf("", *)            = -1 (except when * = "")
     * TextUtils.indexOf("aabaabaa", "a")  = 0
     * TextUtils.indexOf("aabaabaa", "b")  = 2
     * TextUtils.indexOf("aabaabaa", "ab") = 1
     * TextUtils.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the Text to find, may be null
     * @return the first index of the search Text,
     * -1 if no match or {@code null} string input
     */
    public static int indexOf(Text t, Text toFind) {
        return indexOf(t, toFind, 0);
    }

    /**
     * <p>Finds the first index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A negative start position is treated as zero.
     * An empty ("") search Text always matches.
     * A start position greater than the string length only matches
     * an empty search Text.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *, *)          = -1
     * TextUtils.indexOf(*, null, *)          = -1
     * TextUtils.indexOf("", "", 0)           = 0
     * TextUtils.indexOf("", *, 0)            = -1 (except when * = "")
     * TextUtils.indexOf("aabaabaa", "a", 0)  = 0
     * TextUtils.indexOf("aabaabaa", "b", 0)  = 2
     * TextUtils.indexOf("aabaabaa", "ab", 0) = 1
     * TextUtils.indexOf("aabaabaa", "b", 3)  = 5
     * TextUtils.indexOf("aabaabaa", "b", 9)  = -1
     * TextUtils.indexOf("aabaabaa", "b", -1) = 2
     * TextUtils.indexOf("aabaabaa", "", 2)   = 2
     * TextUtils.indexOf("abc", "", 9)        = 3
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the Text to find, may be null
     * @param start  the start position, negative treated as zero
     * @return the first index of the search Text (always &ge; startPos),
     * -1 if no match or {@code null} string input
     */
    public static int indexOf(Text t, Text toFind, int start) {
        if (start < 0) {
            start = 0;
        }

        if (t == null || toFind == null) {
            return NOT_FOUND;
        }

        int tLen = t.getLength();
        int toFindLen = toFind.getLength();

        // Catch some special cases up front
        if (t == toFind) {
            return (start == 0 ? 0 : NOT_FOUND);
        } else if (tLen < toFindLen) {
            return NOT_FOUND;
        } else if (tLen == 0) {
            return (toFindLen == 0 ? 0 : NOT_FOUND);
        } else if (toFindLen == 0) {
            return (start > tLen ? tLen : start);
        } else if (start > tLen) {
            return NOT_FOUND;
        }

        byte[] tb = t.getBytes();
        byte[] toFindB = toFind.getBytes();

        for (int i = start; i < tLen - toFindLen + 1; i++) {
            if (tb[i] == toFindB[0]) {
                boolean flag = true;
                for (int j = 1; j < toFindLen; j++) {
                    if (tb[i + j] != toFindB[j]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * <p>Finds the n-th index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.ordinalIndexOf(null, *, *)          = -1
     * TextUtils.ordinalIndexOf("aabaabaa", 'a', 1)  = 0
     * TextUtils.ordinalIndexOf("aabaabaa", 'a', 2)  = 1
     * TextUtils.ordinalIndexOf("aabaabaa", 'b', 1)  = 2
     * TextUtils.ordinalIndexOf("aabaabaa", 'b', 2)  = 5
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th {@code searchStr} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} string input
     */
    public static int ordinalIndexOf(Text t, char toFind, int ordinal) {
        return ordinalIndexOf(t, (int) toFind, ordinal);
    }

    /**
     * <p>Finds the n-th index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.ordinalIndexOf(null, *, *)          = -1
     * TextUtils.ordinalIndexOf("aabaabaa", 'a', 1)  = 0
     * TextUtils.ordinalIndexOf("aabaabaa", 'a', 2)  = 1
     * TextUtils.ordinalIndexOf("aabaabaa", 'b', 1)  = 2
     * TextUtils.ordinalIndexOf("aabaabaa", 'b', 2)  = 5
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th {@code searchStr} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} string input
     */
    public static int ordinalIndexOf(Text t, int toFind, int ordinal) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }
        return ordinalIndexOf(t, toFind, 0, ordinal);
    }

    /**
     * <p>Finds the n-th index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.ordinalIndexOf(null, *, *, *)          = -1
     * TextUtils.ordinalIndexOf("aabaabaa", 'a', 0, 1)  = 0
     * TextUtils.ordinalIndexOf("aabaabaa", 'a', 3, 2)  = 6
     * TextUtils.ordinalIndexOf("aabaabaa", 'b', 0, 1)  = 2
     * TextUtils.ordinalIndexOf("aabaabaa", 'b', 2, 2)  = -1
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th {@code searchStr} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} string input
     */
    public static int ordinalIndexOf(Text t, int toFind, int start, int ordinal) {
        if (isEmpty(t) || ordinal <= 0 || ordinal >= t.getLength()) {
            return NOT_FOUND;
        }

        int nextStart = start;
        for (int i = 0; i < ordinal; i++) {
            nextStart = indexOf(t, toFind, nextStart);
            if (nextStart == NOT_FOUND) {
                return NOT_FOUND;
            }
            nextStart++;
        }

        return nextStart - 1;
    }

    /**
     * <p>Finds the n-th index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.ordinalIndexOf(null, *, *)          = -1
     * TextUtils.ordinalIndexOf(*, null, *)          = -1
     * TextUtils.ordinalIndexOf("", "", *)           = 0
     * TextUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
     * TextUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
     * TextUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
     * TextUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
     * TextUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
     * TextUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
     * TextUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
     * TextUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the Text to find, may be null
     * @param ordinal the n-th {@code toFind} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
    public static int ordinalIndexOf(Text t, Text toFind, int ordinal) {
        return ordinalIndexOf(t, toFind, 0, ordinal);
    }

    /**
     * <p>Finds the n-th index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.ordinalIndexOf(null, *, *, *)          = -1
     * TextUtils.ordinalIndexOf(*, null, *, *)          = -1
     * TextUtils.ordinalIndexOf("", "", *, *)           = 0
     * TextUtils.ordinalIndexOf("aabaabaa", "a", 0, 1)  = 0
     * TextUtils.ordinalIndexOf("aabaabaa", "a", 3, 2)  = 6
     * TextUtils.ordinalIndexOf("aabaabaa", "b", 0, 1)  = 2
     * TextUtils.ordinalIndexOf("aabaabaa", "b", 3, 2)  = -1
     * TextUtils.ordinalIndexOf("aabaabaa", "ab", 0, 1) = 1
     * TextUtils.ordinalIndexOf("aabaabaa", "ab", 0, 2) = 4
     * TextUtils.ordinalIndexOf("aabaabaa", "", *, 1)   = start >= len(t) ? len(t) : start
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the Text to find, may be null
     * @param ordinal the n-th {@code toFind} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
    public static int ordinalIndexOf(Text t, Text toFind, int start, int ordinal) {
        if (t == null || toFind == null || ordinal <= 0) {
            return NOT_FOUND;
        }

        int nextStart = start;
        for (int i = 0; i < ordinal; i++) {
            nextStart = indexOf(t, toFind, nextStart);
            if (nextStart == NOT_FOUND) {
                return NOT_FOUND;
            }
            nextStart++;
        }

        return nextStart - 1;
    }

    /**
     * <p>Finds the last index within a text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOf(null, *)         = -1
     * TextUtils.lastIndexOf("", *)           = -1
     * TextUtils.lastIndexOf("aabaabaa", 'a') = 7
     * TextUtils.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     *
     * @param t      the CharSequence to check, may be null
     * @param toFind the character to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(Text t, char toFind) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }

        return lastIndexOf(t, toFind, t.getLength() - 1);
    }

    /**
     * <p>Finds the last index within a text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOf(null, *)         = -1
     * TextUtils.lastIndexOf("", *)           = -1
     * TextUtils.lastIndexOf("aabaabaa", 'a') = 7
     * TextUtils.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     *
     * @param t      the CharSequence to check, may be null
     * @param toFind the character to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(Text t, int toFind) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }

        return lastIndexOf(t, toFind, t.getLength() - 1);
    }

    /**
     * <p>Finds the last index within a text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOf(null, *, *)         = -1
     * TextUtils.lastIndexOf("", *, *)           = -1
     * TextUtils.lastIndexOf("aabaabaa", 'a', 7) = 7
     * TextUtils.lastIndexOf("aabaabaa", 'b', 7) = 5
     * TextUtils.lastIndexOf("aabaabaa", 'b', 4) = 2
     * TextUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * </pre>
     *
     * @param t      the CharSequence to check, may be null
     * @param toFind the character to find
     * @param start  the start position
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(Text t, int toFind, int start) {
        return lastIndexOf(t, (char) toFind, start);
    }

    /**
     * <p>Finds the last index within a text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOf(null, *, *)         = -1
     * TextUtils.lastIndexOf("", *, *)           = -1
     * TextUtils.lastIndexOf("aabaabaa", 'a', 7) = 7
     * TextUtils.lastIndexOf("aabaabaa", 'b', 7) = 5
     * TextUtils.lastIndexOf("aabaabaa", 'b', 4) = 2
     * TextUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
     * </pre>
     *
     * @param t      the CharSequence to check, may be null
     * @param toFind the character to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(Text t, char toFind, int start) {
        if (isEmpty(t) || start < 0) {
            return NOT_FOUND;
        }

        int tLen = t.getLength();

        if (start >= tLen) {
            start = tLen - 1;
        }

        byte[] tb = t.getBytes();
        for (int i = start; i >= 0; i--) {
            if (tb[i] == toFind) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * <p>Finds the last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOf(null, *)          = -1
     * TextUtils.lastIndexOf(*, null)          = -1
     * TextUtils.lastIndexOf("", "")           = 0
     * TextUtils.lastIndexOf("aabaabaa", "a")  = 7
     * TextUtils.lastIndexOf("aabaabaa", "b")  = 5
     * TextUtils.lastIndexOf("aabaabaa", "ab") = 4
     * TextUtils.lastIndexOf("aabaabaa", "")   = 8
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the Text to find, may be null
     * @return the last index of the search Text,
     * -1 if no match or {@code null} string input
     */
    public static int lastIndexOf(Text t, Text toFind) {
        if (t == null || toFind == null) {
            return NOT_FOUND;
        }

        if (t.getLength() == 0 && toFind.getLength() == 0) {
            return 0;
        }

        return lastIndexOf(t, toFind, t.getLength() - 1);
    }

    /**
     * <p>Finds the last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A negative start position returns {@code -1}.
     * An empty ("") search Text always matches unless the start position is negative.
     * A start position greater than the string length searches the whole string.
     * The search starts at the startPos and works backwards; matches starting after the start
     * position are ignored.
     * </p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOf(null, *, *)          = -1
     * TextUtils.lastIndexOf(*, null, *)          = -1
     * TextUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
     * TextUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
     * TextUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
     * TextUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
     * TextUtils.lastIndexOf("aabaabaa", "b", -1) = -1
     * TextUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
     * TextUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
     * TextUtils.lastIndexOf("aabaabaa", "b", 1)  = -1
     * TextUtils.lastIndexOf("aabaabaa", "b", 2)  = 2
     * TextUtils.lastIndexOf("aabaabaa", "ba", 2)  = -1
     * TextUtils.lastIndexOf("aabaabaa", "ba", 2)  = 2
     * </pre>
     *
     * @param t      the Text to check, may be null
     * @param toFind the Text to find, may be null
     * @param start  the start position, negative treated as zero
     * @return the last index of the search Text (always &le; startPos),
     * -1 if no match or {@code null} string input
     */
    public static int lastIndexOf(Text t, Text toFind, int start) {
        if (t == null || toFind == null || start < 0) {
            return NOT_FOUND;
        }

        int tLen = t.getLength();
        int toFindLen = toFind.getLength();

        if (start > tLen - toFindLen) {
            start = tLen - toFindLen - 1;
        }

        // Catch some special cases up front
        if (t == toFind) {
            return (start == tLen ? 0 : NOT_FOUND);
        } else if (tLen == 0) {
            return (toFindLen == 0 ? 0 : NOT_FOUND);
        } else if (tLen < toFindLen || start + 1 < toFindLen) {
            return NOT_FOUND;
        } else if (toFindLen == 0) {
            return start + 1;
        }

        byte[] tb = t.getBytes();
        byte[] toFindB = toFind.getBytes();

        for (int i = start; i >= 0; i--) {
            if (tb[i] == toFindB[0]) {
                boolean flag = true;
                for (int j = 1; j < toFindLen; j++) {
                    if (tb[i + j] != toFindB[j]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * <p>Finds the n-th last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * TextUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * TextUtils.lastOrdinalIndexOf("", "", *)           = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 1)  = 7
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 2)  = 6
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 1)  = 5
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 2)  = 2
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} string input
     */
    public static int lastOrdinalIndexOf(Text t, char toFind, int ordinal) {
        return lastOrdinalIndexOf(t, (int) toFind, ordinal);
    }

    /**
     * <p>Finds the n-th last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * TextUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * TextUtils.lastOrdinalIndexOf("", "", *)           = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 1)  = 7
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 2)  = 6
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 1)  = 5
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 2)  = 2
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} string input
     */
    public static int lastOrdinalIndexOf(Text t, int toFind, int ordinal) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }
        return lastOrdinalIndexOf(t, toFind, t.getLength() - 1, ordinal);
    }

    /**
     * <p>Finds the n-th last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * TextUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * TextUtils.lastOrdinalIndexOf("", "", *)           = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 7, 1)  = 7
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 7, 2)  = 6
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 3, 2)  = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 7, 1)  = 5
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 7, 2)  = 2
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 2, 2)  = -1
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the character to find
     * @param start   the start position
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} string input
     */
    public static int lastOrdinalIndexOf(Text t, int toFind, int start, int ordinal) {
        if (isEmpty(t) || ordinal <= 0 || ordinal >= t.getLength()) {
            return NOT_FOUND;
        }

        int nextStart = start;
        for (int i = 0; i < ordinal; i++) {
            nextStart = lastIndexOf(t, toFind, nextStart);
            if (nextStart == NOT_FOUND) {
                return NOT_FOUND;
            }
            nextStart--;
        }

        return nextStart + 1;
    }

    /**
     * <p>Finds the n-th last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * TextUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * TextUtils.lastOrdinalIndexOf("", "", *)           = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the Text to find, may be null
     * @param ordinal the n-th last {@code searchStr} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
    public static int lastOrdinalIndexOf(Text t, Text toFind, int ordinal) {
        if (t == null || toFind == null || ordinal <= 0) {
            return NOT_FOUND;
        }

        // Catch some special cases up front
        if (t.getLength() == 0 && toFind.getLength() == 0) {
            return 0;
        }

        return lastOrdinalIndexOf(t, toFind, t.getLength() - 1, ordinal);
    }

    /**
     * <p>Finds the n-th last index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.lastOrdinalIndexOf(null, *, *)          = -1
     * TextUtils.lastOrdinalIndexOf(*, null, *)          = -1
     * TextUtils.lastOrdinalIndexOf("", "", *)           = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
     * TextUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
     * </pre>
     *
     * @param t       the Text to check, may be null
     * @param toFind  the Text to find, may be null
     * @param start   the start position
     * @param ordinal the n-th last {@code searchStr} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} string input
     */
    public static int lastOrdinalIndexOf(Text t, Text toFind, int start, int ordinal) {
        if (t == null || toFind == null || ordinal <= 0) {
            return NOT_FOUND;
        }

        int nextStart = start;
        for (int i = 0; i < ordinal; i++) {
            nextStart = lastIndexOf(t, toFind, nextStart);
            if (nextStart == NOT_FOUND) {
                return NOT_FOUND;
            }
            nextStart -= 1;
        }

        return nextStart + 1;
    }

    /**
     * <p>Checks if Text contains a search String, handling {@code null}.
     * This method uses {@link Text#find(String)}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.contains(null, *)     = false
     * TextUtils.contains(*, null)     = false
     * TextUtils.contains("", "")      = true
     * TextUtils.contains("abc", "")   = true
     * TextUtils.contains("abc", "a")  = true
     * TextUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param in     the Text to check, may be null
     * @param toFind the String to find, may be null
     * @return true if the Text contains the search Text,
     * false if not or {@code null} string input
     */
    public static boolean contains(Text in, String toFind) {
        return (in.find(toFind) != NOT_FOUND);
    }

    /**
     * <p>Checks if Text contains a search Text, handling {@code null}.
     * This method uses {@link Text#find(String)}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.contains(null, *)     = false
     * TextUtils.contains(*, null)     = false
     * TextUtils.contains("", "")      = true
     * TextUtils.contains("abc", "")   = true
     * TextUtils.contains("abc", "a")  = true
     * TextUtils.contains("abc", "z")  = false
     * </pre>
     *
     * @param in     the Text to check, may be null
     * @param toFind the Text to find, may be null
     * @return true if the Text contains the search Text,
     * false if not or {@code null} string input
     */
    public static boolean contains(Text in, Text toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }

    /**
     * <p>Checks if Text contains a search character, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.contains(null, *)    = false
     * TextUtils.contains("", *)      = false
     * TextUtils.contains("abc", 'a') = true
     * TextUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param in     the Text to check, may be null
     * @param toFind the character to find
     * @return true if the Text contains the search character,
     * false if not or {@code null} string input
     */
    public static boolean contains(Text in, int toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }


    /**
     * <p>Checks if Text contains a search character, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.contains(null, *)    = false
     * TextUtils.contains("", *)      = false
     * TextUtils.contains("abc", 'a') = true
     * TextUtils.contains("abc", 'z') = false
     * </pre>
     *
     * @param in     the Text to check, may be null
     * @param toFind the character to find
     * @return true if the Text contains the search character,
     * false if not or {@code null} string input
     */
    public static boolean contains(Text in, char toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }

    /**
     * Check whether the given Text contains any whitespace characters.
     *
     * @param in the Text to check (may be {@code null})
     * @return {@code true} if the Text is not empty and
     * contains at least 1 whitespace character
     * @see java.lang.Character#isWhitespace
     */
    public static boolean containsWhitespace(Text in) {
        if (isEmpty(in)) {
            return false;
        }

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        for (int i = 0; i < inLen; i++) {
            if (Character.isWhitespace(inB[i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * <p>Search a Text to find the first index of any
     * character in the given set of characters.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A {@code null} or zero length search array will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOfAny(null, *)                = -1
     * TextUtils.indexOfAny("", *)                  = -1
     * TextUtils.indexOfAny(*, null)                = -1
     * TextUtils.indexOfAny(*, [])                  = -1
     * TextUtils.indexOfAny("zzabyycdxx",['z','a']) = 0
     * TextUtils.indexOfAny("zzabyycdxx",['b','y']) = 3
     * TextUtils.indexOfAny("aba", ['z'])           = -1
     * </pre>
     *
     * @param in          the Text to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     */
    public static int indexOfAny(Text in, final char[] searchChars) {
        if (isEmpty(in) || searchChars == null || searchChars.length == 0) {
            return NOT_FOUND;
        }

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        int inLast = inLen - 1;
        int searchLen = searchChars.length;
        int searchLast = searchLen - 1;

        for (int i = 0; i < inLen; i++) {
            char ch = (char) inB[i];
            for (int j = 0; j < searchLen; j++) {
                if (ch == searchChars[j]) {
                    if (i < inLast && j < searchLast && Character.isHighSurrogate(ch)) {
                        // ch is a supplementary character
                        if (searchChars[j + 1] == inB[i + 1]) {
                            return i;
                        }
                    } else {
                        return i;
                    }
                }
            }
        }

        return NOT_FOUND;
    }

    /**
     * <p>Checks if the Text contains any character in the given
     * set of characters.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code false}.
     * A {@code null} or zero length search array will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.containsAny(null, *)                = false
     * TextUtils.containsAny("", *)                  = false
     * TextUtils.containsAny(*, null)                = false
     * TextUtils.containsAny(*, [])                  = false
     * TextUtils.containsAny("zzabyycdxx",['z','a']) = true
     * TextUtils.containsAny("zzabyycdxx",['b','y']) = true
     * TextUtils.containsAny("aba", ['z'])           = false
     * </pre>
     *
     * @param in          the Text to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the {@code true} if any of the chars are found,
     * {@code false} if no match or null input
     */
    public static boolean containsAny(Text in, final char[] searchChars) {
        return (indexOfAny(in, searchChars) != -1);
    }

    /**
     * <p>Searches a Text to find the first index of any
     * character not in the given set of characters.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A {@code null} or zero length search array will return {@code -1}.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOfAnyBut(null, *)                              = -1
     * TextUtils.indexOfAnyBut("", *)                                = -1
     * TextUtils.indexOfAnyBut(*, null)                              = -1
     * TextUtils.indexOfAnyBut(*, [])                                = -1
     * TextUtils.indexOfAnyBut("zzabyycdxx", new char[] {'z', 'a'} ) = 3
     * TextUtils.indexOfAnyBut("aba", new char[] {'z'} )             = 0
     * TextUtils.indexOfAnyBut("aba", new char[] {'a', 'b'} )        = -1
     *
     * </pre>
     *
     * @param in          the Text to check, may be null
     * @param searchChars the chars to search for, may be null
     * @return the index of any of the chars, -1 if no match or null input
     */
    public static int indexOfAnyBut(Text in, final char[] searchChars) {
        if (isEmpty(in) || searchChars == null || searchChars.length == 0) {
            return NOT_FOUND;
        }

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        int inLast = inLen - 1;
        int searchLen = searchChars.length;
        int searchLast = searchLen - 1;

        for (int i = 0; i < inLen; i++) {
            char ch = (char) inB[i];
            int j = 0;
            for (j = 0; j < searchLen; j++) {
                if (ch == searchChars[j]) {
                    if (i < inLast && j < searchLast && Character.isHighSurrogate(ch)) {
                        // ch is a supplementary character
                        if (searchChars[j + 1] == inB[i + 1]) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }

            if (j == searchLen) {
                return i;
            }

        }

        return NOT_FOUND;
    }

    /**
     * <p>Checks if the Text contains only certain characters.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code false}.
     * A {@code null} valid character array will return {@code false}.
     * An empty Text (length()=0) always returns {@code true}.</p>
     * <p/>
     * <pre>
     * TextUtils.containsOnly(null, *)       = false
     * TextUtils.containsOnly(*, null)       = false
     * TextUtils.containsOnly("", *)         = true
     * TextUtils.containsOnly("ab", '')      = false
     * TextUtils.containsOnly("abab", 'abc') = true
     * TextUtils.containsOnly("ab1", 'abc')  = false
     * TextUtils.containsOnly("abz", 'abc')  = false
     * </pre>
     *
     * @param in          the Text to check, may be null
     * @param searchChars an array of valid chars, may be null
     * @return true if it only contains valid chars and is non-null
     */
    public static boolean containsOnly(Text in, final char[] searchChars) {
        if (in == null || searchChars == null) {
            return false;
        } else if (in.getLength() == 0) {
            return true;
        } else if (searchChars.length == 0) {
            return false;
        }

        return (indexOfAnyBut(in, searchChars) == NOT_FOUND);
    }

    /**
     * <p>Checks that the Text does not contain certain characters.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code true}.
     * A {@code null} invalid character array will return {@code true}.
     * An empty Text (length()=0) always returns true.</p>
     * <p/>
     * <pre>
     * TextUtils.containsNone(null, *)       = true
     * TextUtils.containsNone(*, null)       = true
     * TextUtils.containsNone("", *)         = true
     * TextUtils.containsNone("ab", '')      = true
     * TextUtils.containsNone("abab", 'xyz') = true
     * TextUtils.containsNone("ab1", 'xyz')  = true
     * TextUtils.containsNone("abz", 'xyz')  = false
     * </pre>
     *
     * @param in          the Text to check, may be null
     * @param searchChars an array of invalid chars, may be null
     * @return true if it contains none of the invalid chars, or is null
     */
    public static boolean containsNone(Text in, final char[] searchChars) {
        if (in == null || searchChars == null) {
            return true;
        } else if (in.getLength() == 0) {
            return true;
        } else if (searchChars.length == 0) {
            return true;
        }

        return (indexOfAny(in, searchChars) == NOT_FOUND);
    }

    /**
     * <p>Find the first index of any of a set of potential subtexts.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A {@code null} or zero length search array will return {@code -1}.
     * A {@code null} search array entry will be ignored, but a search
     * array containing "" will return {@code 0} if {@code str} is not
     * null.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOfAny(null, *)                     = -1
     * TextUtils.indexOfAny(*, null)                     = -1
     * TextUtils.indexOfAny(*, [])                       = -1
     * TextUtils.indexOfAny("zzabyycdxx", ["ab","cd"])   = 2
     * TextUtils.indexOfAny("zzabyycdxx", ["cd","ab"])   = 2
     * TextUtils.indexOfAny("zzabyycdxx", ["mn","op"])   = -1
     * TextUtils.indexOfAny("zzabyycdxx", ["zab","aby"]) = 1
     * TextUtils.indexOfAny("zzabyycdxx", [""])          = 0
     * TextUtils.indexOfAny("", [""])                    = 0
     * TextUtils.indexOfAny("", ["a"])                   = -1
     * </pre>
     *
     * @param in         the Text to check, may be null
     * @param searchText the Text to search for, may be null
     * @return the first index of any of the searchTexts in str, -1 if no match
     */
    public static int indexOfAny(Text in, final Text[] searchText) {
        if (in == null || searchText == null || searchText.length == 0) {
            return NOT_FOUND;
        }

        int ret = Integer.MAX_VALUE;

        for (Text search : searchText) {
            if (search == null) {
                continue;
            }

            int tmp = indexOf(in, search);
            if (tmp != NOT_FOUND && tmp < ret) {
                ret = tmp;
            }
        }

        return (ret == Integer.MAX_VALUE ? NOT_FOUND : ret);
    }

    /**
     * <p>Find the latest index of any of a set of potential substrings.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A {@code null} search array will return {@code -1}.
     * A {@code null} or zero length search array entry will be ignored,
     * but a search array containing "" will return the length of {@code str}
     * if {@code str} is not null.</p>
     * <p/>
     * <pre>
     * TextUtils.lastIndexOfAny(null, *)                   = -1
     * TextUtils.lastIndexOfAny(*, null)                   = -1
     * TextUtils.lastIndexOfAny(*, [])                     = -1
     * TextUtils.lastIndexOfAny(*, [null])                 = -1
     * TextUtils.lastIndexOfAny("zzabyycdxx", ["ab","cd"]) = 6
     * TextUtils.lastIndexOfAny("zzabyycdxx", ["cd","ab"]) = 6
     * TextUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
     * TextUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
     * TextUtils.lastIndexOfAny("zzabyycdxx", ["mn",""])   = 10
     * </pre>
     *
     * @param in         the Text to check, may be null
     * @param searchText the Text to search for, may be null
     * @return the last index of any of the Text, -1 if no match
     */
    public static int lastIndexOfAny(Text in, final Text[] searchText) {
        if (in == null || searchText == null || searchText.length == 0) {
            return NOT_FOUND;
        }

        int ret = NOT_FOUND;

        for (Text search : searchText) {
            if (search == null) {
                continue;
            }

            int tmp = lastIndexOf(in, search);
            if (tmp > ret) {
                ret = tmp;
            }
        }

        return ret;
    }

    /**
     * <p>Gets a subtext from the specified Text.</p>
     * <p/>
     * <p>A negative start position can be used to start {@code n}
     * characters from the end of the String.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code null}.
     * An empty ("") Text will return "".</p>
     * <p/>
     * <pre>
     * TextUtils.substring(null, *)   = null
     * TextUtils.substring("", *)     = ""
     * TextUtils.substring("abc", 0)  = "abc"
     * TextUtils.substring("abc", 2)  = "c"
     * TextUtils.substring("abc", 4)  = ""
     * TextUtils.substring("abc", -2) = "bc"
     * TextUtils.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param in    the String to get the substring from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the Text by this many characters
     * @param out   where the subtext will be stored. On cases where null is returned,
     *              the {@code out} is set cleared to be ""
     * @return substring from start position, {@code null} if null Text input.
     * this same output is stored in out
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtext(Text in, int start, Text out) {

        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        }

        if (start < 0) {
            start = in.getLength() + start;
        }

        if (start < 0) {
            start = 0;
        }

        if (start >= in.getLength()) {
            out.set(EMPTY);
        } else {
            byte[] inb = in.getBytes();
            out.set(inb, start, in.getLength() - start);
        }

        return out;
    }

    /**
     * <p>Gets a subtext from the specified Text</p>
     * <p/>
     * <p>A negative start position can be used to start/end {@code n}
     * characters from the end of the Text.</p>
     * <p/>
     * <p>The returned subtext starts with the character in the {@code start}
     * position and ends before the {@code end} position. All position counting is
     * zero-based -- i.e., to start at the beginning of the string use
     * {@code start = 0}. Negative start and end positions can be used to
     * specify offsets relative to the end of the String.</p>
     * <p/>
     * <p>If {@code start} is not strictly to the left of {@code end}, ""
     * is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.substring(null, *, *)    = null
     * TextUtils.substring("", * ,  *)    = "";
     * TextUtils.substring("abc", 0, 2)   = "ab"
     * TextUtils.substring("abc", 2, 0)   = ""
     * TextUtils.substring("abc", 2, 4)   = "c"
     * TextUtils.substring("abc", 4, 6)   = ""
     * TextUtils.substring("abc", 2, 2)   = ""
     * TextUtils.substring("abc", -2, -1) = "b"
     * TextUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param in    the String to get the substring from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the String by this many characters
     * @param end   the position to end at (exclusive), negative means
     *              count back from the end of the String by this many characters
     * @param out   where the subtext will be stored. On cases where null is returned,
     *              the {@code out} is set cleared to be ""
     * @return substring from start position to end position,
     * {@code null} if null String input. this same output is stored in out
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtext(final Text in, int start, int end, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        }

        if (end < 0) {
            end = in.getLength() + end;
        }

        if (start < 0) {
            start = in.getLength() + start;
        }

        if (end > in.getLength()) {
            end = in.getLength();
        }

        if (start > end) {
            out.set(EMPTY);
        } else {

            if (start < 0) {
                start = 0;
            }

            if (end < 0) {
                end = 0;
            }

            byte[] inb = in.getBytes();
            out.set(inb, start, end - start);

        }

        return out;
    }

    /**
     * <p>Gets the leftmost {@code len} characters of a Text.</p>
     * <p/>
     * <p>If {@code len} characters are not available, or the
     * Text is {@code null}, the Text will be returned without
     * an exception. An empty Text is returned if len is negative.</p>
     * <p/>
     * <pre>
     * TextUtils.left(null, *)    = null
     * TextUtils.left(*, -ve)     = ""
     * TextUtils.left("", *)      = ""
     * TextUtils.left("abc", 0)   = ""
     * TextUtils.left("abc", 2)   = "ab"
     * TextUtils.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param in  the Text to get the leftmost characters from, may be null
     * @param len the length of the required Text
     * @param out where the subtext will be stored. On cases where null is returned,
     *            the {@code out} is set cleared to be ""
     * @return the leftmost characters, {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text left(final Text in, int len, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        }

        if (len <= 0) {
            out.set(EMPTY);
        } else if (in.getLength() <= len) {
            out.set(in);
        } else {
            out.set(in.getBytes(), 0, len);
        }

        return out;
    }

    /**
     * <p>Gets the rightmost {@code len} characters of a Text.</p>
     * <p/>
     * <p>If {@code len} characters are not available, or the Text
     * is {@code null}, the Text will be returned without an
     * an exception. An empty Text is returned if len is negative.</p>
     * <p/>
     * <pre>
     * TextUtils.right(null, *)    = null
     * TextUtils.right(*, -ve)     = ""
     * TextUtils.right("", *)      = ""
     * TextUtils.right("abc", 0)   = ""
     * TextUtils.right("abc", 2)   = "bc"
     * TextUtils.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param in  the String to get the rightmost characters from, may be null
     * @param len the length of the required String
     * @param out where the subtext will be stored. On cases where null is returned,
     *            the {@code out} is set cleared to be ""
     * @return the rightmost characters, {@code null} if null String input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text right(final Text in, int len, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        }

        if (len <= 0) {
            out.set(EMPTY);
        } else if (in.getLength() <= len) {
            out.set(in);
        } else {
            out.set(in.getBytes(), in.getLength() - len, len);
        }

        return out;
    }

    /**
     * <p>Gets {@code len} characters from the middle of a Text.</p>
     * <p/>
     * <p>If {@code len} characters are not available, the remainder
     * of the Text will be returned without an exception. If the
     * Text is {@code null}, {@code null} will be returned.
     * An empty Text is returned if len is negative or exceeds the
     * length of {@code str}.</p>
     * <p/>
     * <pre>
     * TextUtils.mid(null, *, *)    = null
     * TextUtils.mid(*, *, -ve)     = ""
     * TextUtils.mid("", 0, *)      = ""
     * TextUtils.mid("abc", 0, 2)   = "ab"
     * TextUtils.mid("abc", 0, 4)   = "abc"
     * TextUtils.mid("abc", 2, 4)   = "c"
     * TextUtils.mid("abc", 4, 2)   = ""
     * TextUtils.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param in  the Text to get the characters from, may be null
     * @param pos the position to start from, negative treated as zero
     * @param len the length of the required Text
     * @param out where the subtext will be stored. On cases where null is returned,
     *            the {@code out} is set cleared to be ""
     * @return the middle characters, {@code null} if null String input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text middle(final Text in, int pos, int len, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        }

        if (len <= 0 || pos > in.getLength()) {
            out.set(EMPTY);
        } else {

            if (pos < 0) {
                pos = 0;
            }

            if (in.getLength() <= pos + len) {
                out.set(in.getBytes(), pos, in.getLength() - pos);
            } else {
                out.set(in.getBytes(), pos, len);
            }
        }

        return out;
    }

    /**
     * <p>Gets the subtext before the first occurrence of a separator.
     * The separator is not returned.</p>
     * <p/>
     * <p>A {@code null} string input will return {@code null}.
     * An empty ("") Text input will return the empty Text.
     * A {@code null} separator will return the input Text.</p>
     * <p/>
     * <p>If nothing is found, the Text input is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.substringBefore(null, *)      = null
     * TextUtils.substringBefore("", *)        = ""
     * TextUtils.substringBefore("abc", "a")   = ""
     * TextUtils.substringBefore("abcba", "b") = "a"
     * TextUtils.substringBefore("abc", "c")   = "ab"
     * TextUtils.substringBefore("abc", "d")   = "abc"
     * TextUtils.substringBefore("abc", "")    = ""
     * TextUtils.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param in        the Text to get a substring from, may be null
     * @param separator the Text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the substring before the first occurrence of the separator,
     * {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBefore(final Text in, final Text separator, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        } else if (in.getLength() == 0 || separator == null) {
            out.set(in);
        } else if (separator.getLength() == 0) {
            out.set(EMPTY);
        } else {
            final int pos = TextUtils.indexOf(in, separator);
            if (pos == NOT_FOUND) {
                out.set(in);
            } else {
                out.set(in.getBytes(), 0, pos);
            }
        }

        return out;
    }

    /**
     * <p>Gets the subtext after the first occurrence of a separator.
     * The separator is not returned.</p>
     * <p/>
     * <p>A {@code null} Text input will return {@code null}.
     * An empty ("") Text input will return the empty Text.
     * A {@code null} separator will return the empty Text if the
     * input Text is not {@code null}.</p>
     * <p/>
     * <p>If nothing is found, the empty Text is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.substringAfter(null, *)      = null
     * TextUtils.substringAfter("", *)        = ""
     * TextUtils.substringAfter(*, null)      = ""
     * TextUtils.substringAfter("abc", "a")   = "bc"
     * TextUtils.substringAfter("abcba", "b") = "cba"
     * TextUtils.substringAfter("abc", "c")   = ""
     * TextUtils.substringAfter("abc", "d")   = ""
     * TextUtils.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param in        the Text to get a substring from, may be null
     * @param separator the Text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the substring after the first occurrence of the separator,
     * {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextAfter(final Text in, final Text separator, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        } else if (in.getLength() == 0 || separator == null) {
            out.set(EMPTY);
        } else if (separator.getLength() == 0) {
            out.set(in);
        } else {
            int pos = TextUtils.indexOf(in, separator);
            if (pos == NOT_FOUND) {
                out.set(EMPTY);
            } else {
                int sLen = separator.getLength();
                pos += sLen;
                out.set(in.getBytes(), pos, in.getLength() - pos);
            }
        }

        return out;
    }

    /**
     * <p>Gets the subtext before the last occurrence of a separator.
     * The separator is not returned.</p>
     * <p/>
     * <p>A {@code null} Text input will return {@code null}.
     * An empty ("") Text input will return the empty Text.
     * An empty or {@code null} separator will return the input Text.</p>
     * <p/>
     * <p>If nothing is found, the Text input is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.substringBeforeLast(null, *)      = null
     * TextUtils.substringBeforeLast("", *)        = ""
     * TextUtils.substringBeforeLast("abcba", "b") = "abc"
     * TextUtils.substringBeforeLast("abc", "c")   = "ab"
     * TextUtils.substringBeforeLast("a", "a")     = ""
     * TextUtils.substringBeforeLast("a", "z")     = "a"
     * TextUtils.substringBeforeLast("a", null)    = "a"
     * TextUtils.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param in        the Text to get a subtext from, may be null
     * @param separator the Text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the subtext before the last occurrence of the separator,
     * {@code null} if null String input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBeforeLast(final Text in, final Text separator, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        } else if (in.getLength() == 0) {
            out.set(EMPTY);
        } else if (isEmpty(separator)) {
            out.set(in);
        } else {
            final int pos = lastIndexOf(in, separator);
            if (pos == NOT_FOUND) {
                out.set(in);
            } else {
                out.set(in.getBytes(), 0, pos);
            }
        }

        return out;
    }

    /**
     * <p>Gets the subtext after the last occurrence of a separator.
     * The separator is not returned.</p>
     * <p/>
     * <p>A {@code null} text input will return {@code null}.
     * An empty ("") text input will return the empty text.
     * An empty or {@code null} separator will return the empty text if
     * the input text is not {@code null}.</p>
     * <p/>
     * <p>If nothing is found, the empty text is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.substringAfterLast(null, *)      = null
     * TextUtils.substringAfterLast("", *)        = ""
     * TextUtils.substringAfterLast(*, "")        = ""
     * TextUtils.substringAfterLast(*, null)      = ""
     * TextUtils.substringAfterLast("abc", "a")   = "bc"
     * TextUtils.substringAfterLast("abcba", "b") = "a"
     * TextUtils.substringAfterLast("abc", "c")   = ""
     * TextUtils.substringAfterLast("a", "a")     = ""
     * TextUtils.substringAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param in        the text to get a substring from, may be null
     * @param separator the text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the substring after the last occurrence of the separator,
     * {@code null} if null text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextAfterLast(final Text in, final Text separator, Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null) {
            out.clear();
            return null;
        } else if (in.getLength() == 0 || isEmpty(separator)) {
            out.set(EMPTY);
        } else {
            final int pos = lastIndexOf(in, separator);
            if (pos == NOT_FOUND) {
                out.set(in);
            } else {
                out.set(in.getBytes(), pos + 1, in.getLength() - pos - 1);
            }
        }

        return out;
    }

    /**
     * <p>Gets the Text that is nested in between two instances of the
     * same Text.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.
     * A {@code null} tag returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.substringBetween(null, *)            = null
     * TextUtils.substringBetween("", "")             = ""
     * TextUtils.substringBetween("", "tag")          = null
     * TextUtils.substringBetween("tagabctag", null)  = null
     * TextUtils.substringBetween("tagabctag", "")    = ""
     * TextUtils.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param in  the Text containing the subtext, may be null
     * @param tag the Text before and after the subtext, may be null
     * @param out where the subtext will be stored. On cases where null is returned,
     *            the {@code out} is set cleared to be ""
     * @return the Text, {@code null} if no match
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBetween(final Text in, final Text tag, Text out) {
        return subtextBetween(in, tag, tag, out);
    }

    /**
     * <p>Gets the Text that is nested in between two Texts.
     * Only the first match is returned.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.
     * A {@code null} open/close returns {@code null} (no match).
     * An empty ("") open and close returns an empty Text.</p>
     * <p/>
     * <pre>
     * TextUtils.substringBetween("wx[b]yz", "[", "]") = "b"
     * TextUtils.substringBetween(null, *, *)          = null
     * TextUtils.substringBetween(*, null, *)          = null
     * TextUtils.substringBetween(*, *, null)          = null
     * TextUtils.substringBetween("", "", "")          = ""
     * TextUtils.substringBetween("", "", "]")         = null
     * TextUtils.substringBetween("", "[", "]")        = null
     * TextUtils.substringBetween("yabcz", "", "")     = ""
     * TextUtils.substringBetween("yabcz", "y", "z")   = "abc"
     * TextUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param in    the Text containing the subtext, may be null
     * @param open  the Text before the subtext, may be null
     * @param close the Text after the subtext, may be null
     * @param out   where the subtext will be stored. On cases where null is returned,
     *              the {@code out} is set cleared to be ""
     * @return the subtext, {@code null} if no match
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBetween(final Text in, final Text open, final Text close, final Text out) {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        } else if (in == null || open == null || close == null) {
            out.clear();
            return null;
        }
        final int start = indexOf(in, open);
        if (start != NOT_FOUND) {
            final int end = indexOf(in, close, start + open.getLength());
            if (end != NOT_FOUND) {
                return TextUtils.subtext(in, start + open.getLength(), end, out);
            }
        }
        out.clear();
        return null;
    }

    public static Text concat(Text t1, Text t2) {
        t1.append(t2.getBytes(), 0, t2.getLength());
        return t1;
    }
}
