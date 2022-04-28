package com.yrnet.sso.server.core.result;

import java.io.Serializable;

/**
 * common return
 *
 * @author xuxueli 2015-12-4 16:32:31
 * @param <T>
 */
public class ReturnT<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	public static final String SUCCESS_CODE = "0";
	public static final String FAIL_CODE = "-1";
	public static final ReturnT<String> SUCCESS = new ReturnT<String>(null);
	public static final ReturnT<String> FAIL = new ReturnT<String>(FAIL_CODE, null);

	private String code;
	private String msg;
	private T data;

	public ReturnT(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ReturnT(T data) {
		this.code = SUCCESS_CODE;
		this.msg = "成功";
		this.data = data;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
