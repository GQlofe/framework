package org.simple.core;

import org.simple.annotation.Controller;
import org.simple.annotation.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean管理器
 */
public class BeanManeger {

    /**
     * Bean容器，存储所有此框架管理的Bean
     */
    private static final Map<Class<?>, Object> beanMap;


    static {
        beanMap = initBeans();

        // 依赖注入
        AutowiredParser.parse();
    }

    public static Map<Class<?>, Object> getBeanMap(){
        return beanMap;
    }

    public static Map<Class<?>, Object> initBeans(){

        Map<Class<?>, Object> beanMap = new HashMap<>();
        Set<Class<?>> classSet = ClassManager.getClasses();
        for (Class<?> clazz : classSet) {
            if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)){
                beanMap.put(clazz, ClassManager.newInstance(clazz));
            }
        }
        return beanMap;
    }

}
