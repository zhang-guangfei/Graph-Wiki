package com.smc.smccloud.core.utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.github.pagehelper.PageInfo;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Denghui
 * Date: 2021-09-27 16:32
 * Description: bean拷贝
 */
public class BeanCopyUtil {

    private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<Class<?>, BeanCopier>> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<?>, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();


    public static void copy(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    public static <T> T copy(Object source, Class<T> target) {
        T t = getConstructorAccess(target).newInstance();
        copy(source, t);
        return t;
    }

    public static <T> List<T> copyList(List<?> sources, Class<T> target) {
        if (sources == null || sources.isEmpty()) {
            return Collections.emptyList();
        }
        ConstructorAccess<T> constructorAccess = getConstructorAccess(target);
        List<T> list = new ArrayList<>(sources.size());
        for (Object source : sources) {
            T t = constructorAccess.newInstance();
            copy(source, t);
            list.add(t);
        }
        return list;
    }

    private static <S, T> BeanCopier getBeanCopier(Class<S> sourceClass, Class<T> targetClass) {
        ConcurrentHashMap<Class<?>, BeanCopier> copierConcurrentHashMap = BEAN_COPIER_CACHE.computeIfAbsent(sourceClass, clazz -> new ConcurrentHashMap<>());
        return copierConcurrentHashMap.computeIfAbsent(targetClass, clazz -> BeanCopier.create(sourceClass, targetClass, false));
    }

    @SuppressWarnings("unchecked")
    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> type) {
        return CONSTRUCTOR_ACCESS_CACHE.computeIfAbsent(type, clazz -> ConstructorAccess.get(type));
    }

    /**
     * PageInfo<S>转换为PageInfo<T>
     *
     * @param source      PageInfo<S>
     * @param targetClass targetClass
     * @return PageInfo<T>
     */
    public static <S, T> PageInfo<T> pageDto2Vo(PageInfo<S> source, Class<T> targetClass) {
        PageInfo<T> pageVo = new PageInfo<>();
        List<T> list = copyList(source.getList(), targetClass);
        source.setList(null);
        copy(source, pageVo);
        pageVo.setList(list);
        return pageVo;
    }

    /**
     *   两个相同对象的值合并  比如 A1 - A2  合并之后 A2为主 具有A1属性的值
     * @param origin
     * @param destination  destination为主
     * @param <T>
     */
    public static <T> void mergeObject(T origin, T destination) {
        if (origin == null || destination == null)
            return;
        if (!origin.getClass().equals(destination.getClass()))
            return;

        Field[] fields = destination.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object valueD = fields[i].get(origin);
                Object valueO = fields[i].get(destination);
                if (null == valueO) {
                    fields[i].set(destination, valueD);
                }
                fields[i].setAccessible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* 遵守驼峰式命名规范进行命名则不存在大小写复制的问题，而且更好维护；对象属性null不复制的，可以使用中间对象编辑 */
//    /*
//     * 两个对象之间不分大小写进行值复制
//     *
//     * @param object
//     * @param cls
//     * @return
//     * @throws Exception
//     */
//    public static Object beanTobeanLowerCase(Object object, Class<?> cls) throws Exception {
//        Object obj = cls.newInstance();
//        if (object != null) {
//            Class<?> clsOld = object.getClass();
//            Field[] fieldsOld = clsOld.getDeclaredFields(); // 获取被复制的所有字段(将A中的所有值 赋值给B)
//            Field[] fields = obj.getClass().getDeclaredFields();
//            for (Field fieldOld : fieldsOld) {
//                String fieldNameOld = fieldOld.getName().replace("-", "");
//                fieldNameOld = fieldNameOld.replace("", "");
//                int modOld = fieldOld.getModifiers();
//                if (Modifier.isPrivate(modOld) && !Modifier.isFinal(modOld)) {
//                    fieldOld.setAccessible(true);
//                    for (Field field : fields) {
//                        String fieldName = field.getName().replace("-", "");
//                        fieldName = field.getName().replace("", "");
//                        int mod = field.getModifiers();
//                        if (Modifier.isPrivate(mod) && !Modifier.isFinal(mod)) {
//                            field.setAccessible(true);
//                            if (fieldNameOld.equalsIgnoreCase(fieldName)) {
//                                field.set(obj, fieldOld.get(object));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return obj;
//    }
//
//    /**
//     * source属性为空的不赋值给target
//     * @param source
//     * @param target
//     */
//    public static void beanCopierNotNull(Object source,Object target){
//        CopyConverter copyConverter = new CopyConverter(target);
//        BeanCopier copier = BeanCopier.create(source.getClass(),target.getClass(),true);
//        copier.copy(source,target,copyConverter);
//    }
}
