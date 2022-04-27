package com.yrnet.viewweb.business.custom.service;

import com.yrnet.viewweb.ViewWebApplication;
import com.yrnet.viewweb.business.bill.dto.VipInfoResDto;
import com.yrnet.viewweb.business.custom.entity.VipInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IVipInfoService extends IService<VipInfo> {

    /**
     * Description 取用户会员信息
     * @param 	openId
     * @return com.yrnet.viewweb.business.bill.dto.VipInfoResDto
     * @throws DocumentException
     * @Author dengbp
     * @Date 1:52 PM 4/27/22
     **/

    VipInfoResDto getVipInf(String openId)throws DocumentException;

    /**
     * Description 更新会员信息表
     * @param userId 用户id
     * @param producerId 支付商品id
     * @return void
     * @throws DocumentException
     * @Author dengbp
     * @Date 4:05 PM 6/20/21
     **/


    void upInsert(String userId,Integer producerId)throws DocumentException;

}
