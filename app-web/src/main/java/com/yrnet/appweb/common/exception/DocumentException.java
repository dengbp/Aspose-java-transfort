package com.yrnet.appweb.common.exception;

/**
 * ERP系统内部异常
 *
 * @author dengbp
 */
public class DocumentException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public DocumentException(String message) {
        super(message);
    }
}
