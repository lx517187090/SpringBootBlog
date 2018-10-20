package com.example.springbootblog.common.hibernate.criterion;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * 解决Oracle中，in操作大于1000报错问题
 * Created by zhoul on 2017/12/5.
 */
public class RestrictionExtend {

    private static final int PARAMETER_LIMIT = 999;

    public static Criterion in(String propertyName, List<?> values) {
        Criterion criterion = null;
        int listSize = values.size();
        for (int i = 0; i < listSize; i += PARAMETER_LIMIT) {
            List<?> subList;
            if (listSize > i + PARAMETER_LIMIT) {
                subList = values.subList(i, (i + PARAMETER_LIMIT));
            } else {
                subList = values.subList(i, listSize);
            }
            if (criterion != null) {
                criterion = Restrictions.or(criterion, Restrictions.in(propertyName, subList));
            } else {
                criterion = Restrictions.in(propertyName, subList);
            }
        }
        return criterion;
    }

}


