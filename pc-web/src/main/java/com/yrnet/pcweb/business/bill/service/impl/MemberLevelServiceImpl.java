package com.yrnet.pcweb.business.bill.service.impl;//package com.yrnet.viewweb.business.bill.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.yrnet.viewweb.business.bill.entity.MemberLevel;
//import com.yrnet.viewweb.business.bill.entity.PlanService;
//import com.yrnet.viewweb.business.bill.mapper.MemberLevelMapper;
//import com.yrnet.viewweb.business.bill.service.IMemberLevelService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.yrnet.viewweb.business.bill.service.IPlanServiceService;
//import com.yrnet.viewweb.common.exception.YinXXException;
//import com.yrnet.viewweb.common.service.ISeqService;
//import com.yrnet.viewweb.common.utils.DateUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.text.ParseException;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author dengbp
// */
//@Service
//public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements IMemberLevelService {
//
//    @Resource
//    private IPlanServiceService planServiceService;
//
//    @Autowired
//    private ISeqService seqService;
//
//    @Override
//    public void upInsert(String userId, Long plansId) throws YinXXException {
//        PlanService plans = planServiceService.getById(plansId);
//        LambdaQueryWrapper<MemberLevel> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(MemberLevel::getUserId,userId);
//        List<MemberLevel> memberLevelList =  this.list(queryWrapper);
//        long current = Long.parseLong(DateUtil.current_yyyyMMddHHmmss());
//        MemberLevel memberLevel;
//        if (!memberLevelList.isEmpty()){
//            memberLevel = memberLevelList.get(0);
//            this.setVal(plans, memberLevel, current, plansId);
//            memberLevel.setUpdateTime(current);
//            this.updateById(memberLevel);
//            return;
//        }
//        memberLevel = new MemberLevel();
//        memberLevel.setId(Integer.valueOf(String.valueOf(seqService.getSeq())));
//        this.setVal(plans, memberLevel, current, plansId);
//        memberLevel.setCreateTime(current);
//        memberLevel.setUserId(userId);
//        this.save(memberLevel);
//    }
//
//    @Override
//    public Integer queryLastDays(String userId) throws YinXXException {
//        LambdaQueryWrapper<MemberLevel> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(MemberLevel::getUserId,userId).orderByDesc(MemberLevel::getCreateTime);
//        List<MemberLevel> list = this.list(queryWrapper);
//        if (list.isEmpty()){
//            return 0;
//        }
//        MemberLevel memberLevel = list.get(0);
//        try {
//            return DateUtil.differentDays(new Date(),DateUtil.stringToDate(String.valueOf(memberLevel.getExpiryDate()),DateUtil.FULL_TIME_PATTERN));
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    private void setVal(PlanService plans,MemberLevel memberLevel,long current,Long plansId){
//        memberLevel.setPlansId(plansId);
//        memberLevel.setIssueDate(current);
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MONTH, plans.getEffectiveTime());
//        memberLevel.setExpiryDate(Long.parseLong(DateUtil.getDateFormat(cal.getTime(),DateUtil.FULL_TIME_PATTERN)));
//    }
//}
