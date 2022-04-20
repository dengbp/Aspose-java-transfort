package com.yrnet.viewweb.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.viewweb.common.annotation.Log;
import com.yrnet.viewweb.common.controller.BaseController;
import com.yrnet.viewweb.common.entity.LicenseResponse;
import com.yrnet.viewweb.common.constant.ProductSuggestionType;
import com.yrnet.viewweb.common.utils.DateUtil;
import com.yrnet.viewweb.common.utils.LicenseUtil;
import com.yrnet.viewweb.system.Dto.SystemParamsDto;
import com.yrnet.viewweb.system.entity.SystemParams;
import com.yrnet.viewweb.system.service.SystemParamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * Description todo
 * @Author dengbp
 * @Date 17:48 2019-12-04
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/system-param")
public class SystemParamsController extends BaseController {
    @Resource
    private SystemParamsService sysService;

    /**
     * 系统参数列表
     */
    @Log("系统参数列表查询")
    @GetMapping("/list")
    @ResponseBody
    public LicenseResponse getListSys(SystemParamsDto request) {
        if (request.getPageNum() == null){
            return new LicenseResponse().data(this.sysService.getByParentId(request)).success();
        }
        return new LicenseResponse().data(this.sysService.getList(request)).success();
    }


    /**
     * 系统参数录入
     */
    @Log("系统参数录入")
    @PostMapping("/add")
    @ResponseBody
    public LicenseResponse addSys(@Valid SystemParamsDto request) {
        SystemParams systemParams = new SystemParams();
        BeanUtils.copyProperties(request, systemParams);
        systemParams.setCreateBy(LicenseUtil.getCurrentUser().getUsername());
        systemParams.setCreateTime(new Long(DateUtil.getDateFormat(new Date(), DateUtil.FULL_TIME_PATTERN)));
        if(this.sysService.create(systemParams)){
            return new LicenseResponse().success();
        }else {
            return new LicenseResponse().fail();
        }
    }

    /**
     * 系统参数更新
     */
    @PostMapping("/update")
    public LicenseResponse updateSys(@Valid SystemParamsDto request) {
        if (request.getId()==null){
            return new LicenseResponse().fail().message("id 不能为空");
        }
        SystemParams sysParams = new SystemParams();
        BeanUtils.copyProperties(request, sysParams);
        request.setUpdateBy(LicenseUtil.getCurrentUser().getUsername());
        request.setUpdateTime(new Long(DateUtil.getDateFormat(new Date(), DateUtil.FULL_TIME_PATTERN)));
        if(this.sysService.update(sysParams)){
            return new LicenseResponse().success();
        }else{
            return new LicenseResponse().fail();
        }
    }

    /**
     * 系统参数删除
     */
    @GetMapping("/delete")
    public LicenseResponse delSys(SystemParamsDto request) {
        if (request.getId() == null) {
            return new LicenseResponse().fail().message("id不能为空");
        }
        this.sysService.delete(request.getId());
        return new LicenseResponse().success();
    }

    @GetMapping("/query-by-type")
    public LicenseResponse queryByType(SystemParamsDto request) {
        if (request.getParamType() == null) {
            return new LicenseResponse().fail().message("编码类型(paramType)不能为空");
        }
        LambdaQueryWrapper<SystemParams> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemParams::getParamType,request.getParamType());
        return new LicenseResponse().success().data(this.sysService.list(wrapper));
    }


    @GetMapping("/type-query")
    public LicenseResponse typeQuery() {
        List<Map> list = new ArrayList<>();
        for(ProductSuggestionType sysParamType : ProductSuggestionType.values()){
            Map<String,String> stringMap = new HashMap<>();
            stringMap.put("code",sysParamType.getCode().toString());
            stringMap.put("des",sysParamType.getDes());
            list.add(stringMap);
        }
        return new LicenseResponse().success().data(list);
    }


}
