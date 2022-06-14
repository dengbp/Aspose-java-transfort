package com.yrnet.pcweb.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
