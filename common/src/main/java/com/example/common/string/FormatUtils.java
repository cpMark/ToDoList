package com.example.common.string;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * function：字符串格式化工具类
 * author by admin
 * create on 2018/5/24.
 */
public class FormatUtils {

    /**
     * 占位符字符串格式化
     *
     * @param format 待格式化的字符串
     * @param args   可变字符串（用于格式化字符串的占位符）
     */
    public static String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    /*
     * 是否为浮点数？double或float类型。
     * @param str 传入的字符串。
     * @return 是浮点数返回true,否则返回false。
     */
    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

}
