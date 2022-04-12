package com.yrnet.viewweb.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 */
@Data
@TableName("t_test")
public class Test implements Serializable {

    private static final long serialVersionUID = -2510534697977648681L;
    private String name;
    private int age;

}
