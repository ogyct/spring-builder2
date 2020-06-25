package com.example.corona2;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> type);
}
