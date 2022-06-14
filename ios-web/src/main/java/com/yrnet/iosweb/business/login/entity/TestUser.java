package com.yrnet.iosweb.business.login.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 5:59 下午
 */
@TableName("user")
@Data
public class TestUser {

    /** id */
    @TableId
    private Long id ;
    /** 用户id */
    private Long userid ;
    /** 性别1男2女 */
    private Integer gender ;
    /** 省份 */
    private String  province ;
    /** 城市 */
    private String city ;
    /** 身高 */
    private Double height ;
    /** 照片 */
    private String avatar ;
    /** 用户名 */
    private String username ;
    /** 工资 */
    private String salary ;
    /** 出生年份 */
    private String birthdayyear ;
    /** 工作 */
    private String profession ;
    /** 学历 */
    private String education ;
    /** 婚姻状况 */
    private String marry ;
    /** 宣言 */
    private String monolog ;
    /** 是否有获取详细信息1未获取2已获取 */
    private Integer userdetailflag ;
    /** 创建时间 */
    private Date createtime ;
}
