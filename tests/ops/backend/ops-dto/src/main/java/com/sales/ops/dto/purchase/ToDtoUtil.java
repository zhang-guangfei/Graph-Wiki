package com.sales.ops.dto.purchase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Do转DTO 工具类
 */
public class ToDtoUtil {
    /**
     * @param po
     * @param dto
     * @return
     * @throws Exception
     *             PO转DTO工具类
     */
    public static Object PoToDto(Object po, Object dto) throws Exception{

        // 取得po对象的所有属性
        Field[] poFields = po.getClass().getDeclaredFields();
        // 取父类的所有属性
        Field[] superPoFields = po.getClass().getSuperclass().getDeclaredFields();
        // 合并数组
        poFields = (Field[]) mergeArray(poFields, superPoFields);

        // 遍历拼接dto的set方法字段表示
        for (Field f : poFields) {
            String fieldName = f.getName();
            // 取得当前get，set字符串表达形式
            String dtoSetMethodName = "set" + firstToBig(fieldName);
            String poGetMethodName = "get" + firstToBig(fieldName);

            // 取得DTO对象的set方法
            Method dtoSetMethod = null;
            try {
                dtoSetMethod = dto.getClass().getMethod(dtoSetMethodName, f.getType());
                // 取得Po对象的get方法
                Method poGetMethod = po.getClass().getMethod(poGetMethodName);
                // 将po对象的属性值set到dto对象中去
                dtoSetMethod.invoke(dto, poGetMethod.invoke(po));
            } catch (NoSuchMethodException e) {// 如果不存在此方法跳过，
                continue;
            }

        }
        return dto;

    }

    /**
     * 合并数组
     *
     * @param a
     * @param b
     * @return
     */
    public static Object[] mergeArray(Object[] a, Object[] b) {
        Object[] c = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 首字母大写
     *
     * @param fieldName
     * @return
     */
    public static String firstToBig(String fieldName) {
        if (fieldName != null && fieldName != "") {
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
        return fieldName;
    }
}
