package com.yrnet.appweb.business.bill.controller;//package com.yrnet.viewweb.business.bill.controller;
//
//
//import com.yrnet.viewweb.business.bill.dto.VipLastDaysReqDto;
//import com.yrnet.viewweb.business.bill.service.IMemberLevelService;
//import com.yrnet.viewweb.common.annotation.ControllerEndpoint;
//import com.yrnet.viewweb.common.annotation.Log;
//import com.yrnet.viewweb.common.entity.YinXXResponse;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//
///**
// * @author dengbp
// */
//@RestController
//@RequestMapping("/bill/member-level")
//public class MemberLevelController {
//
//
//    @Resource
//    private IMemberLevelService memberLevelService;
//
//
//    @PostMapping("/last-day")
//    @ResponseBody
//    @ControllerEndpoint(operation = "会员到期时间查询", exceptionMessage = "会员到期时间查询失败")
//    @Log("会员到期时间查询接口")
//    public YinXXResponse lastDay(@RequestBody @Valid VipLastDaysReqDto dto){
//        return new YinXXResponse().success().data(memberLevelService.queryLastDays(dto.getUserId()));
//    }
//
//}
