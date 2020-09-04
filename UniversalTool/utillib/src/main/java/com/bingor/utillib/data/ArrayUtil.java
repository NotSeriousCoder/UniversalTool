package com.bingor.utillib.data;



import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayUtil {
//    public static int indexOf(Object[] array, Object obj) {
//        for (int i = 0, len = array.length; i < len; i++) {
//            if (array[i].equals(obj))
//                return i;
//        }
//        return -1;
//    }

    public static <T> int indexOf(T[] array, T obj) {
        for (int i = 0, len = array.length; i < len; i++) {
            if (array[i].equals(obj))
                return i;
        }
        return -1;
    }

    public static String join(Object[] array, String delimiter) {
        if (array.length == 0)
            return "";
        StringBuffer buffer = new StringBuffer(array[0].toString());
        for (int i = 1, len = array.length; i < len; i++) {
            buffer.append(delimiter).append(array[i].toString());
        }
        return buffer.toString();
    }

    public static <T> String join(AbstractCollection<T> array, String delimiter) {
        if (array.isEmpty())
            return "";
        Iterator<T> iter = array.iterator();
        StringBuffer buffer = new StringBuffer(iter.next().toString());
        while (iter.hasNext())
            buffer.append(delimiter).append(iter.next().toString());
        return buffer.toString();
    }

    public static <T> T[] merge(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static byte[] merge(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }


    public static <T> T[] mergeAll(T[]... rest) {
        int totalLength = 0;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(rest[0], totalLength);
        int offset = 0;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }


    public static byte[] mergeAll(byte[]... rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(rest[0], totalLength);
        int offset = 0;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
