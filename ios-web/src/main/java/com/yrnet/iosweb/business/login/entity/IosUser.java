package com.yrnet.iosweb.business.login.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yrnet.iosweb.common.utils.DateUtil;
import lombok.Data;

import java.util.Map;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/22 4:24 下午
 */
@Data
@TableName("ios_user")
public class IosUser {
    @TableId(value = "USER_ID", type = IdType.AUTO)
    private Long userId;
    /**
     * ios用户唯一标识
     */
    private String openId;

    /** Apple Developer帐户中的client_id */
    private String aud;
    /**
     * ios用户名称
     */
    private String userName;

    private String email;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号
     */
    private String phoneNumber;


    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    /** 国家编码 **/
    private String unionId;

    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改人
     */
    private String updateUserName;
    /**
     * 状态 0：普通用户，1:会员 2:禁用用户
     */
    private Integer status;

    @TableField(exist=false)
    private String session_key;



    /**
     * Description todo
     *         "sub": "001241.36b72d1f8f4041b0ae8eca24c7c8656e.0110",
     *         "nonce_supported": true,
     *         "email_verified": "true",
     *         "iss": "https://appleid.apple.com",
     *         "aud": "com.dpb.dev.pdfconvert.t",
     *         "c_hash": "PlURrMBQ5M4P4mxKtule-A",
     *         "auth_time": 1654844944,
     *         "exp": 1654931344,
     *         "iat": 1654844944,
     *         "email": "weilianzhongchuang@163.com"
     * @param result
     * @return com.yrnet.iosweb.business.login.entity.IosUser
     * @Author dengbp
     * @Date 5:24 PM 6/10/22
     **/

    public static IosUser builder(Map<String, Object> result,String baseUrl){
        IosUser user = new IosUser();
        user.setOpenId((String) result.get("sub"));
        user.setAud((String) result.get("aud"));
        user.setEmail((String) result.get("email"));
        user.setUserName(user.getEmail());
        user.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        user.setAvatarUrl(baseUrl);
        return user;
    }
}
