package com.example.springbootblog.annotation.impl;

import com.example.springbootblog.annotation.ByteRangeLength;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 */
public class ByteRangeLengthImpl implements ConstraintValidator<ByteRangeLength,Object> {
    protected  final Logger logger = LoggerFactory.getLogger(getClass());
    private int value;
    @Override
    public void initialize(ByteRangeLength byteRangeLength) {
        this.value = byteRangeLength.value();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if(String_length(o)<=value){
                return true;
            }
        }catch (Exception e){
            logger.error("com.bytter.core.annotation.impl.ByteRangeLengthImpl ",e);
        }
        return false;
    }

    public int String_length(Object value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.toString().length(); i++) {
            String temp = value.toString().substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
