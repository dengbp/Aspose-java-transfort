package com.yrnet.viewweb.business.custom.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.yrnet.viewweb.business.bill.dto.VipInfoResDto;
import com.yrnet.viewweb.common.utils.DateUtil;
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



    public VipInfoResDto toResponse(VipInfoResDto vipInfo){
        vipInfo.setState(state);
        vipInfo.setExpireDate(expireDate.toString());
        return vipInfo;
    }
}
