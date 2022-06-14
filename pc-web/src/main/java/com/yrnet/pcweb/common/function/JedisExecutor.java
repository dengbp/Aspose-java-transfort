package com.yrnet.pcweb.common.function;


import com.yrnet.pcweb.common.exception.RedisConnectException;

/**
 * @author dengbp
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
