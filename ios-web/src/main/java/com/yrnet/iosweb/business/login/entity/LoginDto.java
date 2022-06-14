package com.yrnet.iosweb.business.login.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/12/1 7:54 下午
 */
@Data
public class LoginDto {

    @NotNull
    private String encryptedData;
}
