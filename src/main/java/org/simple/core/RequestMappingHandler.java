package org.simple.core;

import java.lang.reflect.Method;

public class RequestMappingHandler {

    private Class<?> controllerClass;


    private Method method;


    public RequestMappingHandler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }
}
