package com.example.corona2;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;

    public static ObjectFactory getOurInstance() {
        //homemade singleton, anitpattern
        return ourInstance;
    }

    private ObjectFactory() {
        config = new JavaConfig("com.example.corona2", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
    }

    /*
getBean in spring
 */
    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        Class<? extends T> implClass = type;
        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = implClass.getDeclaredConstructor().newInstance();

        //TODO all object configuration magic here

        return t;
        //TOO many abstractions?
    }
}
