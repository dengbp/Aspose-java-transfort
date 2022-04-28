package com.yrnet.baseadmin.common.http;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author dengbp
 */
public class BaseAdminResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public BaseAdminResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public BaseAdminResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public BaseAdminResponse data(Object data) {
        this.put("data", data);
        return this;
    }



    public BaseAdminResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public BaseAdminResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    public boolean isNormal() {
        if (HttpStatus.OK.value() == (Integer) this.get("code")){
            return true;
        }
        return false;
    }

    @Override
    public BaseAdminResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
