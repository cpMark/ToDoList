package com.example.common.qr;

import java.util.regex.Pattern;

public class QrUtils {


    private static String[] sSpecialCharArray = {"(", "[", "\\", "^", "-", "$", "]", ")", "?", "*", "+", ".","|"};

    /**
     * 生成正则表达式
     *
     * @param qrCodeValue 原值
     * @param splitChar   分割符
     * @return 正则表达式
     */
    public static String generateRegex(String qrCodeValue, String splitChar) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sSpecialCharArray.length; i++) {
            if (sSpecialCharArray[i].equals(splitChar)) {
                splitChar = new StringBuilder().append("\\").append(splitChar).toString();
            }
        }

        //生成匹配规则
        //分割字符串
        String[] splitArray = qrCodeValue.split(splitChar);
        for (int i = 0; i < splitArray.length; i++) {
            //匹配对应个数的字符
            stringBuilder.append(".{" + splitArray[i].length() + "}");
            if (i != splitArray.length - 1) {
                stringBuilder.append(splitChar);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * @param qrCodeValue 二维码的值
     * @param regex       正则
     * @param splitChar   分隔符
     * @param targetIndex 要取得的目标索引位置（从1）。规定0为不截取，整个原值直接使用
     * @return
     */
    public static String getUniqueIdentifier(String qrCodeValue, String regex, String splitChar, int targetIndex, int subIndex) {
        String identifier = "";
        //参数不符合条件时，返回空串。此时可直接调用下一次匹配
        if (qrCodeValue == null || qrCodeValue.length() == 0
                || regex == null || regex.length() == 0
                || splitChar == null || splitChar.length() == 0
                || targetIndex < 0
                || subIndex < 0) {
            return identifier;
        }

        //此时表明不需要截取，直接使用原值（美隆，油柑标签）
        if (targetIndex == 0) {
            return qrCodeValue;
        }

        //处理特殊符号的正则,移除特殊字符的转义字符
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < sSpecialCharArray.length; i++) {
            if (sSpecialCharArray[i].equals(splitChar)) {
                splitChar = sBuilder.append("\\").append(splitChar).toString();
                //重置分隔符之后清空Builder，后面拼接新的正则
                sBuilder.delete(0,splitChar.length());
                String[] split = regex.split(splitChar);
                for (String s : split) {
                    if (s.endsWith("\\")) {
                        s = s.substring(0, s.length() - 1);
                    }
                    sBuilder.append(s);
                }

                regex = sBuilder.toString();
                break;
            }
        }

        boolean result = Pattern.compile(regex).matcher(qrCodeValue).find();
        if (result) {
            String[] splitArray = qrCodeValue.split(splitChar);
            if (splitArray.length > targetIndex) {
                identifier = splitArray[targetIndex - 1].substring(subIndex);
            }
        }

        return identifier;
    }

}
