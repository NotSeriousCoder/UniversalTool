package com.bingor.utillib.data;

import java.util.ArrayList;

/**
 * 列表工具
 * Created by HXB on 2018/9/4.
 */
public class ListUtil {

    /**
     * 数组转List
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> array2List(T[] array) {
        ArrayList<T> res = new ArrayList<>();
        if (array != null) {
            for (T t : array) {
                res.add(t);
            }
        }
        return res;
    }
}
