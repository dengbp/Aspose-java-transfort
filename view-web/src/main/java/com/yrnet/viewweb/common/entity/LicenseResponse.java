package com.yrnet.viewweb.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author dengbp
 */
public class LicenseResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public LicenseResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public LicenseResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public LicenseResponse data(Object data) {
        this.put("data", data);
        return this;
    }


    public LicenseResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public LicenseResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public LicenseResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
