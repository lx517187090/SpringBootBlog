package com.example.springbootblog.util;


import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 专门处理异常
 */
public class ConstraintViolationExceptionHandler  {

    /**
     * 获取批量异常信息
     * @param e
     */
    public static String getMessage(ConstraintViolationException e){
        List<String> msgList = new ArrayList<>();
        for(ConstraintViolation<?> constraintViolation:e.getConstraintViolations()){
            msgList.add(constraintViolation.getMessage());
        }
        String message = StringUtils.join(msgList.toArray(),";");
        return message;
    }

}
