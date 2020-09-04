package com.bingor.utillib.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bingor on 2020/9/4.
 */
public class MapUtil {

    public static <K extends Comparable<K>, V> LinkedHashMap<K, V> sortMap(Map<K, V> mapTarget, boolean ascending) {
        LinkedHashMap<K, V> res = new LinkedHashMap<>();
        if (mapTarget == null) {
            return res;
        }
        List<K> keys = new ArrayList<>();
        Iterator iterator = mapTarget.keySet().iterator();
        while (iterator.hasNext()) {
            keys.add((K) iterator.next());
        }
        Collections.sort(keys);
        if (ascending) {
            for (K key : keys) {
                res.put(key, mapTarget.get(key));
            }
        } else {
            for (int i = keys.size() - 1; i >= 0; i--) {
                K key = keys.get(i);
                res.put(key, mapTarget.get(key));
            }
        }
        return res;
    }
}
