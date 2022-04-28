package com.yrnet.appweb.common.function;


import com.yrnet.appweb.common.exception.RedisConnectException;

/**
 * @author dengbp
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
