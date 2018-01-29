package com.jf.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 自定义异常
 *
 * @author fly
 */
public class CoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errorCode;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CoreException.class);

    public CoreException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CoreException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
