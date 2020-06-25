package com.example.corona2;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config;
    private List<ObjectConfigurer> configurers = new ArrayList<>();

    public static ObjectFactory getOurInstance() {
        //homemade singleton, anitpattern
        return ourInstance;
    }

    @SneakyThrows
    private ObjectFactory() {
        config = new JavaConfig("com.example.corona2", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
        for (Class<? extends ObjectConfigurer> confs : config.getScanner().getSubTypesOf(ObjectConfigurer.class)) {
            configurers.add(confs.getDeclaredConstructor().newInstance());
        }
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

        configurers.forEach(objectConfigurer -> objectConfigurer.configure(t));
        //ObjectFactory breaks code
        //would be nice to support singletons

        return t;
    }
}
