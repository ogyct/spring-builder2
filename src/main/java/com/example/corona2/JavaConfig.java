package com.example.corona2;

import org.reflections.Reflections;

import java.util.Set;

public class JavaConfig implements Config {
    private Reflections scanner;

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        //assume 1 impl
        Set<Class<? extends T>> subTypesOf = scanner.getSubTypesOf(type);
        if (subTypesOf.size() != 1) {
            throw new RuntimeException("More than 1 impl");
        }

        return subTypesOf.iterator().next();
    }
}
