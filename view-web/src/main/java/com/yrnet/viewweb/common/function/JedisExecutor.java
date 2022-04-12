package com.yrnet.viewweb.common.function;


import com.yrnet.viewweb.common.exception.RedisConnectException;

/**
 * @author dengbp
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
