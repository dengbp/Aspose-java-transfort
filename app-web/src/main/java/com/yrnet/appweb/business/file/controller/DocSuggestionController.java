package com.yrnet.appweb.business.file.controller;


import com.yrnet.appweb.business.video.dto.SuggestionReqDto;
import com.yrnet.appweb.business.video.entity.ProductSuggestion;
import com.yrnet.appweb.business.video.service.IProductSuggestionService;
import com.yrnet.appweb.common.annotation.ControllerEndpoint;
import com.yrnet.appweb.common.annotation.Log;
import com.yrnet.appweb.common.constant.ProductSuggestionType;
import com.yrnet.appweb.common.entity.ViewWebResponse;
import com.yrnet.appweb.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/doc/product")
@Slf4j
@Validated
public class DocSuggestionController {

    @Resource
    private IProductSuggestionService productSuggestionService;




    @PostMapping("/suggestion")
    @ResponseBody
    @ControllerEndpoint(operation = "产品建议接口", exceptionMessage = "产品建议失败")
    @Log("文档转换功能建议接口")
    public ViewWebResponse suggestion(@RequestBody @Valid SuggestionReqDto dto){
        ProductSuggestion suggestion = new ProductSuggestion();
        suggestion.setSuggestionType(ProductSuggestionType.PROJECTCODETYPE.getCode().toString());
        suggestion.setSuggestionDesc(dto.getSuggestionDesc());
        suggestion.setUserId(dto.getUserOpenId());
        suggestion.setSuggestionTypeName(ProductSuggestionType.PROJECTCODETYPE.getDes());
        suggestion.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        productSuggestionService.save(suggestion);
        return new ViewWebResponse().success().message("提交建议成功");
    }
}
