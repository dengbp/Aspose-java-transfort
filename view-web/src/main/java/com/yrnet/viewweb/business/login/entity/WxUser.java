package com.yrnet.viewweb.business.login.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/22 4:24 下午
 */
@Data
@TableName("wx_user")
public class WxUser {
    @TableId
    private Long wxUserId;
    /**
     * 微信用户唯一标识
     */
    private String wxOpenId;
    /**
     * 微信号
     */
    private String wxUserName;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 权限ID (0:异常 1:采购 2:销售 3:财务 4:admin)
     */
    private Integer authorityId;

    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;

    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改人
     */
    private String updateUserName;
    /**
     * 状态 0：普通用户，1:会员 2:禁用用户
     */
    private Integer status;



    public void getWxUser(JSONObject userInfoJson){
        this.wxOpenId = userInfoJson.getString("openId");
        this.wxUserName= userInfoJson.getString("nickName");
        this.city=userInfoJson.getString("city");
        this.gender = userInfoJson.getInteger("gender");
        this.province=userInfoJson.getString("province");
        this. country= userInfoJson.getString("country");
        this.avatarUrl=userInfoJson.getString("avatarUrl");
        if (userInfoJson.getString("unionId")!=null) {
            this.unionId= userInfoJson.getString("unionId");
        }
    }
}
