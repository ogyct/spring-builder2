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


    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        for (Class<? extends ObjectConfigurer> confs : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurer.class)) {
            configurers.add(confs.getDeclaredConstructor().newInstance());
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

        //PROXY pattern showcase

        if (implClass.isAnnotationPresent(Deprecated.class)) {
            return (T) Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    System.out.println("stop using deprecated stuff");
                    //async mechanism
                    //AOP territory
                    return method.invoke(t);

                }
            });
        } else {
            return t;
        }
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
