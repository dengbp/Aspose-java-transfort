package com.yrnet.appweb.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
