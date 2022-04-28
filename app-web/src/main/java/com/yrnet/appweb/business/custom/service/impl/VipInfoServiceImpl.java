package com.yrnet.appweb.business.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.appweb.business.bill.dto.VipInfoResDto;
import com.yrnet.appweb.business.bill.entity.PlanService;
import com.yrnet.appweb.business.bill.service.IPlanServiceService;
import com.yrnet.appweb.business.custom.entity.VipInfo;
import com.yrnet.appweb.business.custom.mapper.VipInfoMapper;
import com.yrnet.appweb.business.custom.service.IVipInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.appweb.common.exception.DocumentException;
import com.yrnet.appweb.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class VipInfoServiceImpl extends ServiceImpl<VipInfoMapper, VipInfo> implements IVipInfoService {

    @Resource
    private IPlanServiceService planServiceService;

    @Override
    public VipInfoResDto getVipInf(String openId) throws DocumentException {
        VipInfo vip = this.getByOpenId(openId);
        VipInfoResDto res = VipInfoResDto.builder().state(0).expireDate(DateUtil.current_yyyyMMdd()).build();
        return vip == null?null:vip.toResponse(res);
    }

    private VipInfo getByOpenId(String openId){
        List<VipInfo> list = list(new LambdaQueryWrapper<VipInfo>().eq(VipInfo::getUserId,openId));
        return list.isEmpty()?null:list.get(0);
    }

    @Override
    public void upInsert(String userId,Integer producerId) throws DocumentException {
        VipInfo vip = this.getByOpenId(userId);
        vip.setState(1);
        PlanService plan = planServiceService.getById(producerId);
        if (vip != null){
            vip.setExpireDate(DateUtil.getAfterMonth(vip.getExpireDate(),plan.getEffectiveTime()));
            updateById(vip);
            return;
        }
        vip = new VipInfo();
        vip.setUserId(userId);
        vip.setExpireDate(DateUtil.getAfterMonth(Long.parseLong(DateUtil.current_yyyyMMdd()),plan.getEffectiveTime()));
        vip.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        save(vip);
    }
}
