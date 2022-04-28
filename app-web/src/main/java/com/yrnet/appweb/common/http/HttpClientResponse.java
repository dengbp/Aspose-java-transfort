package com.yrnet.appweb.common.http;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName HttpResponse
 * @Description TODO
 * @date 2020-11-06 14:06
 */
@Data
public class HttpClientResponse implements Serializable {


    private int code;
    private String desc;
    private Object data;

    public HttpClientResponse() {
    }

    public HttpClientResponse(int code) {
        this.code = code;
    }

    public HttpClientResponse(String desc) {
        this.desc = desc;
    }

    public HttpClientResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
