package com.jf.base;

import com.jf.exception.CoreException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 用于业务逻辑校验的“断言”控制，与常规的Assert断言区别在于抛出 @see ValidationException
 * 此类异常不会进行常规的logger.error记录，一般只在前端显示提示用户
 */
public class Validation {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new CoreException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new CoreException(message);
        }
    }

    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CoreException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CoreException(message);
        }
    }

}
