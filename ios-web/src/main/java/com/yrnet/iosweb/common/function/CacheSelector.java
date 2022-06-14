package com.yrnet.iosweb.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
