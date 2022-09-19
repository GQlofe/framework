package org.simple.core;

public enum RequestMappingMethod {
    GET("GET"),
    POST("POST");

    private String name;

    RequestMappingMethod(String name) {
        this.name = name;
    }

    public static RequestMappingMethod getByName(String method){
        return RequestMappingMethod.valueOf(method.toUpperCase());
    }

}
