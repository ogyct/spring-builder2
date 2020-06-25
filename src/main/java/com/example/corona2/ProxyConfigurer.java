package com.example.corona2;

public interface ProxyConfigurer {
    Object replaceWithProxyIfNeeded(Object t, Class implClass);
}
