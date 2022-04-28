package com.yrnet.appweb.business.login.entity;

import lombok.Data;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/12/1 7:54 下午
 */
@Data
public class LoginDto {
    private String encryptedData;
    private String iv;
    private String code;
    private String sessionKey;
    private String wxOpenId;
}
