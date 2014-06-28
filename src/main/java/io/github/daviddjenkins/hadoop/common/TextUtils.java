package io.github.daviddjenkins.hadoop.common;

import org.apache.hadoop.io.Text;

/**
 * Created by david on 6/23/14.
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

    ;

    public static boolean isEmpty(final Text in) {
        return (in == null || in.getLength() == 0);
    }

    public static boolean isNotEmpty(final Text in) {
        return !isEmpty(in);
    }

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

    public static boolean isNoneEmpty(final Text[] ins) {
        return !isAnyEmpty(ins);
    }

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

    public static boolean isNotBlank(final Text in) {
        return !isBlank(in);
    }

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

    public static Text trim(Text in) {
        return removeFrontBackChars(in, CHARTYPE.CONTROL);
    }

    public static Text strip(Text in) {
        return removeFrontBackChars(in, CHARTYPE.WHITESPACE);
    }

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

    public static int indexOf(Text t, char toFind) {
        return indexOf(t, toFind, 0);
    }

    public static int indexOf(Text t, int toFind) {
        return indexOf(t, toFind, 0);
    }

    public static int indexOf(Text t, int toFind, int start) {
        return indexOf(t, (char) toFind, start);
    }

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

    public static int indexOf(Text t, Text toFind) {
        return indexOf(t, toFind, 0);
    }

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

        for (int i = start; i < tLen - toFindLen; i++) {
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

    public static int ordinalIndexOf(Text t, char toFind, int ordinal) {
        return ordinalIndexOf(t, (int) toFind, ordinal);
    }

    public static int ordinalIndexOf(Text t, int toFind, int ordinal) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }
        return ordinalIndexOf(t, toFind, 0, ordinal);
    }

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

    public static int ordinalIndexOf(Text t, Text toFind, int ordinal) {
        return ordinalIndexOf(t, toFind, 0, ordinal);
    }

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

    public static int lastIndexOf(Text t, char toFind) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }

        return lastIndexOf(t, toFind, t.getLength() - 1);
    }

    public static int lastIndexOf(Text t, int toFind) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }

        return lastIndexOf(t, toFind, t.getLength() - 1);
    }

    public static int lastIndexOf(Text t, int toFind, int start) {
        return lastIndexOf(t, (char) toFind, start);
    }

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

    public static int lastIndexOf(Text t, Text toFind) {
        return lastIndexOf(t, toFind, t.getLength() - 1);
    }

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

    public static int lastOrdinalIndexOf(Text t, char toFind, int ordinal) {
        return lastOrdinalIndexOf(t, (int) toFind, ordinal);
    }

    public static int lastOrdinalIndexOf(Text t, int toFind, int ordinal) {
        if (isEmpty(t)) {
            return NOT_FOUND;
        }
        return lastOrdinalIndexOf(t, toFind, t.getLength() - 1, ordinal);
    }

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

    public static Text concat(Text t1, Text t2) {
        t1.append(t2.getBytes(), 0, t2.getLength());
        return t1;
    }

    public static boolean contains(Text in, String toFind) {
        return (in.find(toFind) != NOT_FOUND);
    }

    public static boolean contains(Text in, Text toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }

    public static boolean contains(Text in, int toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }

    public static boolean contains(Text in, char toFind) {
        return (indexOf(in, toFind) != NOT_FOUND);
    }

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

    public static int indexOfAny(Text in, final char[] searchChars) {
        if (isEmpty(in) || searchChars == null || searchChars.length == 0) {
            return NOT_FOUND;
        }

        byte[] inB = in.getBytes();
        int inLen = in.getLength();
        int inLast = inLen - 1;
        int searchLen = searchChars.length;
        int searchLast = searchLen -1;

        for (int i = 0; i < inLen; i++) {
            char ch = (char)inB[i];
            for (int j = 0; j < searchLen; j++) {
                if(ch == searchChars[j]) {
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

    public static boolean containsAny(Text in, final char[] searchChars) {
        return (indexOfAny(in, searchChars) != -1);
    }

}
