package org.simple.core;

import org.simple.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Map;

public class AutowiredParser {

    public static void parse(){
        Map<Class<?>, Object> beanMap = BeanManeger.getBeanMap();

        beanMap.forEach((clazz,bean)->{
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)){
                    Class<?> fieldType = field.getType();
                    Object fieldTypeInstance = beanMap.get(fieldType);
                    if (fieldTypeInstance != null){
                        try {
                            field.setAccessible(true);
                            field.set(bean,fieldTypeInstance);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
