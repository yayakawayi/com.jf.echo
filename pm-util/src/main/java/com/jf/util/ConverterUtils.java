package com.jf.util;

import jodd.datetime.JDateTime;
import jodd.typeconverter.Convert;

/**
 * *************************************************************************************************<br>
 * <br>
 * 实现功能：<br>
 * 类型转换工具栏
 * <br>
 * ------------------------------------------------------------------------------------------------<br>
 * 版本          变更时间             变更人                     变更原因<br>
 * ------------------------------------------------------------------------------------------------<br>
 * 1.0.00      2017/4/27 13:47      马超(Vision.Mac)                新建<br>
 * <br>
 * *************************************************************************************************<br>
 */
public final class ConverterUtils {

    private ConverterUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> type, final Object value) {
        return (T) convert(type.getName(), value);
    }

    public static Object convert(String type, Object value) {
        Object val = null;
        if (value != null) {
            try {
                if ("java.lang.Byte".equals(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toByte(value);
                    }
                } else if ("java.lang.Short".equals(type) || "short".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toShort(value);
                    }
                } else if ("java.lang.Integer".equals(type) || "int".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toInteger(value);
                    }
                } else if ("java.lang.Long".equals(type) || "long".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toLong(value);
                    }
                } else if ("java.lang.Float".equals(type) || "float".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toFloat(value);
                    }
                } else if ("java.lang.Double".equals(type) || "double".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toDouble(value);
                    }
                } else if ("java.math.BigDecimal".equals(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toBigDecimal(value);
                    }
                } else if ("java.lang.Boolean".equals(type) || "boolean".equalsIgnoreCase(type) || "bool".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toBoolean(value);
                    }
                } else if ("java.lang.String".equals(type) || "string".equalsIgnoreCase(type) || "str".equalsIgnoreCase(type)) {
                    val = Convert.toString(value);
                } else if ("java.util.Date".equals(type) || "date".equalsIgnoreCase(type)) {
                    if (value.toString().length() > 0) {
                        val = Convert.toDate(value);
                    }
                } else if ("java.sql.Date".equals(type)) {
                    if (value.toString().length() > 0) {
                        JDateTime jDateTime = new JDateTime(value.toString());
                        val = jDateTime.convertToSqlDate();
                    }
                } else if ("java.sql.Timestamp".equals(type)) {
                    if (value.toString().length() > 0) {
                        JDateTime jDateTime = new JDateTime(value.toString());
                        val = jDateTime.convertToSqlTimestamp();
                    }
                } else {
                    val = value;
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return val;
    }
}
