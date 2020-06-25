package com.example.corona2;

import lombok.SneakyThrows;

import javax.naming.spi.ObjectFactory;
import java.lang.reflect.Field;

public class InjectByTypeObjectConfigurerImpl implements ObjectConfigurer {

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
                //now can't create object using new (main)
            }
        }

    }
}
