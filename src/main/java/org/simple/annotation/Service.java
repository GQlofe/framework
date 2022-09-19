package org.simple.annotation;

import java.lang.annotation.*;

/**
 * 服务类注解，此注解修饰的Bean会被IOC容器管理
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
}
