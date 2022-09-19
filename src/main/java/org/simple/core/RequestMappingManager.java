package org.simple.core;

import java.util.Map;

public class RequestMappingManager {

    private static Map<Request,RequestMappingHandler> handlerMap;


    static {
        handlerMap = ControllerParser.parse();
    }


    public static RequestMappingHandler getHandler(String requestPath,String requestMethod){

        return handlerMap.get(new Request(requestPath,RequestMappingMethod.getByName(requestMethod)));
    }
}
