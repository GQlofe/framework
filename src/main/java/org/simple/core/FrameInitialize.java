package org.simple.core;

import org.simple.util.ClassUtil;

/**
 * 框架初始化
 */
public class FrameInitialize {


    /**
     * 初始化核心类
     */
    public static void init() {

        // 加载类时，会执行类中的静态代码块
        Class<?>[] initialClasses = {
                ClassManager.class,
                BeanManeger.class,
                RequestMappingManager.class
        };
        for (Class<?> clazz : initialClasses) {
            ClassUtil.loadClass(clazz.getName(),true);
        }

    }
}
