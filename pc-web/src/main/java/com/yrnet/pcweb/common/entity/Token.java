package com.yrnet.pcweb.common.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName Token
 * @Description TODO
 * @date 2/3/21 11:50 AM
 */
@Data
public class Token implements Serializable {

    @NotNull
    private String token;
}
