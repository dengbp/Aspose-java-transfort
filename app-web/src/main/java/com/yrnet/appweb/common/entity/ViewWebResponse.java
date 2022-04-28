package com.yrnet.appweb.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author dengbp
 */
public class ViewWebResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public ViewWebResponse code(int status) {
        this.put("code", status);
        return this;
    }

    public ViewWebResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public ViewWebResponse data(Object data) {
        this.put("data", data);
        return this;
    }


    public ViewWebResponse success() {
        this.code(HttpStatus.OK.value());
        return this;
    }

    public boolean isNormal() {
        if (HttpStatus.OK.value() == (Integer) this.get("code")){
            return true;
        }
        return false;
    }

    public ViewWebResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return this;
    }


    public ViewWebResponse fail(int code) {
        this.code(code);
        return this;
    }

    @Override
    public ViewWebResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
