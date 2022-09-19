package org.simple.core;

import org.simple.util.ClassUtil;

import java.util.Set;

/**
 * 框架核心类管理器
 */
public class ClassManager {

    // 框架核心类集合
    private static final Set<Class<?>> CLASS_SET;


    // 加载框架核心类
    static {
        //TODO 读取配置文件
        String packageName = "org.simple";

        CLASS_SET = ClassUtil.scanPackage(packageName);

    }

    public static Set<Class<?>> getClasses(){
        return CLASS_SET;
    }

    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
