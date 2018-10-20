package com.example.springbootblog.util;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Bean转换工具
 *
 * @author 周亮
 * @version 0.0.5
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * MAP集合转成bean对象集合
     *
     * @param listResult 需要拷贝的集合
     * @param clazz      需要将map转换成的Class
     * @param <B>        泛型
     * @return
     * @author 周亮
     */
    public static <B> List<B> copyMapListProperties(List listResult, Class<B> clazz) {
        List<B> sourceList = new ArrayList<>();
        Map<String, Object> cacheMap = new HashMap<>();
        //遍历MAP结果集
        for (Object obj : listResult) {
            Map map = (Map) obj;
            B source = copyMapProperties(map, clazz, cacheMap);
//            if (null != clazz) {
//                source = copyMapProperties(map, clazz, cacheMap);
//            } else {
//                Map<String, Object> newMap = new HashMap<>();
//                Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
//                while (entries.hasNext()) {
//                    Map.Entry<String, Object> entry = entries.next();
//                    newMap.put(entry.getKey().toLowerCase(), entry.getValue());
//                }
//                source = (B) newMap;
//            }
            sourceList.add(source);
        }
        return sourceList;
    }

    /**
     * MAP转成bean对象集合
     *
     * @param map
     * @param clazz
     * @param <B>
     * @return
     * @author 周亮
     */
    public static <B> B copyMapProperties(Map map, Class<B> clazz) {
        Map<String, Object> cacheMap = new HashMap<>();
        return copyMapProperties(map, clazz, cacheMap);
    }

    /**
     * 递归获取当前class的所有父类中的属性
     *
     * @param clazz
     * @param fieldList
     */
    public static void getAllFields(Class<?> clazz, List<Field> fieldList) {
        Class superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            getAllFields(superClass, fieldList);
        }
        Field[] fields = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
    }

    /**
     * MAP转成bean对象集合
     *
     * @param map
     * @param clazz
     * @param cacheMap
     * @param <B>
     * @return
     * @author 周亮
     */
    private static <B> B copyMapProperties(Map map, Class<B> clazz, Map<String, Object> cacheMap) {

        Assert.notNull(map, "map must not be null");
        Assert.notNull(clazz, "clazz must not be null");

        List<Field> fieldList = (List<Field>) cacheMap.get(clazz);
        if (fieldList == null) {
            fieldList = new ArrayList<>();
            getAllFields(clazz, fieldList);
        }

        B source = null;
        try {
            source = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //遍历结果MAP
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            //遍历映射实体属性
            for (Field field : fieldList) {
                String key = entry.getKey();
                Boolean flag = (Boolean) cacheMap.get(key + field.getName());
                if (flag == null) {
                    flag = !key.replace("_", "").equalsIgnoreCase(field.getName());
                    cacheMap.put(key + field.getName(), flag);
                }
                if (flag) {
                    continue;
                }

                Object value = entry.getValue();
                Class typeClass = field.getType();
                Object valueType = setFieldValueByFieldType(value,typeClass);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, source, valueType);
            }
        }
        return source;
    }

    private static Object setFieldValueByFieldType(Object value,Class typeClass){
        //这里去掉typeClass == double.class是为了代码规范，保证bean中的属性使用包装类
        if ((value instanceof Character) && typeClass == String.class) {
            Character character = (Character) value;
            value = character.toString();
        }else if (value instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) value;
            if (typeClass == Double.class) {
                value = bigDecimal.doubleValue();
            } else if (typeClass == Integer.class) {
                value = bigDecimal.intValue();
            } else if (typeClass == Long.class) {
                value = bigDecimal.longValue();
            } else {
                value = bigDecimal;
            }
        } else if (value instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) value;
            if (typeClass == Integer.class) {
                value = bigInteger.intValue();
            } else if (typeClass == Long.class) {
                value = bigInteger.longValue();
            } else {
                value = bigInteger;
            }
        } else if(value instanceof Integer){
            Integer integer = (Integer) value;
            if (typeClass == BigDecimal.class) {
                value = new BigDecimal(integer);
            } else if (typeClass == BigInteger.class){
                value = new BigInteger(String.valueOf(integer.longValue()));
            }else{
                value = integer;
            }
        } else if (value instanceof Double){
            Double doubleVal = (Double) value;
            if (typeClass == BigDecimal.class) {
                value = new BigDecimal(doubleVal);
            }else{
                value = doubleVal;
            }
        }
        return value;
    }

    /**
     * 作者：周亮
     * 批量转换List<Bean>
     *
     * @param source list集合
     * @param t      所成对象的class
     * @param <B>
     * @return List<T>
     * @throws BeansException
     */
    public static <S, B> List<B> copyListProperties(List<S> source, Class<B> t) throws BeansException {
        List<B> list = new ArrayList<>();
        if (source == null) {
            return list;
        }
        try {
            for (Object o : source) {
                B tObj = t.newInstance();
                copyProperties(o, tObj);
                list.add(tObj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static Map<String, Object> beanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {

        }
        return map;

    }

    /**
     * 把source和target相同属性的value复制到target中
     *
     * @param source 准备赋值对象
     * @param target 被赋值对象
     * @throws BeansException
     */
    public static void copyNotNullProperties(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, null);
    }

    public static <B> B copyNewInstanceProperties(Object source, Class<B> t) throws BeansException {
        B tObj = null;
        try {
            tObj = t.newInstance();
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        copyProperties(source, tObj);
        return tObj;
    }

    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (value != null) {  //只拷贝不为null的属性
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}
