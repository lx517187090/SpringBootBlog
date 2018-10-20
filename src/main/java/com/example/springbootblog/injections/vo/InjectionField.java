package com.example.springbootblog.injections.vo;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by zhoul on 2017/6/5.
 */
public class InjectionField implements Serializable{
    private Annotation annotation;
    private Field annotationField;
    private Field injectField;

    public InjectionField(Annotation annotation, Field annotationField, Field injectField){
        this.annotation = annotation;
        this.annotationField = annotationField;
        this.injectField = injectField;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Field getAnnotationField() {
        return annotationField;
    }

    public void setAnnotationField(Field annotationField) {
        this.annotationField = annotationField;
    }

    public Field getInjectField() {
        return injectField;
    }

    public void setInjectField(Field injectField) {
        this.injectField = injectField;
    }
}
