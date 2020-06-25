package com.example.corona2;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedProxyConfigurer implements ProxyConfigurer {
    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {


        if (implClass.isAnnotationPresent(Deprecated.class)) {

            if (implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (net.sf.cglib.proxy.InvocationHandler) (o, method, args) -> {
                    return getInvocationHandlerLogic(t, method, args);
                });
            }
            //Conclusion look at CoronaDisinfectant

            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    return getInvocationHandlerLogic(t, method, objects);

                }
            });
        } else {
            return t;
        }
    }

    private Object getInvocationHandlerLogic(Object t, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        System.out.println("stop using deprecated stuff");
        //security could be here
        return method.invoke(t, args);
    }
}