package com.smc.smccloud.core.utils;

import java.util.regex.Pattern;

public class ChineseUtil {

    //中文标点符号
    final static String[] chinesePunctuation = {"！", "，", "。", "；", "《", "》", "（", "）", "？",
            "｛", "｝", "“", "：", "【", "】", "”", "‘", "’"};


    //英文标点符号
    final static String[] englishPunctuation = {"!", ",", ".", ";", "<", ">", "(", ")", "?",
            "{", "}", "\"", ":", "{", "}", "\"", "\'", "\'"};

    /**
     * 完整的判断中文汉字和符号
     */
    public static boolean containsChinese(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (isChinese(c[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 只能判断部分CJK字符（CJK统一汉字）
     */
    public static boolean containsChineseByREG(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 根据UnicodeBlock方法判断中文标点符号
     */
    public static boolean containsChinesePunctuation(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        final char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (isChinesePunctuation(c[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据UnicodeBlock方法判断中文标点符号
     */
    private static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 中文标点符号转英文字标点符号
     */
    public static String chinesePunctuationToEnglish(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        for (int i = 0; i < chinesePunctuation.length; i++) {
            if (str.contains(chinesePunctuation[i])) {
                str = str.replaceAll(chinesePunctuation[i], englishPunctuation[i]);
            }
        }
        return str;
    }
}