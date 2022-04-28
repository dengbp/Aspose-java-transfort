package com.yrnet.appweb.system.Dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName TestDto
 * @Description TODO
 * @date 2019-11-20 17:42
 */
@Data
public class TestDto  implements Serializable {

    private String name;

    private Integer sex;
}
