package com.yrnet.pcweb.business.login.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 5:59 下午
 */
@Data
@TableName("userDetail")
public class TestUserDetail {



    @TableId
    private Long id ;
    /** 用户id */
    private Long userid ;
    /** 现居地 */
    private String currentaddr ;
    /** 籍贯 */
    private String  birthaddr ;
    /** 星座 */
    private String astro ;
    /** 血型 */
    private String bloodtype ;
    /** 爱好，多个用引文;分割 */
    private String hobby ;
    /** 对方宗教限制 */
    private String v1 ;
    /** 是否介意对方有子女 */
    private String v2 ;
    /** 计划结婚时间 */
    private String v3 ;
    /** 是否介意对方抽烟 */
    private String v4 ;
    /** 是否介意对方喝酒 */
    private String v5 ;
    /** 对方年龄 */
    private String v6 ;
    /** 对方婚姻状况 */
    private String v7 ;
    /** 对方学历 */
    private String v8 ;
    /** 对方职业 */
    private String v9 ;
    /** 对方月薪 */
    private String v10 ;
    /** 对方籍贯 */
    private String v11 ;
    /** 对方现居地 */
    private String v12 ;
    /** 创建时间 */
    private Date createtime ;
}
