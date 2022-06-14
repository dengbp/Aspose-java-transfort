package com.yrnet.iosweb.common.function;


import com.yrnet.iosweb.common.exception.RedisConnectException;

/**
 * @author dengbp
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
