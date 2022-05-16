package com.yrnet.appweb.business.custom.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dengbp
 * @ClassName UserEmailDto
 * @Description TODO
 * @date 5/12/22 2:12 PM
 */

@Data
public class UserEmailDto {

    @NotNull
    private String userOpenId;
    @NotNull
    private String email;
}
