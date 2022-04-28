package com.yrnet.baseadmin.common.exception;

/**
 * DBM系统内部异常
 *
 * @author dengbp
 */
public class BaseAdminException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public BaseAdminException(String message) {
        super(message);
    }
}
