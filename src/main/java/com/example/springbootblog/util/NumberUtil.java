package com.example.springbootblog.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by lixi on
 */
public class NumberUtil {

    // 数字转字符串
    public static String formatNumeric(double numeric, String pattern) {
        if (numeric == -0) {
            numeric = 0;
        }
        DecimalFormat decFormat = new DecimalFormat(pattern);
        return decFormat.format(numeric);
    }

    public static String formatNumeric(BigDecimal numeric, String pattern) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatNumeric(numeric.doubleValue(), pattern);
    }

    // 数字转逗号分隔字符串
    public static String formatNumeric(double numeric) {
        return formatNumeric(numeric, "#,##0.00");
    }

    public static String formatNumeric(BigDecimal numeric) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatNumeric(numeric.doubleValue());
    }

    // 数字转逗号分隔字符串,附加小数位数(保留8位小数，那么dec参数为6，即，最少要有2位小数)
    public static String formatNumeric(double numeric, int dec) {
        String p = "";
        for (int i = 0; i < dec; i++) {
            p += "#";
        }
        return formatNumeric(numeric, "#,##0.00" + p);
    }

    public static String formatNumeric(BigDecimal numeric, int dec) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatNumeric(numeric.doubleValue(), dec);
    }

    // 数字转逗号分隔字符串；如果数字为零，则返回空
    public static String formatNumericEx(double numeric) {
        String result;
        if (numeric != 0) {
            result = formatNumeric(numeric);
        } else {
            result = "0.00";
        }
        return result;
    }

    public static String formatNumericEx(BigDecimal numeric) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatNumericEx(numeric.doubleValue());
    }

    // 将大数字格式化为字符串，避免以科学计数法显示
    public static String formatDouble(double numeric) {
        return formatNumeric(numeric, "#0.00");
    }

    public static String formatDouble(BigDecimal numeric) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatDouble(numeric.doubleValue());
    }

    // 将大数字格式化为字符串，避免以科学计数法显示5位
    public static String formatDoubles(double numeric) {
        return formatNumeric(numeric, "#0.00000");
    }
    
    // 将大数字格式化为字符串，避免以科学计数法显示4位
    public static String formatDoubles(BigDecimal numeric,int dec) {
    	 String p = "";
    	 if(dec<2){
    		 dec = 2;
    	 }
         for (int i = 0; i < dec; i++) {
             p += "0";
         }
         
        return formatNumeric(numeric, "#0."+p);
    }

    public static String formatDoubles(BigDecimal numeric) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatNumeric(numeric.doubleValue(), "#0.00000");
    }

    public static String formatDoubleWithSix(double numeric) {
        return formatNumeric(numeric, "#0.000000");
    }

    public static String formatDoubleWithSix(BigDecimal numeric) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return formatNumeric(numeric.doubleValue(), "#0.000000");
    }

	// 将形如234，567.00的逗号分隔字符串格式化为Double
	public static double formatNumeric(String str) {
		try {
			return (new DecimalFormat("#,##0.00######")).parse(str)
					.doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 数据非空转换为零
	 */
	public static BigDecimal formatNumericNull(BigDecimal numeric) {
        if (numeric == null) {
            numeric = BigDecimal.ZERO;
        }
        return numeric;
    }
}
