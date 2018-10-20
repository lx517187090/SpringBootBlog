package com.example.springbootblog.proxy;

import com.example.springbootblog.annotation.CompositeSearch;
import com.example.springbootblog.enums.MatchTypeEnum;
import com.example.springbootblog.util.Reflections;
import com.example.springbootblog.util.StringUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 动态修改查询注解
 */
public class SearchDynamically {
    protected static final String pkgName = "com.bytter.core.proxy";//代理类包名
    protected static final String className = "DynamicSearchBeanProxy";
    /**
     * 缓存类的属性信息
     */
    private static Map<String, List<Field>> beanCache = new HashMap<String, List<Field>>();


    public static Map<String, Map<String, Object>> searchAnnotationMap(String alias, Object bean, ArrayListMultimap<String, Object> typeMap, Map<String, String> orderMap, Object... needParams) throws Exception {
        Map<String, Map<String, Object>> map = new LinkedHashMap<>();
        if (bean == null) {
            //返回map处理 条件处理
            for (String fieldName : typeMap.keySet()) {
                if (null != needParams && needParams.length > 0) {
                    List<Object> objects = Arrays.asList(needParams);
                    if (!objects.contains(fieldName)) {
                        continue;
                    }
                }
                Map<String, Object> cMap = new LinkedHashMap<>();
                List<String> operList = Lists.newArrayList();
                List<String> fieldNameList = Lists.newArrayList();
                String order = orderMap.get(fieldName);
                Collection<Object> value = typeMap.get(fieldName);
                if (fieldName.indexOf(".") < 1) {
                    fieldName = StringUtils.isBlank(alias) ? fieldName : alias + "." + fieldName;
                }
                fieldNameList.add(fieldName);
                //operCode
                for (Object matchTypeEnum : value) {
                    if (null == matchTypeEnum) {
                        operList.add(MatchTypeEnum.EQ.getValue());
                    } else {
                        if (matchTypeEnum instanceof MatchTypeEnum) {
                            operList.add(((MatchTypeEnum) matchTypeEnum).getValue());
                        }
                        if (matchTypeEnum instanceof Map) {
                            Map<String, Object> objectMap = (Map) matchTypeEnum;
                            StringBuilder sb = new StringBuilder();
                            MatchTypeEnum typeEnum = null;
                            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                                fieldName = entry.getKey();
                                typeEnum = (MatchTypeEnum) entry.getValue();
                                if (fieldName.indexOf(".") < 1) {
                                    fieldName = StringUtils.isBlank(alias) ? fieldName : alias + "." + fieldName;
                                }
                                sb.append(fieldName).append(",");
                            }
                            if (sb.length() > 0) {
                                sb.setLength(sb.length() - 1);
                            }
                            fieldNameList.add(sb.toString());
                            operList.add(typeEnum == null ? MatchTypeEnum.EQ.getValue() : typeEnum.getValue());
                        }
                    }
                }
                cMap.put("fieldName", fieldNameList.toArray(new String[fieldNameList.size()]));
                cMap.put("operCode", operList.toArray(new String[operList.size()]));
                cMap.put("orderBy", StringUtils.isBlank(order) ? "" : order);
                map.put(fieldName, cMap);
            }
            //排序处理
            for (String orderName : orderMap.keySet()) {
                String order = orderMap.get(orderName);
                if (map.get(orderName) == null) {
                    if (orderName.indexOf(".") < 1) {
                        orderName = StringUtils.isBlank(alias) ? orderName : alias + "." + orderName;
                    }
                    Map<String, Object> cMap = new LinkedHashMap<>();
                    cMap.put("fieldName", new String[]{orderName});
                    cMap.put("operCode", new String[0]);
                    cMap.put("orderBy", StringUtils.isBlank(order) ? "" : order);
                    map.put(orderName, cMap);
                }
            }
            return map;
        }
        List<Field> fieldList = beanCache.get(bean.getClass().getName());
        if (fieldList == null) {
            fieldList = Reflections.fields(bean.getClass());
            beanCache.put(bean.getClass().getName(), fieldList);
        }
        for (Field field : fieldList) {
            Method getMethod = getGetMethod(bean.getClass(), field.getName());
            Method setMethod = getSetMethod(bean.getClass(), field.getName());
            String order = orderMap.get(field.getName());
            if (getMethod != null && setMethod != null) {
                Map<String, Object> cMap = new LinkedHashMap<>();
                CompositeSearch btCompositeSearch = field.getAnnotation(CompositeSearch.class);
                if (field.isAnnotationPresent(CompositeSearch.class)) {
                    if (null != needParams && needParams.length > 0) {
                        List<Object> objects = Arrays.asList(needParams);
                        if (!objects.contains(field.getName())) {
                            continue;
                        }
                    }
                    List<String> operList = Lists.newArrayList();
                    List<String> fieldNameList = Lists.newArrayList();
                    Collection<Object> list = typeMap.get(field.getName());
                    StringBuffer mapKey = new StringBuffer();
                    //operCode
                    for (Object matchTypeEnum : list) {
                        if (null == matchTypeEnum) {
                            String[] fieldName = btCompositeSearch.fieldName();
                            if (null == fieldName || fieldName.length == 0) {
                                fieldName = new String[]{field.getName()};
                            }
                            if (fieldName[0].indexOf(".") < 1) {
                                fieldName[0] = StringUtils.isBlank(alias) ? fieldName[0] : alias + "." + fieldName[0];
                            }
                            fieldNameList.add(fieldName[0]);
                            operList.add(MatchTypeEnum.EQ.getValue());
                        } else {
                            if (matchTypeEnum instanceof MatchTypeEnum) {
                                String[] fieldName = btCompositeSearch.fieldName();
                                if (null == fieldName || fieldName.length == 0) {
                                    fieldName = new String[]{field.getName()};
                                }
                                if (fieldName[0].indexOf(".") < 1) {
                                    fieldName[0] = StringUtils.isBlank(alias) ? fieldName[0] : alias + "." + fieldName[0];
                                }
                                fieldNameList.add(fieldName[0]);
                                operList.add(((MatchTypeEnum) matchTypeEnum).getValue());
                            }
                            if (matchTypeEnum instanceof Map) {
                                Map<String, Object> objectMap = (Map) matchTypeEnum;
                                StringBuilder nameStr = new StringBuilder("");
                                MatchTypeEnum typeEnum = null;
                                for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                                    String name = entry.getKey();
                                    mapKey.append(name).append("#");
                                    Field nameField = Reflections.findField(bean.getClass(), name);
                                    if (null != nameField) {
                                        if (nameField.isAnnotationPresent(CompositeSearch.class)) {
                                            CompositeSearch fieldAnnotation = nameField.getAnnotation(CompositeSearch.class);
                                            String[] annName = fieldAnnotation.fieldName();
                                            if (null == annName || annName.length == 0) {
                                                annName = new String[]{field.getName()};
                                            }
                                            name = annName[0];
                                        } else {
                                            name = nameField.getName();
                                        }
                                    }
                                    typeEnum = (MatchTypeEnum) entry.getValue();
                                    if (name.indexOf(".") < 1) {
                                        name = StringUtils.isBlank(alias) ? name : alias + "." + name;
                                    }
                                    nameStr.append(name).append(",");
                                }
                                if (nameStr.length() > 0) {
                                    nameStr.setLength(nameStr.length() - 1);
                                }
                                fieldNameList.add(nameStr.toString());
                                operList.add(typeEnum == null ? MatchTypeEnum.EQ.getValue() : typeEnum.getValue());
                            }
                        }
                    }
                    if (mapKey.length() > 0) {
                        mapKey.setLength(mapKey.length() - 1);
                    }
                    cMap.put("fieldName", fieldNameList.toArray(new String[fieldNameList.size()]));
                    cMap.put("operCode", operList.toArray(new String[operList.size()]));
                    cMap.put("orderBy", StringUtils.isBlank(order) ? "" : order);
                    cMap.put("mapKey", mapKey.toString());
                } else {
                    continue;
                }
                map.put(field.getName(), cMap);
            }
            //TODO:排序处理
            String orderName = field.getName();
            if (StringUtils.isNotBlank(order)) {
                String filedName = orderName;
                Map<String, Object> cMap = Maps.newHashMap();
                if (map.get(orderName) == null || (map.get(orderName) != null && (map.get(orderName).get("fieldName") == null || ((String[]) map.get(orderName).get("fieldName")).length == 0))) {
                    Field nameField = Reflections.findField(bean.getClass(), orderName);
                    if (null != nameField) {
                        if (nameField.isAnnotationPresent(CompositeSearch.class)) {
                            CompositeSearch fieldAnnotation = nameField.getAnnotation(CompositeSearch.class);
                            String[] annName = fieldAnnotation.fieldName();
                            if (null == annName || annName.length == 0) {
                                annName = new String[]{field.getName()};
                            }
                            orderName = annName[0];
                        } else {
                            orderName = nameField.getName();
                        }
                    }
                    if (orderName.indexOf(".") < 1) {
                        orderName = StringUtils.isBlank(alias) ? orderName : alias + "." + orderName;
                    }
                    cMap.put("fieldName", new String[]{orderName});
                    cMap.put("operCode", new String[0]);
                    cMap.put("mapKey", "");
                } else {
                    cMap.put("fieldName", map.get(orderName).get("fieldName"));
                    cMap.put("operCode", map.get(orderName).get("operCode"));
                    cMap.put("mapKey", map.get(orderName).get("mapKey"));
                }
                cMap.put("orderBy", order);
                map.put(filedName, cMap);
            }
        }
        return map;
    }

    public static Class<?> searchAnnotation(String alias, Object bean, ArrayListMultimap<String, MatchTypeEnum> typeMap, Map<String, String> orderMap) throws Exception {
        if (bean == null) {
            return null;
        }
        List<Field> fieldList = beanCache.get(bean.getClass().getName());
        if (fieldList == null) {
            fieldList = Reflections.fields(bean.getClass());
            beanCache.put(bean.getClass().getName(), fieldList);
        }

        ClassPool cp = new ClassPool(true);//ClassPool.getDefault()
        String classname = bean.getClass().getName();
        CtClass cc = cp.makeClass(classname);
        String name = bean.getClass().getName() + "." + StringUtils.getUUId();
        cc.setName(name);
        //CtClass cc = cp.getCtClass(pkgName + "." + className);
        //cc.defrost();
        //String name = bean.getClass().getName() + "." + UUIDGenerator.generate();
        //cc.setName(name);
        for (Field field : fieldList) {
            Method getMethod = getGetMethod(bean.getClass(), field.getName());
            Method setMethod = getSetMethod(bean.getClass(), field.getName());
            if (getMethod != null && setMethod != null) {
                //动态创建属性
                CtField ctField = new CtField(cp.getCtClass(field.getType().getName()), field.getName(), cc);
                ctField.setModifiers(Modifier.PRIVATE);
                //cc.addField(ctField, CtField.Initializer.constant("default"));
                cc.addField(ctField);
                //设置属性的get set方法
                cc.addMethod(CtNewMethod.setter(setMethod.getName(), ctField));
                cc.addMethod(CtNewMethod.getter(getMethod.getName(), ctField));
                //为属性添加注解
                addAnnotation(alias, cc, field, CompositeSearch.class.getName(), typeMap, orderMap);
            }
        }
        //Class<?> c = cc.toClass(new DynamicSearchBeanProxy(), DynamicSearchBeanProxy.class.getProtectionDomain());
        Class c = cc.toClass();
        cc.stopPruning(true);
        cc.detach();//使用完删除
        return c;
    }

    protected static void addAnnotation(String alias, CtClass clazz, Field field, String annotationName,
                                        ArrayListMultimap<String, MatchTypeEnum> typeMap, Map<String, String> orderMap) throws Exception {
        ClassFile cfile = clazz.getClassFile();
        ConstPool cpool = cfile.getConstPool();
        CtField cfield = clazz.getField(field.getName());
        Collection<MatchTypeEnum> list = typeMap.get(field.getName());
        ArrayMemberValue arrayMemberValue = new ArrayMemberValue(cpool);
        List<StringMemberValue> operList = Lists.newArrayList();
        //operCode
        for (MatchTypeEnum matchTypeEnum : list) {
            operList.add(new StringMemberValue(matchTypeEnum == null ? MatchTypeEnum.EQ.getValue() : matchTypeEnum.getValue(), cpool));
        }
        StringMemberValue[] values = new StringMemberValue[operList.size()];
        arrayMemberValue.setValue(operList.toArray(values));

        AnnotationsAttribute attr = new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
        Annotation annot = new Annotation(annotationName, cpool);
        CompositeSearch btCompositeSearch = field.getAnnotation(CompositeSearch.class);
        String order = orderMap.get(field.getName());
        if (field.isAnnotationPresent(CompositeSearch.class)) {
            ArrayMemberValue fieldNameValue = new ArrayMemberValue(cpool);
            List<StringMemberValue> fieldNameList = Lists.newArrayList();
            String[] fieldName = btCompositeSearch.fieldName();
            for (String name : fieldName) {
                if (name.indexOf(".") < 1) {
                    name = StringUtils.isBlank(alias) ? name : alias + "." + name;
                }
                fieldNameList.add(new StringMemberValue(name, cpool));
            }
            StringMemberValue[] fieldNames = new StringMemberValue[fieldNameList.size()];
            fieldNameValue.setValue(fieldNameList.toArray(fieldNames));

            annot.addMemberValue("fieldName", fieldNameValue);
            annot.addMemberValue("operCode", arrayMemberValue);
            annot.addMemberValue("orderBy", new StringMemberValue(StringUtils.isBlank(order) ? "" : order, cpool));
            attr.addAnnotation(annot);
            cfield.getFieldInfo().addAttribute(attr);
        } else {
            annot.addMemberValue("fieldName", new StringMemberValue(StringUtils.isBlank(alias) ? field.getName() : alias + "." + field.getName(), cpool));
            annot.addMemberValue("operCode", arrayMemberValue);
            annot.addMemberValue("orderBy", new StringMemberValue(StringUtils.isBlank(order) ? "" : order, cpool));
            attr.addAnnotation(annot);
            cfield.getFieldInfo().addAttribute(attr);
        }
    }

    /**
     * java反射bean的set方法
     *
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    protected static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * java反射bean的get方法
     *
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    protected static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }
}
