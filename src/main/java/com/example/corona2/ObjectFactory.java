package com.example.corona2;

import lombok.SneakyThrows;

public class ObjectFactory {

    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config = new JavaConfig("com.example.corona2");



    public static ObjectFactory getOurInstance() {
        //homemade singleton, anitpattern
        return ourInstance;
    }

    private ObjectFactory() {
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
        return implClass.getDeclaredConstructor().newInstance();
        //TOO many abstractions?
    }
}
