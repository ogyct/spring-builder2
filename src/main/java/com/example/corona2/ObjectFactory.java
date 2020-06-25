package com.example.corona2;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {

    private final ApplicationContext context;
    private List<ObjectConfigurer> configurers = new ArrayList<>();
    private List<ProxyConfigurer> proxyConfigurers = new ArrayList<>();


    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        for (Class<? extends ObjectConfigurer> confs : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurer.class)) {
            configurers.add(confs.getDeclaredConstructor().newInstance());
        }

        for (Class<? extends ProxyConfigurer> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurer.class)) {
            proxyConfigurers.add(aClass.getDeclaredConstructor().newInstance());
        }

    }

    /*
getBean in spring
 */
    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {

        T t = create(implClass);

        configure(t);

        invokeInit(implClass, t);


        //refactoring only
        return wrapWithProxyIfNeeded(implClass, t);
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t) {

        for (ProxyConfigurer proxyConfigurer : proxyConfigurers) {
            t = (T) proxyConfigurer.replaceWithProxyIfNeeded(t, implClass);
        }
        return t;
    }

    private <T> void invokeInit(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurers.forEach(objectConfigurer -> objectConfigurer.configure(t, context));
    }

    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }
}
