package org.simple.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Request {

    private String requestPath;

    private RequestMappingMethod method;

    public Request(String requestPath, RequestMappingMethod method) {
        this.requestPath = requestPath;
        this.method = method;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public RequestMappingMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        return  EqualsBuilder.reflectionEquals(this,o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
