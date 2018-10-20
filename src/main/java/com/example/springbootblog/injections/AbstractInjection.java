package com.example.springbootblog.injections;


import com.example.springbootblog.injections.vo.InjectionField;
import com.example.springbootblog.util.BeanUtils;
import com.example.springbootblog.util.EmptyUtils;
import com.example.springbootblog.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhoul on 2017/12/7.
 */
public abstract class AbstractInjection implements IInjection {

    private Map<Class<? extends IBaseDataInject>, List<InjectionField>> baseDataInjectClassCache = new ConcurrentHashMap<>();

    private Map<Class, IBaseDataInjectService> multiInjectService = new ConcurrentHashMap<>();
    private Map<Class, IBaseDataInjectService> singleInjectService = new ConcurrentHashMap<>();


    @Override
    public void inject(List<? extends IBaseDataInject> list) {
        if (!EmptyUtils.isEmpty(list)) {
            for (IBaseDataInject baseDataInject : list) {
                inject(baseDataInject);
            }
        }
    }

    @Override
    public void inject(IBaseDataInject object) {
        if (!EmptyUtils.isEmpty(object)) {
            Class<? extends IBaseDataInject> actualEditable = object.getClass();
            //获取对象的所有属性
            List<InjectionField> fields = getField(actualEditable);
            //遍历属性
            for (InjectionField injectionField : fields) {
                //获取属性上带有注解的属性的值
                Object value = ReflectionUtils.getField(injectionField.getAnnotationField(), object);
                if (value == null) {
                    continue;
                }
                //获取需要被注入属性类型
                Class<?> injectField = injectionField.getInjectField().getType();
                if (injectField == String.class) {
                    IBaseDataInjectService baseDataInjectService = singleInjectService.get(injectionField.getAnnotation().annotationType());
                    String name = "";
                    if(!EmptyUtils.isEmpty(injectionField.getInjectField())){
                        name = injectionField.getInjectField().getName().toUpperCase();
                    }
                    String v = baseDataInjectService.returnInjectValue(name,value,injectionField.getAnnotation());
                    ReflectionUtils.setField(injectionField.getInjectField(), object, v);
                }
//            else {
//
//            }

//            Object objectValue = ReflectionUtils.getField(injectionField.getInjectField(), source);
//            if(objectValue == null){
//            }

            }
        }
    }

    /**
     * 获取所有属性字段，添加从缓存中获取
     *
     * @param actualEditable
     * @return
     */
    private List<InjectionField> getField(Class<? extends IBaseDataInject> actualEditable) {
        List<InjectionField> baseDataInjectClass = baseDataInjectClassCache.get(actualEditable);
        if (baseDataInjectClass == null) {
            baseDataInjectClass = new LinkedList<>();
            List<Field> fields = new ArrayList<>();
            BeanUtils.getAllFields(actualEditable, fields);
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                if (annotations.length == 0) {
                    continue;
                }
                List<Annotation> annotationList = new ArrayList<>();
                //获取多注解
                List<Annotation> multiInjectAnnotations = getMultiInjectAnnotations(annotations);
                if (!EmptyUtils.isEmpty(multiInjectAnnotations)) {
                    for (Annotation annotation : multiInjectAnnotations) {
                        IBaseDataInjectService baseDataInjectService = multiInjectService.get(annotation.annotationType());
                        Annotation[] singleAnnotations = baseDataInjectService.getMultiInjectAnnotations(annotation);
                        if (singleAnnotations != null && singleAnnotations.length > 0) {
                            annotationList.addAll(Arrays.asList(singleAnnotations));
                        }
                    }
                }
                //获取单注解
                List<Annotation> singleInjectAnnotations = getSingleInjectAnnotations(annotations);
                if (!EmptyUtils.isEmpty(singleInjectAnnotations)) {
                    annotationList.addAll(singleInjectAnnotations);
                }
                if (EmptyUtils.isEmpty(annotationList)) {
                    continue;
                }
                for (Annotation annotation : annotationList) {
                    //Dict d = (Dict)annotation;
                    IBaseDataInjectService baseDataInjectService = singleInjectService.get(annotation.annotationType());
                    //属性上是否存在需要注入的注解
                    String injectAttribute = baseDataInjectService.getInjectAttributeAnnotation(annotation);
                    //不存在注入注解或者注解中的inject属性名为空，结束本次循环
                    if (StringUtils.isBlank(injectAttribute)) {
                        continue;
                    }
                    //获取被注入属性描述对象
                    Field injectField = ReflectionUtils.findField(actualEditable, injectAttribute);
                    if (injectField == null) {
                        continue;
                    }
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.makeAccessible(injectField);
                    baseDataInjectClass.add(new InjectionField(annotation, field, injectField));
                }
            }
            baseDataInjectClassCache.put(actualEditable, baseDataInjectClass);
        }
        return baseDataInjectClass;
    }

    private List<Annotation> getInjectAnnotations(Annotation[] annotations, Map<Class, IBaseDataInjectService> dataInjectService) {
        List<Annotation> injectAnnotations = new ArrayList<>();
        for (Map.Entry<Class, IBaseDataInjectService> entry : dataInjectService.entrySet()) {
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(entry.getKey())) {
                    injectAnnotations.add(annotation);
                }
            }
        }
        return injectAnnotations;
    }

    private List<Annotation> getSingleInjectAnnotations(Annotation[] annotations) {
        return getInjectAnnotations(annotations, singleInjectService);
    }


    public void putSingleInjectService(Class classz, IBaseDataInjectService baseDataInjectService) {
        this.singleInjectService.put(classz, baseDataInjectService);
    }

    private List<Annotation> getMultiInjectAnnotations(Annotation[] annotations) {
        return getInjectAnnotations(annotations, multiInjectService);
    }

    public void putMultiInjectService(Class classz, IBaseDataInjectService baseDataInjectService) {
        this.multiInjectService.put(classz, baseDataInjectService);
    }


    /**
     * 对一个集合注入
     *
     * @param source
     * @param clazz
     */
    @Deprecated
    public void searchInject(List<?> source, Class<?> clazz) {
        if (!EmptyUtils.isEmpty(source) && source.size() >= 1 && source.get(0) != null && source.get(0) instanceof IBaseDataInject) {
            for (Object obj : source) {
                inject((IBaseDataInject) obj);
            }
        }
    }

    /**
     * 对单个对象注入
     *
     * @param obj
     * @param clazz
     */
    @Deprecated
    public void searchInject(Object obj, Class<?> clazz) {
        if (obj != null && obj instanceof IBaseDataInject) {
            inject((IBaseDataInject) obj);
        }
    }

}
