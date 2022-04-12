package com.yrnet.viewweb.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
