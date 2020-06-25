package com.example.corona2;

import lombok.SneakyThrows;

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

        T t = implClass.getDeclaredConstructor().newInstance();

        configurers.forEach(objectConfigurer -> objectConfigurer.configure(t, context));

        //ObjectFactory doesn’t create itself 
        // No static things left
        //try announcer in constructor

        return t;
        //TOO many abstractions?
    }
}
