package com.example.corona2;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class,Class> ifc2ImplClass) {
        this.scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        //assume 1 impl
        return ifc2ImplClass.computeIfAbsent(type, aClass -> {
            //assume 1 impl
            Set<Class<? extends T>> subTypesOf = scanner.getSubTypesOf(type);
            if (subTypesOf.size() != 1) {
                throw new RuntimeException("Has 0 or >1 impl, pls update config");
            }

            return subTypesOf.iterator().next();
        });
    }
}
