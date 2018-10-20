package com.example.springbootblog.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by zhoul on 2017/6/13.
 */
public class ParameterUtils {

    private static final int maxOutputLengthOfParaValue = 512;


    /**
     * 获取request请求中的所有参数
     * @param request
     * @param sb
     * @return
     */
    public static void getParams(HttpServletRequest request, StringBuilder sb) {
        Enumeration<String> e = request.getParameterNames();
        if (e.hasMoreElements()) {
            sb.append("Parameter   : ");
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String[] values = request.getParameterValues(name);
                if (values.length == 1) {
                    sb.append(name).append("=");
                    if (values[0] != null && values[0].length() > maxOutputLengthOfParaValue) {
                        sb.append(values[0].substring(0, maxOutputLengthOfParaValue)).append("...");
                    } else {
                        sb.append(values[0]);
                    }
                } else {
                    sb.append(name).append("[]={");
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0)
                            sb.append(",");
                        sb.append(values[i]);
                    }
                    sb.append("}");
                }
                sb.append("  ");
            }
            sb.append("\n");
        }
    }
}
