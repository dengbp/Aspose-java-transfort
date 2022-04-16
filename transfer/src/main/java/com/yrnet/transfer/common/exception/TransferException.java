package com.yrnet.transfer.common.exception;

/**
 * DBM系统内部异常
 *
 * @author dengbp
 */
public class TransferException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public TransferException(String message) {
        super(message);
    }
}
