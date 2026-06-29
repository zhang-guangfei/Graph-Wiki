package com.smc.smccloud.core.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PublicUtil {

    private static final AtomicLong counter = new AtomicLong(0);
    private static final Random random = new Random();

    /**
     * 国内供应商字符串
     */
    public static final String IN_COUNTRY_SUPPER_CODE = "CM-CN-CT-GN-GZ-TZ-YZ";

    /**
     * 判断对象是否Empty(null或元素为0) 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param obj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return "".equals(obj);
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else {
            return false;
        }
    }

    /**
     * 返回两个集合相交部分数据
     * @param list1
     * @param list2
     * @return
     * @param <T>
     */
    public static <T> List<T> getIntersection(List<T> list1, List<T> list2) {
        // 将第一个列表转换为集合
        Set<T> set1 = new HashSet<>(list1);
        // 创建一个用于存储相交部分的列表
        List<T> intersection = new ArrayList<>();

        // 遍历第二个列表，检查是否有元素在第一个集合中
        for (T element : list2) {
            if (set1.contains(element)) {
                intersection.add(element); // 找到相交元素，添加到结果列表中
            }
        }

        return intersection; // 返回相交部分的列表
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素大于0) 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param obj 待检查对象
     * @return boolean 返回的布尔值
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    // 计算增占率
    // public static double calcIncreaseRate(double curNum, double lastNum) {
    // if (curNum == 0 || lastNum == 0) {
    // return 0;
    // }
    //
    // double rate = curNum / lastNum;
    // BigDecimal bg = new BigDecimal(rate);
    //
    // return bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    // }

    public static Double Round(Double val, int scale) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(scale);
        return Double.valueOf(nf.format(val));
    }

    public static String Sha1(String str) {

        MessageDigest crypt;
        try {
            crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            byte digest[] = crypt.digest();
            Formatter formatter = new Formatter();
            for (byte b : digest) {
                formatter.format("%02x", b);
            }
            String result = formatter.toString();
            formatter.close();
            return result;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getKeyList(HashMap<String, String> map, String value) {
        List<String> keyList = new ArrayList<>();
        for (String getKey : map.keySet()) {
            if (map.get(getKey).equals(value)) {
                keyList.add(getKey);
            }
        }
        return keyList;
    }

    public static String html2Text(String html) {
        String txtcontent = html.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
        txtcontent = txtcontent.replaceAll("&nbsp;|\\s*|\t|\r|\n", "");// 去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }

    public static String getNextMonthDate(String dateStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 将日期调到下个月的第一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // 输出结果
        return sdf.format(calendar.getTime());
    }

    public static int getDaysInMonth(int year, int month) {
        // 创建一个Calendar对象并设置年份和月份
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // 月份从0开始，所以要减1

        // 获取当月的最大天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return daysInMonth;
    }

    public static boolean isValidDate(String date) {
        try {
            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 将字符串转换为本地日期对象
            LocalDate localDate = LocalDate.parse(date, formatter);

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 判断供应商代码是否为国内供应商
     *
     * @param supperCode 供应商代码
     * @return boolean 返回的布尔值
     */
    public static boolean isInCountry(String supperCode) {
        if (isEmpty(supperCode)) {
            return false;
        }
        return IN_COUNTRY_SUPPER_CODE.contains(supperCode);
    }


    // 判断 '-' 分隔符出现的次数
    public static int showCountByStr(String str) {
        int oldLength = str.length();
        String newStr = str.replaceAll("-", "");
        int newLength = newStr.length();
        return oldLength - newLength;
    }


    /**
     *  根据分隔符 分割字符串
     * @param val 字符串
     * @param spt 分隔符
     * @return  比如 12345-45-1  返回 new String[]{12345,12345-45}
     */
    public static String[] getValBySeparator(String val ,String spt) {
        //获得第一个点的位置
        int index = val.indexOf(spt);
        String result = val.substring(0,index);
        //根据第一个点的位置 获得第二个点的位置
        index = val.indexOf(spt, index + 1);
        //根据第二个点的位置，截取 字符串。得到结果 result
        String result2 = val.substring(0,index);
        return new String[]{result,result2};
    }

    public static boolean isNum(String str) {

        Pattern pattern = Pattern.compile("^-?[0-9]+");
        if (pattern.matcher(str).matches()) {
            //数字
            return true;
        } else {
            //非数字
            return false;
        }
    }

    public static String getRandomStr() {
        Random random = new Random();
        String randomString = "";
        for (int i = 0; i < 20; i++) {
            int randomNumber = random.nextInt(26);
            char letter = (char) ('a' + randomNumber);
            randomString += letter;
        }
        return randomString;
    }

    public static boolean exactDivisor(int big, int small) {
        // 转换bid为double类型。使得运算变为：double变量和int变量运算，结果为double。
        double result = (double) big / small;
        // double result不一定是big和small的精确地商，但我们不需要精确得的商。只需要判断小数部分。
        String str = String.valueOf(result);    // 转换结果为字符串
        // 用正则表达式判断：有无小数部分，若有，小数部分是否全为0。
        return str.matches("^([1-9]\\d*(\\.0+))$"); // 正则匹配
    }

    // 获取字符串字节长度
    public static int getWordByteLen(String s) {
        return s.getBytes(StandardCharsets.UTF_8).length;
    }

    // 多次加密
    public static  String encryptionPwd(String pwd, int count) {
        for (int i = 1 ; i<=count; i++) {
            pwd =  java.util.Base64.getEncoder().encodeToString(pwd.getBytes());
        }
        return pwd;
    }
    // 多次解密
    public static  String decipheringPwd(String pwd, int count) {
        for (int i = 1 ; i<=count; i++) {
            byte[] decodeBytes = java.util.Base64.getDecoder().decode(pwd);
            pwd = new String(decodeBytes);
        }
        return pwd;
    }

    // 返回最后一个指定字符后面的字符串
    public static String getLastStrBySeparator(String str, String separator) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(separator)) {
            return null;
        }
        return str.substring(str.lastIndexOf(separator)+1);
    }

    // 返回最后一个指定字符串之前的字符串
    public static String getBeforeLastSepaStr(String str, String separator) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(separator)) {
            return null;
        }
       return str.substring(0,str.lastIndexOf(separator));
    }

    public static void setObjFieldVal(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("error while set obj field value", e);
        }
    }

    public static Object getFieldValueByObject(Object object, String targetFieldName) {
        Object result = null;
        Class objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            String currentFieldName = "";
            try {
                boolean hasJsonProperty = field.isAnnotationPresent(JsonProperty.class);
                if (hasJsonProperty) {
                    currentFieldName = field.getAnnotation(JsonProperty.class).value();
                } else {
                    currentFieldName = field.getName();
                }
                if (currentFieldName.toUpperCase().equals(targetFieldName.toUpperCase())) {
                    field.setAccessible(true);
                    result = field.get(object);
                    return result;
                }
            } catch (SecurityException e) {
                throw new RuntimeException("获取对象的属性值安全性异常：" + e.getMessage());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("获取对象的属性值非法参数异常：" + e.getMessage());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("获取对象的属性值无访问权限异常：" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 截取返回某字符第n次出现位置之后的字符
     * @param data
     * @param str
     * @param num
     * @return
     */
    public static String getStringByIndexOf(String data,String str,int num){
        if(StringUtils.isBlank(data)) {
            return null;
        }
        Pattern pattern = Pattern.compile(str);
        Matcher findMatcher = pattern.matcher(data);
        //标记遍历字符串的位置
        int indexNum=0;
        while(findMatcher.find()) {
            indexNum++;
            if(indexNum==num){
                break;
            }
        }
        return  data.substring(findMatcher.start());
    }

    public static String getRandomBatchNo(String pre, Date curDate) {

        // 将时间戳转换为字符串
        String timestampStr = String.valueOf(curDate.getTime());

        // 获取时间戳的最后 6 位数字
        String last6Digits = timestampStr.substring(timestampStr.length() - 6);

        // 生成随机数
        Random random = new Random(curDate.getTime());
        int randomNumber = random.nextInt(999999);
        // int randomNumber = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;

        String uuid = UUID.randomUUID().toString();

            // 时间戳后6位+6位随机数+4位UID后6位
        String randomStr = last6Digits + String.valueOf(randomNumber) + uuid.substring(uuid.length()-4);

        return pre+randomStr;
    }

    /**
     * 随机生成3位有效字符
     * @return
     */
    public static String getRandomStrForThreelen() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 包含所有有效字符的集合

        StringBuilder randomCharacters = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            int index = rand.nextInt(characters.length());
            char character = characters.charAt(index);

            randomCharacters.append(character);
        }

        return  randomCharacters.toString();
    }
    /**
     * 转为字符串
     *
     * @param obj
     * @return
     */
    public static String objToString(Object obj) {

        if (ObjectUtils.isEmpty(obj)) {
            return "";
        }
        if (obj instanceof Date) {
            Date date = (Date) obj;
            if (ObjectUtils.isEmpty(date)) {
                return obj.toString();
            }
            Calendar cd = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            cd.setTime(date);
            if (cd.get(Calendar.HOUR_OF_DAY) > 0) {
                return DateUtil.dateToDateTimeString(date);
            }
            return DateUtil.dateToDateString(date);

        } else {
            return obj.toString();
        }
    }

    public static String generateUID() {
        long uniqueNumber = counter.incrementAndGet();
        StringBuilder uid = new StringBuilder();

        for (int i = 0; i < 20; i++) {
            int randomCharIndex = random.nextInt(62);
            char randomChar;
            if (randomCharIndex < 10) {
                randomChar = (char) ('0' + randomCharIndex);
            } else if (randomCharIndex < 36) {
                randomChar = (char) ('A' + randomCharIndex - 10);
            } else {
                randomChar = (char) ('a' + randomCharIndex - 36);
            }
            uid.append(randomChar);
        }
        return uid.toString();
    }

    public static String getLastStrBySeparator(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        String[] parts = str.split(";");

        List<Integer> numbers = Arrays.stream(parts)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        numbers.sort(Integer::compareTo);

        String sortedString = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";"));

        sortedString += ";";
        return sortedString;
    }
}
