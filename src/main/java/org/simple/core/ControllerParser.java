package org.simple.core;

import org.simple.annotation.Controller;
import org.simple.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * COntroller注解解析器
 */
public class ControllerParser {


    public static Map<Request,RequestMappingHandler> parse(){
        Map<Request,RequestMappingHandler> handlerMap = new HashMap<>();

        Map<Class<?>, Object> beanMap = BeanManeger.getBeanMap();

        beanMap.forEach((clazz,bean)->{
            if (clazz.isAnnotationPresent(Controller.class)){

                String requestPath = "";
                // 获取controller类上的reuqestMapping
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                if (requestMapping != null){
                    requestPath = requestPath + requestMapping.value();
                }

                // 获取方法上的requestMapping
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)){
                        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                        String methodPath = requestPath + methodMapping.value();

                        Request request = new Request(methodPath,methodMapping.method());
                        RequestMappingHandler handler = new RequestMappingHandler(clazz,method);
                        handlerMap.put(request,handler);
                    }
                }
            }
        });
        return handlerMap;
    }

}
