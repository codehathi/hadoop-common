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
 * <p>{@code TextUtils} handles {@code null} input Texts quietly.
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
 * <p>Only a handful of methods throw exceptions.  The only time this happens is when the
 * parameter that's to be used for output is {@code null}.</p>
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
    public static final int INVALID_BYTE = -2;

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

    /**
     * Returns the index to the first byte that's not in the CHARTYPE starting
     * from the start of the array
     *
     * @param inb      input bytes from Text object
     * @param start    where to start searching
     * @param length   how long is the Text object.  Important: this
     *                 needs to be the length of the Text, not the byte length
     *                 That is, {@link org.apache.hadoop.io.Text#getLength()} and not
     *                 {@code inb.length}.
     * @param charType Type of char to skip over
     * @return index to the first byte from the front that's not a CHARTYPE
     */
    private static int getStartSpot(final byte[] inb, final int start, final int length, final CHARTYPE charType) {

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

    /**
     * Returns the index to the first byte that's not in the CHARTYPE starting
     * from the end of the array
     *
     * @param inb      input bytes from Text object
     * @param start    where to start searching
     * @param length   how long is the Text object.  Important: this
     *                 needs to be the length of the Text, not the byte length
     *                 That is, {@link org.apache.hadoop.io.Text#getLength()} and not
     *                 {@code inb.length}.
     * @param charType Type of char to skip over
     * @return index to the first byte from the back that's not a CHARTYPE
     */
    private static int getLastSpot(final byte[] inb, final int start, final int length, final CHARTYPE charType) {

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

    /**
     * Removes the characters of type CHARTYPE from the front/back of the text object.
     * This is an in-place operation
     *
     * @param in       Text object to remove characters from
     * @param charType what type of characters to remove
     * @return the updated Text object (same as {@code in}
     */
    private static Text removeFrontBackChars(Text in, final CHARTYPE charType) {
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
     * <p>This operation is not in place</p>
     * <p/>
     * <pre>
     * TextUtils.trim(null)          = null
     * TextUtils.trim("")            = ""
     * TextUtils.trim("     ")       = ""
     * TextUtils.trim("abc")         = "abc"
     * TextUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param in  the Text to be trimmed, may be null
     * @param out the result of the trimming, may not be null
     * @return the trimmed Text, {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text trim(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (in == null) {
            out.clear();
            return null;
        }

        out.set(in);

        return removeFrontBackChars(out, CHARTYPE.CONTROL);
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
    public static Text trim(Text in) { return removeFrontBackChars(in, CHARTYPE.CONTROL); }

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
     * <p>This operation does not edit the {@code in} parameter</p>
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
     * @param in  the Text to remove whitespace from, may be null
     * @param out the result of the stripping, may not be null
     * @return the stripped Text, {@code null} if null Text input
     */
    public static Text strip(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (in == null) {
            out.clear();
            return null;
        }

        out.set(in);

        return removeFrontBackChars(out, CHARTYPE.WHITESPACE);
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
    public static boolean equals(final Text str1, final Text str2) {
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
    public static boolean equalsIgnoreCase(final Text str1, final Text str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null || str2 == null) {
            return false;
        } else if (str1 == str2) {
            return true;
        } else if (str1.getLength() != str2.getLength()) {
            return false;
        } else {
            int str1Len = str1.getLength();
            byte[] str1Bytes = str1.getBytes();
            byte[] str2Bytes = str2.getBytes();

            int numBytes1 = 1;
            int numBytes2 = 1;

            for (int i = 0; i < str1Len; i += numBytes1) {
                numBytes1 = getNumBytesWithStartingByte(str1Bytes[i]);
                int ch1 = bytesToUnicodeInt(str1Bytes, i, numBytes1);

                numBytes2 = getNumBytesWithStartingByte(str2Bytes[i]);
                int ch2 = bytesToUnicodeInt(str2Bytes, i, numBytes2);

                if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Get the number of UTF-8 octets needed for the supplied unicode codepoint
     *
     * @param c
     * @return num octets
     */
    private static int getNumCharacterOctets(final int c) {
        if (c >= 0 && c < 0x80) {
            return 1;
        } else if (c >= 0x0080 && c <= 0x07FF) {
            return 2;
        } else if (c >= 0x0800 && c <= 0xFFFF) {
            return 3;
        } else if (c >= 0x10000 && c <= 0x1FFFFF) {
            return 4;
        } else if (c >= 0x200000 && c <= 0x3FFFFFF) {
            return 5; // should never get here
        } else if (c >= 0x4000000 && c <= 0x7FFFFFFF) {
            return 6; // should never get here
        } else {
            return INVALID_BYTE; // should never get here
        }
    }

    public static int getNumBytesWithStartingByte(final byte in) {
        int b = (int) in & 0xFF;
        if (b >= 0 && b < 0x80) {
            return 1;
        } else if (b >= 0xC2 && b <= 0xDF) {
            return 2;
        } else if (b >= 0xE0 && b <= 0xEF) {
            return 3;
        } else if (b >= 0xF0 && b <= 0xF4) {
            return 4;
        }

        // too long! Longest valid UTF-8 is 4 bytes (lead + three)
        // or if < 0 we got a trail byte in the lead byte position

        return INVALID_BYTE;
    }

    /**
     * <p>Returns the unicode scalar value for the character at idx.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return -1.</p>
     * <p>NOTE: This should return the same as {@link Text#charAt(int)}.  This function
     * exists because everytime {@link Text#charAt(int)} is called, it allocates
     * a new HeapByteBuffer so this function is to get around the need to create
     * many objects.  This just converts the bytes directly.  Only tested
     * on machine, so not sure if big and little endian come into play here.</p>
     * <p/>
     * <pre>
     * TextUtils.indexOf(null, *)         = -1
     * TextUtils.indexOf("", *)           = -1
     * TextUtils.indexOf("aabaabaa", 'a') = 0
     * TextUtils.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param in  the Text to check, may be null
     * @param idx the index of the character
     * @return Unicode scalar value for character at {@code idx}, -1 if invalid idx,
     * points to trailing byte, or {@code null} Text input
     */
    public static int charAt(final Text in, final int idx) {
        if (in == null || idx >= in.getLength() || idx < 0) {
            return -1;
        }

        byte[] b = in.getBytes();

        int numBytes = getNumBytesWithStartingByte(b[idx]);


        return bytesToUnicodeInt(b, idx, numBytes);
    }

    public static int bytesToUnicodeInt(final byte[] b, final int idx, final int numBytes) {
        if (b == null || numBytes <= 0) {
            return -1;
        }

        if (numBytes == 1) {
            return b[idx];
        }

        int ret = (~(0xFF00 >> numBytes) & b[idx]) & 0xFF;

        for (int i = 1; i < numBytes; i++) {
            ret = (ret << (6)) | (b[idx + i] & 0x3F);
        }

        return ret;
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
     * @param in     the Text to check, may be null
     * @param toFind the character to find
     * @return the first index of the search character, -1 if no match or {@code null} Text input
     */
    public static int indexOf(final Text in, final char toFind) {
        return indexOf(in, (int) toFind, 0);
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
     * @param in     the Text to check, may be null
     * @param toFind the character to find
     * @return the first index of the search character, -1 if no match or {@code null} Text input
     */
    public static int indexOf(final Text in, final int toFind) {
        return indexOf(in, toFind, 0);
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
     * @param toFind the byte to find
     * @return the first index of the search character, -1 if no match or {@code null} Text input
     */
    public static int indexOf(final Text t, final byte toFind) {
        return indexOf(t, toFind, 0);
    }

    /**
     * <p>Finds the first index within a Text from a start position, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code (NOT_FOUND) -1}.
     * A negative start position is treated as zero.
     * A start position greater than the text length returns {@code -1}.</p>
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
     * -1 if no match or {@code null} text input
     */
    public static int indexOf(final Text t, final char toFind, int start) {
        return indexOf(t, (int) toFind, start);
    }

    /**
     * <p>Finds the first index within a Text from a start position, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code (NOT_FOUND) -1}.
     * A negative start position is treated as zero.
     * A start position greater than the text length returns {@code -1}.</p>
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
     * @param toFind the byte to find
     * @param start  the start position, negative treated as zero
     * @return the first index of the search character (always &ge; startPos),
     * -1 if no match or {@code null} text input
     */
    public static int indexOf(final Text t, final byte toFind, int start) {
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
     * <p>Finds the first index within a Text from a start position, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} or empty ("") Text will return {@code (NOT_FOUND) -1}.
     * A negative start position is treated as zero.
     * A start position greater than the Text length returns {@code -1}.</p>
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
     * @param in     the Text to check, may be null
     * @param toFind the byte to find
     * @param start  the start position, negative treated as zero
     * @return the first index of the search character (always &ge; startPos),
     * -1 if no match or {@code null} Text input
     */
    public static int indexOf(final Text in, final int toFind, int start) {
        if (isEmpty(in) || toFind < 0 || start >= in.getLength()) {
            return NOT_FOUND;
        }

        if (start < 0) {
            start = 0;
        }

        int numOctets = getNumCharacterOctets(toFind);

        if (numOctets == INVALID_BYTE) {
            return NOT_FOUND;
        }

        if (numOctets == 1) {
            // this is the simple case, just have an ascii char
            return indexOf(in, (byte) toFind, start);
        }

        // This is the starting byte of the character in UTF-8
        byte startingCharOctet = (byte) ((0xFFFC << (6 - numOctets)) | (toFind >> (6 * (numOctets - 1))));

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        int idx = start;

        // Try to find the starting byte
        while ((idx = indexOf(in, startingCharOctet, idx)) != NOT_FOUND) {
            boolean found = true;

            // Go through the other UTF-8 encoded bytes looking for the rest of the char
            for (int i = 1; i < numOctets && idx + i < inLen; i++) {
                char byteChar = (char) (inB[idx + i] & 0x3F);
                char subOctetChar = (char) ((toFind >> (6 * (numOctets - i - 1))) & 0x3F);
                if (byteChar != subOctetChar) {
                    found = false;
                    idx += numOctets;
                    break;
                }
            }

            if (found) {
                return idx;
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
     * -1 if no match or {@code null} Text input
     */
    public static int indexOf(final Text t, final Text toFind) {
        return indexOf(t, toFind, 0);
    }

    /**
     * <p>Finds the first index within a Text, handling {@code null}.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code -1}.
     * A negative start position is treated as zero.
     * An empty ("") search Text always matches.
     * A start position greater than the Text length only matches
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
     * -1 if no match or {@code null} Text input
     */
    public static int indexOf(final Text t, final Text toFind, int start) {
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
     * @param in      the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th {@code searchStr} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int ordinalIndexOf(final Text in, final char toFind, final int ordinal) {
        return ordinalIndexOf(in, (int) toFind, 0, ordinal);
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
     * @param in      the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th {@code searchStr} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int ordinalIndexOf(final Text in, final int toFind, final int ordinal) {
        return ordinalIndexOf(in, toFind, 0, ordinal);
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
     * @param in      the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th {@code searchStr} to find
     * @return the n-th index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int ordinalIndexOf(final Text in, final int toFind, int start, final int ordinal) {
        if (TextUtils.isEmpty(in) || start >= in.getLength() || ordinal <= 0) {
            return TextUtils.NOT_FOUND;
        }

        if (start < 0) {
            start = 0;
        }

        int idx = start;
        for (int i = 0; i < ordinal; i++) {
            idx = indexOf(in, toFind, idx);
            if (idx == TextUtils.NOT_FOUND) {
                return TextUtils.NOT_FOUND;
            }
            idx += 1;
        }

        return idx - 1;
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
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int ordinalIndexOf(final Text t, final Text toFind, final int ordinal) {
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
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int ordinalIndexOf(final Text t, final Text toFind, int start, final int ordinal) {
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
     * @param in     the CharSequence to check, may be null
     * @param toFind the character to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(final Text in, final char toFind) {
        if (in == null) {
            return TextUtils.NOT_FOUND;
        }
        return lastIndexOf(in, (int) toFind, in.getLength() - 1);
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
     * @param toFind the byte to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(final Text t, final byte toFind) {
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
     * @param in     the CharSequence to check, may be null
     * @param toFind the char to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(final Text in, final int toFind) {
        if (in == null || toFind < 0) {
            return TextUtils.NOT_FOUND;
        }
        return lastIndexOf(in, toFind, in.getLength() - 1);
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
    public static int lastIndexOf(final Text t, final byte toFind, int start) {
        if (TextUtils.isEmpty(t) || start < 0) {
            return TextUtils.NOT_FOUND;
        } else if (start >= t.getLength()) {
            start = t.getLength() - 1;
        }

        byte[] tb = t.getBytes();
        for (int i = start; i >= 0; i--) {
            if (tb[i] == toFind) {
                return i;
            }
        }
        return TextUtils.NOT_FOUND;
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
     * @param in     the CharSequence to check, may be null
     * @param toFind the character to find
     * @return the last index of the search character,
     * -1 if no match or {@code null} text input
     */
    public static int lastIndexOf(final Text in, final int toFind, int start) {
        if (TextUtils.isEmpty(in) || toFind < 0 || start < 0) {
            return TextUtils.NOT_FOUND;
        }

        if (start >= in.getLength()) {
            start = in.getLength() - 1;
        }

        int numOctets = getNumCharacterOctets(toFind);

        if (numOctets == 1) {
            // this is the simple case, just have an ascii char
            return lastIndexOf(in, (byte) toFind, start);
        }

        // This is the starting byte of the character in UTF-8
        byte startingCharOctet = (byte) ((0xFFFC << (6 - numOctets)) | (toFind >> (6 * (numOctets - 1))));

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        int idx = start;

        // Try to find the starting byte
        while ((idx = lastIndexOf(in, startingCharOctet, idx)) != TextUtils.NOT_FOUND) {
            boolean found = true;

            // Go through the other UTF-8 encoded bytes looking for the rest of the char
            for (int i = 1; i < numOctets && idx + i < inLen; i++) {
                char byteChar = (char) (inB[idx + i] & 0x3F);
                char subOctetChar = (char) ((toFind >> (6 * (numOctets - i - 1))) & 0x3F);
                if (byteChar != subOctetChar) {
                    found = false;
                    idx += numOctets;
                    break;
                }
            }

            if (found) {
                return idx;
            }
        }

        return TextUtils.NOT_FOUND;
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
     * -1 if no match or {@code null} Text input
     */
    public static int lastIndexOf(final Text t, final Text toFind) {
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
     * A start position greater than the Text length searches the whole Text.
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
     * -1 if no match or {@code null} Text input
     */
    public static int lastIndexOf(final Text t, final Text toFind, int start) {
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
     * @param in      the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text in, final char toFind, final int ordinal) {
        if (TextUtils.isEmpty(in) || ordinal <= 0) {
            return TextUtils.NOT_FOUND;
        }

        return lastOrdinalIndexOf(in, (int) toFind, in.getLength() - 1, ordinal);
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
     * @param in      the Text to check, may be null
     * @param toFind  the character to find
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text in, final int toFind, final int ordinal) {
        if (TextUtils.isEmpty(in) || ordinal <= 0) {
            return TextUtils.NOT_FOUND;
        }

        return lastOrdinalIndexOf(in, toFind, in.getLength() - 1, ordinal);
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
     * @param toFind  the byte to find
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text t, final byte toFind, final int ordinal) {
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
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text t, final byte toFind, int start, final int ordinal) {
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
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 7, 1)  = 7
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 7, 2)  = 6
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'a', 3, 2)  = 0
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 7, 1)  = 5
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 7, 2)  = 2
     * TextUtils.lastOrdinalIndexOf("aabaabaa", 'b', 2, 2)  = -1
     * </pre>
     *
     * @param in      the Text to check, may be null
     * @param toFind  the character to find
     * @param start   the start position
     * @param ordinal the n-th last {@code toFind} to find
     * @return the n-th last index of the search Text,
     * {@code -1} ({@code NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text in, final int toFind, int start, final int ordinal) {
        if (TextUtils.isEmpty(in) || start < 0 || ordinal <= 0) {
            return TextUtils.NOT_FOUND;
        }

        int idx = start;
        for (int i = 0; i < ordinal; i++) {
            idx = lastIndexOf(in, toFind, idx);
            if (idx == TextUtils.NOT_FOUND) {
                return TextUtils.NOT_FOUND;
            }
            idx -= 1;
        }

        return idx + 1;
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
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text t, final Text toFind, final int ordinal) {
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
     * {@code -1} ({@code INDEX_NOT_FOUND}) if no match or {@code null} Text input
     */
    public static int lastOrdinalIndexOf(final Text t, final Text toFind, int start, final int ordinal) {
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
     * @param toFind the String to find, may be null
     * @return true if the Text contains the search Text,
     * false if not or {@code null} string input
     */
    public static boolean contains(final Text in, final String toFind) {
        return (in.find(toFind) != NOT_FOUND);
    }

    /**
     * <p>Checks if Text contains a search Text, handling {@code null}.
     * This method uses {@link TextUtils#indexOf(Text, Text)}.</p>
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
     * false if not or {@code null} Text input
     */
    public static boolean contains(final Text in, final Text toFind) {
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
     * false if not or {@code null} Text input
     */
    public static boolean contains(final Text in, final char toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }

    /**
     * <p>Checks if Text contains a search byte, handling {@code null}.</p>
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
     * @param toFind the byte to find
     * @return true if the Text contains the search character,
     * false if not or {@code null} Text input
     */
    public static boolean contains(final Text in, final byte toFind) {
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
    public static boolean containsWhitespace(final Text in) {
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
     * <p>Note: Not the most efficient method, but it's the easiest to implement</p>
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
    public static int indexOfAny(final Text in, final char[] searchChars) {
        if (isEmpty(in) || searchChars == null || searchChars.length == 0) {
            return NOT_FOUND;
        }

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        int inLast = inLen - 1;
        int searchLen = searchChars.length;
        int searchLast = searchLen - 1;
        int minIdx = Integer.MAX_VALUE;

        for (int j = 0; j < searchLen; j++) {
            int idx = indexOf(in, searchChars[j]);
            if (idx != NOT_FOUND) {
                minIdx = Math.min(minIdx, idx);
            }
        }

        return (minIdx == Integer.MAX_VALUE ? NOT_FOUND : minIdx);

    }

    /**
     * <p>Checks if the Text contains any character in the given
     * set of characters.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code false}.
     * A {@code null} or zero length search array will return {@code false}.</p>
     * <p/>
     * <p>Note: Not the most efficient method, but it's the easiest to implement</p>
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
    public static boolean containsAny(final Text in, final char[] searchChars) {
        return (indexOfAny(in, searchChars) != -1);
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
    public static boolean containsNone(final Text in, final char[] searchChars) {
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
    public static int indexOfAny(final Text in, final Text[] searchText) {
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
     * <p>Find the latest index of any of a set of potential subTexts.</p>
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
    public static int lastIndexOfAny(final Text in, final Text[] searchText) {
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
     * characters from the end of the Text.</p>
     * <p/>
     * <p>A {@code null} Text will return {@code null}.
     * An empty ("") Text will return "".</p>
     * <p/>
     * <pre>
     * TextUtils.subtext(null, *)   = null
     * TextUtils.subtext("", *)     = ""
     * TextUtils.subtext("abc", 0)  = "abc"
     * TextUtils.subtext("abc", 2)  = "c"
     * TextUtils.subtext("abc", 4)  = ""
     * TextUtils.subtext("abc", -2) = "bc"
     * TextUtils.subtext("abc", -4) = "abc"
     * </pre>
     *
     * @param in    the Text to get the subtext from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the Text by this many characters
     * @param out   where the subtext will be stored. On cases where null is returned,
     *              the {@code out} is set cleared to be ""
     * @return subtext from start position, {@code null} if null Text input.
     * this same output is stored in out
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtext(final Text in, int start, Text out) throws IllegalArgumentException {

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
     * zero-based -- i.e., to start at the beginning of the text use
     * {@code start = 0}. Negative start and end positions can be used to
     * specify offsets relative to the end of the text.</p>
     * <p/>
     * <p>If {@code start} is not strictly to the left of {@code end}, ""
     * is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.subtext(null, *, *)    = null
     * TextUtils.subtext("", * ,  *)    = "";
     * TextUtils.subtext("abc", 0, 2)   = "ab"
     * TextUtils.subtext("abc", 2, 0)   = ""
     * TextUtils.subtext("abc", 2, 4)   = "c"
     * TextUtils.subtext("abc", 4, 6)   = ""
     * TextUtils.subtext("abc", 2, 2)   = ""
     * TextUtils.subtext("abc", -2, -1) = "b"
     * TextUtils.subtext("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param in    the Text to get the subtext from, may be null
     * @param start the position to start from, negative means
     *              count back from the end of the Text by this many characters
     * @param end   the position to end at (exclusive), negative means
     *              count back from the end of the Text by this many characters
     * @param out   where the subtext will be stored. On cases where null is returned,
     *              the {@code out} is set cleared to be ""
     * @return subtext from start position to end position,
     * {@code null} if null Text input. this same output is stored in out
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtext(final Text in, int start, int end, Text out) throws IllegalArgumentException {
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
    public static Text left(final Text in, final int len, Text out) throws IllegalArgumentException {
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
     * @param in  the Text to get the rightmost characters from, may be null
     * @param len the length of the required Text
     * @param out where the subtext will be stored. On cases where null is returned,
     *            the {@code out} is set cleared to be ""
     * @return the rightmost characters, {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text right(final Text in, final int len, Text out) throws IllegalArgumentException {
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
     * @return the middle characters, {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text middle(final Text in, int pos, final int len, Text out) throws IllegalArgumentException {
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
     * <p>A {@code null} Text input will return {@code null}.
     * An empty ("") Text input will return the empty Text.
     * A {@code null} separator will return the input Text.</p>
     * <p/>
     * <p>If nothing is found, the Text input is returned.</p>
     * <p/>
     * <pre>
     * TextUtils.subtextBefore(null, *)      = null
     * TextUtils.subtextBefore("", *)        = ""
     * TextUtils.subtextBefore("abc", "a")   = ""
     * TextUtils.subtextBefore("abcba", "b") = "a"
     * TextUtils.subtextBefore("abc", "c")   = "ab"
     * TextUtils.subtextBefore("abc", "d")   = "abc"
     * TextUtils.subtextBefore("abc", "")    = ""
     * TextUtils.subtextBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param in        the Text to get a subtext from, may be null
     * @param separator the Text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the subtext before the first occurrence of the separator,
     * {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBefore(final Text in, final Text separator, Text out) throws IllegalArgumentException {
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
     * TextUtils.subtextAfter(null, *)      = null
     * TextUtils.subtextAfter("", *)        = ""
     * TextUtils.subtextAfter(*, null)      = ""
     * TextUtils.subtextAfter("abc", "a")   = "bc"
     * TextUtils.subtextAfter("abcba", "b") = "cba"
     * TextUtils.subtextAfter("abc", "c")   = ""
     * TextUtils.subtextAfter("abc", "d")   = ""
     * TextUtils.subtextAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param in        the Text to get a subtext from, may be null
     * @param separator the Text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the subtext after the first occurrence of the separator,
     * {@code null} if null Text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextAfter(final Text in, final Text separator, Text out) throws IllegalArgumentException {
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
     * TextUtils.subtextBeforeLast(null, *)      = null
     * TextUtils.subtextBeforeLast("", *)        = ""
     * TextUtils.subtextBeforeLast("abcba", "b") = "abc"
     * TextUtils.subtextBeforeLast("abc", "c")   = "ab"
     * TextUtils.subtextBeforeLast("a", "a")     = ""
     * TextUtils.subtextBeforeLast("a", "z")     = "a"
     * TextUtils.subtextBeforeLast("a", null)    = "a"
     * TextUtils.subtextBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param in        the Text to get a subtext from, may be null
     * @param separator the Text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the subtext before the last occurrence of the separator,
     * {@code null} if null text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBeforeLast(final Text in, final Text separator, Text out) throws IllegalArgumentException {
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
     * TextUtils.subtextAfterLast(null, *)      = null
     * TextUtils.subtextAfterLast("", *)        = ""
     * TextUtils.subtextAfterLast(*, "")        = ""
     * TextUtils.subtextAfterLast(*, null)      = ""
     * TextUtils.subtextAfterLast("abc", "a")   = "bc"
     * TextUtils.subtextAfterLast("abcba", "b") = "a"
     * TextUtils.subtextAfterLast("abc", "c")   = ""
     * TextUtils.subtextAfterLast("a", "a")     = ""
     * TextUtils.subtextAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param in        the text to get a subtext from, may be null
     * @param separator the text to search for, may be null
     * @param out       where the subtext will be stored. On cases where null is returned,
     *                  the {@code out} is set cleared to be ""
     * @return the subtext after the last occurrence of the separator,
     * {@code null} if null text input
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextAfterLast(final Text in, final Text separator, Text out) throws IllegalArgumentException {
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
     * TextUtils.subtextBetween(null, *)            = null
     * TextUtils.subtextBetween("", "")             = ""
     * TextUtils.subtextBetween("", "tag")          = null
     * TextUtils.subtextBetween("tagabctag", null)  = null
     * TextUtils.subtextBetween("tagabctag", "")    = ""
     * TextUtils.subtextBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param in  the Text containing the subtext, may be null
     * @param tag the Text before and after the subtext, may be null
     * @param out where the subtext will be stored. On cases where null is returned,
     *            the {@code out} is set cleared to be ""
     * @return the Text, {@code null} if no match
     * @throws java.lang.IllegalArgumentException if output is null
     */
    public static Text subtextBetween(final Text in, final Text tag, Text out) throws IllegalArgumentException {
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
     * TextUtils.subtextBetween("wx[b]yz", "[", "]") = "b"
     * TextUtils.subtextBetween(null, *, *)          = null
     * TextUtils.subtextBetween(*, null, *)          = null
     * TextUtils.subtextBetween(*, *, null)          = null
     * TextUtils.subtextBetween("", "", "")          = ""
     * TextUtils.subtextBetween("", "", "]")         = null
     * TextUtils.subtextBetween("", "[", "]")        = null
     * TextUtils.subtextBetween("yabcz", "", "")     = ""
     * TextUtils.subtextBetween("yabcz", "y", "z")   = "abc"
     * TextUtils.subtextBetween("yabczyabcz", "y", "z")   = "abc"
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
    public static Text subtextBetween(final Text in, final Text open, final Text close, Text out) throws IllegalArgumentException {
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

    /**
     * <p>Concatenates one Text object to another </p>
     * <p/>
     * <p>A {@code null} input Text {@code t1} returns {@code null}.
     * A {@code null} input Text {@code t2} returns {@code t1}.</p>
     * <p/>
     * <pre>
     * TextUtils.concat("abc", "_concat") = "abc_concat"
     * TextUtils.concat(null, *) = null
     * TextUtils.concat("abc", null) = "abc"
     * </pre>
     *
     * @param t1 the Text containing the subtext, may be null
     * @param t2 the Text before the subtext, may be null
     * @return {@code t1} with {@code t2} concatenated
     */
    public static Text concat(Text t1, final Text t2) {
        if (t1 == null) {
            return null;
        } else if (t2 != null) {
            t1.append(t2.getBytes(), 0, t2.getLength());
        }
        return t1;
    }

    /**
     * <p>Counts the number times a character occurs in a Text object </p>
     * <p/>
     * <p>A {@code null} input Text {@code t1} returns 0.
     * A {@code null} input Text {@code t2} returns {@code t1}.</p>
     * <p/>
     * <pre>
     * TextUtils.concat("abca", 'a') = 2
     * TextUtils.concat("abca", 'q') = 0
     * TextUtils.concat(null, *) = 0
     * </pre>
     *
     * @param in      the Text containing the subtext, may be null
     * @param toCount the character to count
     * @return number of times {@code toCount} occurs
     */
    public static int countMatches(final Text in, final char toCount) {
        if (in == null) {
            return 0;
        }

        return countMatches(in, (int) toCount);
    }

    /**
     * <p>Counts the number times a character occurs in a Text object </p>
     * <p/>
     * <p>A {@code null} input Text {@code t1} returns 0.
     * A {@code null} input Text {@code t2} returns {@code t1}.</p>
     * <p/>
     * <pre>
     * TextUtils.concat("abca", 'a') = 2
     * TextUtils.concat("abca", 'q') = 0
     * TextUtils.concat(null, *) = 0
     * </pre>
     *
     * @param in      the Text containing the subtext, may be null
     * @param toCount the character to count
     * @return number of times {@code toCount} occurs
     */
    public static int countMatches(final Text in, final int toCount) {
        if (isEmpty(in) || toCount < 0) {
            return 0;
        }

        int count = 0;
        int start = 0;
        int inc = getNumCharacterOctets(toCount);

        while ((start = indexOf(in, toCount, start)) != NOT_FOUND) {
            count++;
            start += inc;
        }

        return count;
    }

    /**
     * <p>Counts the number times a Text occurs in a Text object </p>
     * <p/>
     * <p>A {@code null} input Text {@code t1} returns 0.
     * A {@code null} input Text {@code t2} returns {@code t1}.</p>
     * <p/>
     * <pre>
     * TextUtils.concat("abca", "a") = 2
     * TextUtils.concat("abcab", "ab") = 2
     * TextUtils.concat("abca", "q") = 0
     * TextUtils.concat(null, *) = 0
     * </pre>
     *
     * @param in      the Text containing the subtext, may be null
     * @param toCount the character to count
     * @return number of times {@code toCount} occurs
     */
    public static int countMatches(final Text in, final Text toCount) {
        if (isEmpty(in) || isEmpty(toCount)) {
            return 0;
        }

        int count = 0;
        int start = 0;
        int inc = toCount.getLength();

        while ((start = indexOf(in, toCount, start)) != NOT_FOUND) {
            count++;
            start += inc;
        }

        return count;
    }

    /**
     * <p>Converts a Text to lower case in place as per {@link java.lang.Character#toLowerCase(int)}.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.lowerCase(null)  = null
     * TextUtils.lowerCase("")    = ""
     * TextUtils.lowerCase("aBc") = "ABC"
     * </pre>
     * <p/>
     * <p><strong>Note:</strong> This will not work for non-ascii characters because of the way
     * the bytes are stored in the Text object.</p>
     * <p/>
     * <p>Future work: update code to combine bytes, lowercase it, split the bytes back up</p>
     *
     * @param in the Text to lower case, may be null
     * @return the lower cased Text, {@code null} if null Text input
     */
    public static Text lowerCase(Text in) {
        if (in == null) {
            return null;
        }

        Text out = null;

        try {
            out = lowerCase(in, in);
        } catch (IllegalArgumentException e) {
        } // this will never happen
        return out;
    }

    /**
     * <p>Converts a Text to lower case as per {@link java.lang.Character#toLowerCase(int)}.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.lowerCase(null, * != null)  = ""
     * TextUtils.lowerCase(null, null)  = null
     * TextUtils.lowerCase(*, null)  = null
     * TextUtils.lowerCase("", *)    = ""
     * TextUtils.lowerCase("aBc", *) = "ABC"
     * </pre>
     * <p/>
     *
     * @param in  the Text to lower case, may be null
     * @param out the Text to store the lower case Text, may be null
     * @return the lower cased Text, {@code null} if null Text input
     */
    public static Text lowerCase(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (isEmpty(in)) {
            out.clear();
            return out;
        }

        if (in != out) {
            out.set(in);
        }

        int outLen = out.getLength();
        byte[] outB = out.getBytes();
        int numBytes;

        for (int i = 0; i < outLen; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(outB[i]);

            int lowChar = Character.toLowerCase(bytesToUnicodeInt(outB, i, numBytes));
            storeUnicodeCharacter(outB, i, lowChar, numBytes);
        }

        return out;
    }

    /**
     * <p>Converts a Text to upper case in place as per {@link java.lang.Character#toUpperCase(int)}.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.upperCase(null)  = null
     * TextUtils.upperCase("")    = ""
     * TextUtils.upperCase("aBc") = "ABC"
     * </pre>
     * <p/>
     *
     * @param in the Text to upper case, may be null
     * @return the upper cased Text, {@code null} if null Text input
     */
    public static Text upperCase(Text in) {
        if (in == null) {
            return null;
        }

        Text out = null;

        try {
            out = upperCase(in, in);
        } catch (IllegalArgumentException e) {
        } // this will never happen
        return out;
    }

    /**
     * <p>Converts a Text to upper case as per {@link java.lang.Character#toUpperCase(int)}.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.upperCase(null, * != null)  = ""
     * TextUtils.upperCase(null, null)  = throw exception
     * TextUtils.upperCase(*, null)  = null
     * TextUtils.upperCase("", *)    = ""
     * TextUtils.upperCase("aBc", *) = "ABC"
     * </pre>
     * <p/>
     *
     * @param in  the Text to upper case, may be null
     * @param out the Text to store the upper case Text, may be null
     * @return the upper cased Text, {@code null} if null Text input
     */
    public static Text upperCase(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (isEmpty(in)) {
            out.clear();
            return out;
        }

        if (in != out) {
            out.set(in);
        }

        int outLen = out.getLength();
        byte[] outB = out.getBytes();
        int numBytes;

        for (int i = 0; i < outLen; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(outB[i]);

            int lowChar = Character.toUpperCase(bytesToUnicodeInt(outB, i, numBytes));
            storeUnicodeCharacter(outB, i, lowChar, numBytes);
        }

        return out;
    }

    /**
     * <p>Capitalizes a Text changing the first letter to title case as
     * per {@link Character#toTitleCase(int)}. No other letters are changed.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.capitalize(null)  = null
     * TextUtils.capitalize("")    = ""
     * TextUtils.capitalize("cat") = "Cat"
     * TextUtils.capitalize("cAt") = "CAt"
     * </pre>
     * <p/>
     *
     * @param in the Text to capitalize, may be null
     * @return the capitalized Text, {@code null} if null Text input
     * @see #capitalize(Text, Text)
     * @see #uncapitalize(Text)
     * @see #uncapitalize(Text, Text)
     * @since 2.0
     */
    public static Text capitalize(Text in) {
        if (in == null) {
            return null;
        }

        Text out = null;

        try {
            out = capitalize(in, in);
        } catch (IllegalArgumentException e) {
        } // this will never happen
        return out;
    }

    /**
     * <p>Capitalizes a Text changing the first letter to title case as
     * per {@link Character#toTitleCase(int)}. No other letters are changed.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.capitalize(null, * != null)  = ""
     * TextUtils.capitalize(null, null)  = null
     * TextUtils.capitalize(null, *)  = null
     * TextUtils.capitalize("", *)    = ""
     * TextUtils.capitalize("cat", *) = "Cat"
     * TextUtils.capitalize("cAt", *) = "CAt"
     * </pre>
     * <p/>
     *
     * @param in  the Text to capitalize, may be null
     * @param out the Text to store the output, may be null
     * @return the capitalized Text, {@code null} if null Text input
     * @see #capitalize(Text)
     * @see #uncapitalize(Text)
     * @see #uncapitalize(Text, Text)
     */
    public static Text capitalize(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (isEmpty(in)) {
            out.clear();
            return out;
        }

        if (in != out) {
            out.set(in);
        }

        byte[] outB = out.getBytes();
        int numBytes = getNumBytesWithStartingByte(outB[0]);

        int lowChar = Character.toTitleCase(bytesToUnicodeInt(outB, 0, numBytes));
        storeUnicodeCharacter(outB, 0, lowChar, numBytes);

        return out;
    }

    /**
     * <p>Uncapitalizes a Text changing the first letter to lower case as
     * per {@link Character#toLowerCase(int)}. No other letters are changed.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.uncapitalize(null)  = null
     * TextUtils.uncapitalize("")    = ""
     * TextUtils.uncapitalize("Cat") = "cat"
     * TextUtils.uncapitalize("cAt") = "cAt"
     * </pre>
     * <p/>
     *
     * @param in the Text to uncapitalize, may be null
     * @return the uncapitalized Text, {@code null} if null Text input
     * @see #uncapitalize(Text, Text)
     * @see #capitalize(Text)
     * @see #capitalize(Text, Text)
     * @since 2.0
     */
    public static Text uncapitalize(Text in) {
        if (in == null) {
            return null;
        }

        Text out = null;

        try {
            out = uncapitalize(in, in);
        } catch (IllegalArgumentException e) {
        } // this will never happen
        return out;
    }

    /**
     * <p>Uncapitalizes a Text changing the first letter to lower case as
     * per {@link Character#toTitleCase(int)}. No other letters are changed.</p>
     * <p/>
     * <p>A {@code null} input Text returns {@code null}.</p>
     * <p/>
     * <pre>
     * TextUtils.capitalize(null, * != null)  = ""
     * TextUtils.capitalize(null, null)  = null
     * TextUtils.capitalize(null, *)  = null
     * TextUtils.capitalize("", *)    = ""
     * TextUtils.capitalize("Cat", *) = "cat"
     * TextUtils.capitalize("cAt", *) = "cAt"
     * </pre>
     * <p/>
     *
     * @param in  the Text to uncapitalize, may be null
     * @param out the Text to store the output, may be null
     * @return the uncapitalized Text, {@code null} if null Text input
     * @see #uncapitalize(Text)
     * @see #capitalize(Text)
     * @see #capitalize(Text, Text)
     * @since 2.0
     */
    public static Text uncapitalize(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (isEmpty(in)) {
            out.clear();
            return out;
        }

        if (in != out) {
            out.set(in);
        }

        byte[] outB = out.getBytes();
        int numBytes = getNumBytesWithStartingByte(outB[0]);

        int lowChar = Character.toLowerCase(bytesToUnicodeInt(outB, 0, numBytes));
        storeUnicodeCharacter(outB, 0, lowChar, numBytes);

        return out;
    }

    /**
     * <p>Swaps the case of a Text changing upper and title case to
     * lower case, and lower case to upper case.</p>
     * <p/>
     * <ul>
     * <li>Upper case character converts to Lower case</li>
     * <li>Title case character converts to Lower case</li>
     * <li>Lower case character converts to Upper case</li>
     * </ul>
     * <p/>
     * <p/>
     * <pre>
     * TextUtils.swapCase(null)                 = null
     * TextUtils.swapCase("")                   = ""
     * TextUtils.swapCase("The dog has a BONE") = "tHE DOG HAS A bone"
     * </pre>
     * <p/>
     * <p/>
     *
     * @param in the Text to swap case, may be null
     * @return the changed Text, {@code null} if null Text input
     */
    public static Text swapCase(Text in) {
        if (in == null) {
            return null;
        }

        Text out = null;

        try {
            out = swapCase(in, in);
        } catch (IllegalArgumentException e) {
        } // this will never happen
        return out;
    }

    /**
     * <p>Swaps the case of a Text changing upper and title case to
     * lower case, and lower case to upper case.</p>
     * <p/>
     * <ul>
     * <li>Upper case character converts to Lower case</li>
     * <li>Title case character converts to Lower case</li>
     * <li>Lower case character converts to Upper case</li>
     * </ul>
     * <p/>
     * <p/>
     * <pre>
     * TextUtils.swapCase(null, * != null)  = ""
     * TextUtils.swapCase(null, null)  = null
     * TextUtils.swapCase("", *)                   = ""
     * TextUtils.swapCase("The dog has a BONE", *) = "tHE DOG HAS A bone"
     * </pre>
     * <p/>
     * <p/>
     *
     * @param in  the Text to swap case, may be null
     * @param out the Text to store the result, may be null
     * @return the changed Text, {@code null} if null Text input
     */
    public static Text swapCase(final Text in, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (isEmpty(in)) {
            out.clear();
            return out;
        }

        if (in != out) {
            out.set(in);
        }

        final byte[] buffer = out.getBytes();
        int numBytes;

        for (int i = 0; i < buffer.length; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);

            int ch = bytesToUnicodeInt(buffer, i, numBytes);
            int swappedChar = ch;

            if (Character.isUpperCase(ch) || Character.isTitleCase(ch)) {
                swappedChar = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                swappedChar = Character.toUpperCase(ch);
            }

            storeUnicodeCharacter(buffer, i, swappedChar, numBytes);

        }
        return out;
    }

    /**
     * Gets the specified utf-8 byte for the unicode code point.
     *
     * @param codePoint Unicode code point to turn into bytes
     * @param whichByte Which byte to retrieve
     * @param numBytes  number of utf-8 bytes that the code point needs
     * @return returns the {@code whichByte}th utf-8 for the unicode code point
     */
    private static byte getUtf8Byte(final int codePoint, final int whichByte, final int numBytes) {
        if (whichByte == 0) {
            return (byte) ((0xFFFC << (6 - numBytes)) | (codePoint >> (6 * (numBytes - 1))));
        }

        return (byte) (0xFF80 | ((codePoint >> (6 * (numBytes - whichByte - 1))) & 0x3F));
    }

    /**
     * Stores the unicode code point into the specified location of the byte array.
     *
     * @param buffer
     * @param location
     * @param codePoint
     * @param numBytes
     */
    private static void storeUnicodeCharacter(final byte[] buffer, final int location, final int codePoint, final int numBytes) {
        if (numBytes == 1) {
            buffer[location] = (byte) codePoint;
        } else {
            for (int j = 0; j < numBytes; j++) {
                buffer[location + j] = getUtf8Byte(codePoint, j, numBytes);
            }
        }
    }

    /**
     * <p>Checks if the Text contains only Unicode letters.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAlpha(null)   = false
     * TextUtils.isAlpha("")     = false
     * TextUtils.isAlpha("  ")   = false
     * TextUtils.isAlpha("abc")  = true
     * TextUtils.isAlpha("ab2c") = false
     * TextUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains letters, and is non-null
     */
    public static boolean isAlpha(final Text in) {
        if (isEmpty(in)) {
            return false;
        }
        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only Unicode letters and
     * space (' ').</p>
     * <p/>
     * <p>{@code null} will return {@code false}
     * An empty Text (length()=0) will return {@code true}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAlphaSpace(null)   = false
     * TextUtils.isAlphaSpace("")     = true
     * TextUtils.isAlphaSpace("  ")   = true
     * TextUtils.isAlphaSpace("abc")  = true
     * TextUtils.isAlphaSpace("ab c") = true
     * TextUtils.isAlphaSpace("ab2c") = false
     * TextUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains letters and space,
     * and is non-null
     */
    public static boolean isAlphaSpace(final Text in) {
        if (in == null) {
            return false;
        }
        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);
            if (Character.isLetter(ch) == false && ch != ' ') {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>Checks if the Text contains only Unicode letters or digits.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAlphanumeric(null)   = false
     * TextUtils.isAlphanumeric("")     = false
     * TextUtils.isAlphanumeric("  ")   = false
     * TextUtils.isAlphanumeric("abc")  = true
     * TextUtils.isAlphanumeric("ab c") = false
     * TextUtils.isAlphanumeric("ab2c") = true
     * TextUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains letters or digits,
     * and is non-null
     */
    public static boolean isAlphanumeric(final Text in) {
        if (isEmpty(in)) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);
            if (Character.isLetterOrDigit(ch) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only Unicode letters, digits
     * or space ({@code ' '}).</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code true}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAlphanumericSpace(null)   = false
     * TextUtils.isAlphanumericSpace("")     = true
     * TextUtils.isAlphanumericSpace("  ")   = true
     * TextUtils.isAlphanumericSpace("abc")  = true
     * TextUtils.isAlphanumericSpace("ab c") = true
     * TextUtils.isAlphanumericSpace("ab2c") = true
     * TextUtils.isAlphanumericSpace("ab-c") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains letters, digits or space,
     * and is non-null
     */
    public static boolean isAlphanumericSpace(final Text in) {
        if (in == null) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);
            if (!Character.isLetterOrDigit(ch) && ch != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only ASCII printable characters.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code true}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAsciiPrintable(null)     = false
     * TextUtils.isAsciiPrintable("")       = true
     * TextUtils.isAsciiPrintable(" ")      = true
     * TextUtils.isAsciiPrintable("Ceki")   = true
     * TextUtils.isAsciiPrintable("ab2c")   = true
     * TextUtils.isAsciiPrintable("!ab-c~") = true
     * TextUtils.isAsciiPrintable("\u0020") = true
     * TextUtils.isAsciiPrintable("\u0021") = true
     * TextUtils.isAsciiPrintable("\u007e") = true
     * TextUtils.isAsciiPrintable("\u007f") = false
     * TextUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if every character is in the range
     * 32 thru 126
     */
    public static boolean isAsciiPrintable(final Text in) {
        if (in == null) {
            return false;
        }
        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();

        for (int i = 0; i < sz; i++) {
            int ch = (buffer[i] & 0xFF);
            if (ch < 32 || ch > 126) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code false}.</p>
     * <p/>
     * <p>Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a Text passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.</p>
     * <p/>
     * <pre>
     * TextUtils.isNumeric(null)   = false
     * TextUtils.isNumeric("")     = false
     * TextUtils.isNumeric("  ")   = false
     * TextUtils.isNumeric("123")  = true
     * TextUtils.isNumeric("\u0967\u0968\u0969")  = true
     * TextUtils.isNumeric("12 3") = false
     * TextUtils.isNumeric("ab2c") = false
     * TextUtils.isNumeric("12-3") = false
     * TextUtils.isNumeric("12.3") = false
     * TextUtils.isNumeric("-123") = false
     * TextUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     */
    public static boolean isNumeric(final Text in) {
        if (isEmpty(in)) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);

            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only Unicode digits or space
     * ({@code ' '}).
     * A decimal point is not a Unicode digit and returns false.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code true}.</p>
     * <p/>
     * <pre>
     * TextUtils.isNumericSpace(null)   = false
     * TextUtils.isNumericSpace("")     = true
     * TextUtils.isNumericSpace("  ")   = true
     * TextUtils.isNumericSpace("123")  = true
     * TextUtils.isNumericSpace("12 3") = true
     * TextUtils.isNumeric("\u0967\u0968\u0969")  = true
     * TextUtils.isNumeric("\u0967\u0968 \u0969")  = true
     * TextUtils.isNumericSpace("ab2c") = false
     * TextUtils.isNumericSpace("12-3") = false
     * TextUtils.isNumericSpace("12.3") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains digits or space,
     * and is non-null
     */
    public static boolean isNumericSpace(final Text in) {
        if (in == null) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);

            if (!Character.isDigit(ch) && ch != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only whitespace.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code true}.</p>
     * <p/>
     * <pre>
     * TextUtils.isWhitespace(null)   = false
     * TextUtils.isWhitespace("")     = true
     * TextUtils.isWhitespace("  ")   = true
     * TextUtils.isWhitespace("abc")  = false
     * TextUtils.isWhitespace("ab2c") = false
     * TextUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains whitespace, and is non-null
     */
    public static boolean isWhitespace(final Text in) {
        if (in == null) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);

            if (!Character.isWhitespace(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only lowercase characters.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAllLowerCase(null)   = false
     * TextUtils.isAllLowerCase("")     = false
     * TextUtils.isAllLowerCase("  ")   = false
     * TextUtils.isAllLowerCase("abc")  = true
     * TextUtils.isAllLowerCase("abC") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains lowercase characters, and is non-null
     */
    public static boolean isAllLowerCase(final Text in) {
        if (isEmpty(in)) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);

            if (!Character.isLowerCase(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the Text contains only uppercase characters.</p>
     * <p/>
     * <p>{@code null} will return {@code false}.
     * An empty Text (length()=0) will return {@code false}.</p>
     * <p/>
     * <pre>
     * TextUtils.isAllUpperCase(null)   = false
     * TextUtils.isAllUpperCase("")     = false
     * TextUtils.isAllUpperCase("  ")   = false
     * TextUtils.isAllUpperCase("abc")  = true
     * TextUtils.isAllUpperCase("abC") = false
     * </pre>
     *
     * @param in the Text to check, may be null
     * @return {@code true} if only contains uppercase characters, and is non-null
     */
    public static boolean isAllUpperCase(final Text in) {
        if (isEmpty(in)) {
            return false;
        }

        final int sz = in.getLength();
        final byte[] buffer = in.getBytes();
        int numBytes = 1;

        for (int i = 0; i < sz; i += numBytes) {
            numBytes = getNumBytesWithStartingByte(buffer[i]);
            int ch = bytesToUnicodeInt(buffer, i, numBytes);

            if (!Character.isUpperCase(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Compares two Texts, and returns the portion where they differ.
     * More precisely, return the remainder of the second Text,
     * starting from where it's different from the first. This means that
     * the difference between "abc" and "ab" is the empty Text and not "c". </p>
     * <p/>
     * <p>For example,
     * {@code difference("i am a machine", "i am a robot") -> "robot"}.</p>
     * <p/>
     * <pre>
     * TextUtils.difference(null, null) = null
     * TextUtils.difference("", "") = ""
     * TextUtils.difference("", "abc") = "abc"
     * TextUtils.difference("abc", "") = ""
     * TextUtils.difference("abc", "abc") = ""
     * TextUtils.difference("abc", "ab") = ""
     * TextUtils.difference("ab", "abxyz") = "xyz"
     * TextUtils.difference("abcde", "abxyz") = "xyz"
     * TextUtils.difference("abcde", "xyz") = "xyz"
     * </pre>
     *
     * @param str1 the first Text, may be null
     * @param str2 the second Text, may be null
     * @param out  the output Text, may be null
     * @return the portion of str2 where it differs from str1; returns the
     * empty Text if they are equal
     * @see #indexOfDifference(Text, Text)
     */
    public static Text difference(final Text str1, final Text str2, Text out) throws IllegalArgumentException {
        if (out == null) {
            throw new IllegalArgumentException("out parameter cannot be null");
        }

        if (str1 == null || str2 == null) {
            out.clear();
            return str2;
        } else {
            final int at = indexOfDifference(str1, str2);
            if (at == NOT_FOUND) {
                out.clear();
            } else {
                subtext(str2, at, out);
            }
        }
        return out;
    }

    /**
     * <p>Compares two Texts, and returns the index at which the
     * Texts begin to differ.</p>
     * <p/>
     * <p>For example,
     * {@code indexOfDifference("i am a machine", "i am a robot") -> 7}</p>
     * <p/>
     * <pre>
     * TextUtils.indexOfDifference(null, null) = -1
     * TextUtils.indexOfDifference("", "") = -1
     * TextUtils.indexOfDifference("", "abc") = 0
     * TextUtils.indexOfDifference("abc", "") = 0
     * TextUtils.indexOfDifference("abc", "abc") = -1
     * TextUtils.indexOfDifference("ab", "abxyz") = 2
     * TextUtils.indexOfDifference("abcde", "abxyz") = 2
     * TextUtils.indexOfDifference("abcde", "xyz") = 0
     * </pre>
     *
     * @param cs1 the first Text, may be null
     * @param cs2 the second Text, may be null
     * @return the index where cs1 and cs2 begin to differ; -1 if they are equal
     */
    public static int indexOfDifference(final Text cs1, final Text cs2) {
        if (cs1 == cs2) {
            return NOT_FOUND;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i;
        final byte[] cs1Buffer = cs1.getBytes();
        final byte[] cs2Buffer = cs2.getBytes();
        int numBytes1 = 1;
        int numBytes2 = 1;

        for (i = 0; i < cs1.getLength() && i < cs2.getLength(); i += numBytes1) {

            numBytes1 = getNumBytesWithStartingByte(cs1Buffer[i]);
            int ch1 = bytesToUnicodeInt(cs1Buffer, i, numBytes1);

            numBytes2 = getNumBytesWithStartingByte(cs2Buffer[i]);
            int ch2 = bytesToUnicodeInt(cs2Buffer, i, numBytes2);

            if (ch1 != ch2) {
                break;
            }
        }
        if (i < cs2.getLength() || i < cs1.getLength()) {
            return i;
        }
        return NOT_FOUND;
    }
}
