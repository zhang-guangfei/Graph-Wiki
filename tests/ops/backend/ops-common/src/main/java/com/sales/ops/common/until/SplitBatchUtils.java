package com.sales.ops.common.until;

import com.smc.smccloud.core.model.constants.GlobalConstant;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 批量拆分List工具类
 *
 * @author B28029
 * @version 1.0
 * @date 2022/7/11 14:22
 */
public class SplitBatchUtils {

    /**
     * 进行批量拆分insert数据
     * 按2100行数据成（字段不能大于2100）
     * 2100/字段长度=最大写入批量数据行
     * map key-value=>页码，数组实体
     *
     * @param list
     * @param <T>
     * @return
     */
//    public static <T> Map<Integer, List<T>> getInsertBatchBySqlserver1(List<T> list,Class<T> t) {
//        Map<Integer, List<T>> map = new HashMap<>();
//        //最大拆成行数
//        int MAX_SIZE =  2100/(getAllFields(t).length);//字段个数
//        //页码数
//        int page = list.size() % MAX_SIZE == 0 ? list.size() / MAX_SIZE : (list.size() / MAX_SIZE) + 1;
//        for (int i = 0; i < page; i++) {
//            //集合
//            map.put(i, list.subList(i * MAX_SIZE, page - 1 == i ? list.size() : (i + 1) * MAX_SIZE));
//        }
//        return map;
//    }

    /**
     * 进行批量拆分insert数据
     * 按2100行数据成（字段不能大于2100）
     * 2100/字段长度=最大写入批量数据行
     * map key-value=>页码，数组实体
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, List<T>> getInsertBatchBySqlserver(List<T> list, Class<T> t) {
        Map<Integer, List<T>> map = new HashMap<>();
        //单次最大写入行数=服务器最大处理值2100/字段个数（如有多出，则单次处理行数-1）
        int MAX_SIZE = 2100 % (getAllFields(t).length) == 0 ? 2100 / getAllFields(t).length : 2100 / getAllFields(t).length - 1;
        //页码数
        int page = list.size() % MAX_SIZE == 0 ? list.size() / MAX_SIZE : (list.size() / MAX_SIZE) + 1;
        for (int i = 0; i < page; i++) {
            //集合
            map.put(i, list.subList(i * MAX_SIZE, page - 1 == i ? list.size() : (i + 1) * MAX_SIZE));
        }
        return map;
    }

    /**
     * 返回对象字段
     *
     * @param t
     * @return
     */
    private static Field[] getAllFields(Class t) {
        List<Field> fieldList = new ArrayList<>();
        while (t != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(t.getDeclaredFields())));
            t = t.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

}
