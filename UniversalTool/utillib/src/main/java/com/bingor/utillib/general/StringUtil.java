package com.bingor.utillib.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
     * 替换文字abcdefg->ab***fg
     *
     * @param str
     * @param start **起始位置
     * @param end   **结束位置
     * @return
     */
    public static String replaceWord2Star(String str, int start, int end) {
        if (str == null) {
            return null;
        }
        if (start < 0 || end >= str.length() || start > end) {
            return str;
        }
        int startCount = 0;
        String star = "";
        for (int i = 0; i < end - start + 1; i++) {
            star += "*";
        }
        return str.substring(0, start) + star + str.substring(end + 1, str.length());
    }

    /**
     * 去除空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}