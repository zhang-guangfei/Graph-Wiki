package com.sales.ops.dto.util;

/**
 * @description:
 * @author: B91717
 * @time: 2025/1/21 8:41
 */
public class DataLengthUtil {

    /**
     * 检查字符串在 SQL Server 中的字符长度是否超过指定长度
     *
     * @param input     输入的字符串
     * @param maxLength 最大允许长度（例如 30）
     * @return 如果长度合法，返回 true；否则返回 false
     */
    public static boolean isValidLength(String input, int maxLength) {
        if (input == null) {
            return true; // 如果为空，视为合法
        }
        int totalLength = 0;
        for (char c : input.toCharArray()) {
            // 判断字符是否为中文字符（或其他双字节字符）
            if (isChineseCharacter(c)) {
                totalLength += 2; // 中文字符算 2 个长度
            } else {
                totalLength += 1; // 非中文字符算 1 个长度
            }
            // 如果总长度超过最大长度，直接返回 false
            if (totalLength > maxLength) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符是否为中文字符
     *
     * @param c 字符
     * @return 如果是中文字符，返回 true；否则返回 false
     */
    private static boolean isChineseCharacter(char c) {
        // 中文字符的 Unicode 范围：0x4E00 - 0x9FFF
        return (c >= 0x4E00 && c <= 0x9FFF);
    }

    /**
     * 截取字符串，确保其在 SQL Server 中的字符长度不超过指定长度
     *
     * @param input     输入的字符串
     * @param maxLength 最大允许长度（例如 30）
     * @return 截取后的字符串
     */
    public static String truncateString(String input, int maxLength) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        int totalLength = 0;
        for (char c : input.toCharArray()) {
            int charLength = isChineseCharacter(c) ? 2 : 1;
            if (totalLength + charLength > maxLength) {
                break; // 超出长度，停止追加
            }
            result.append(c);
            totalLength += charLength;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String testString = "这是一个测试字符串啊啊啊啊啊test";

        // 检查长度是否合法
        if (isValidLength(testString, 30)) {
            System.out.println("字符串长度合法");
        } else {
            System.out.println("字符串长度超过限制");
        }

        // 截取字符串
        String truncatedString = truncateString(testString, 30);
        System.out.println("截取后的字符串: " + truncatedString);
    }
}
