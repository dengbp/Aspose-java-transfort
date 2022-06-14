package com.yrnet.pcweb.business.custom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.yrnet.pcweb.business.bill.dto.VipInfoResDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id,openid
     */
    private String userId;

    /**
     * 会员到期时间到期时间(yyyyMMdd)
     */
        @TableField("expireDate")
    private Long expireDate;

    /**
     * 会员等级，1 员会，...
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 会员用户邮件地址，用于发送转换后的文件地址
     */
    private String email;



    public VipInfoResDto toResponse(VipInfoResDto vipInfo){
        vipInfo.setState(state);
        vipInfo.setEmail(email);
        vipInfo.setExpireDate(expireDate.toString());
        return vipInfo;
    }
}
