package com.yrnet.appweb.business.video.controller;


import com.yrnet.appweb.business.video.dto.SuggestionReqDto;
import com.yrnet.appweb.business.video.entity.ProductSuggestion;
import com.yrnet.appweb.business.video.service.IProductSuggestionService;
import com.yrnet.appweb.common.annotation.ControllerEndpoint;
import com.yrnet.appweb.common.annotation.Log;
import com.yrnet.appweb.common.entity.ViewWebResponse;
import com.yrnet.appweb.common.constant.ProductSuggestionType;
import com.yrnet.appweb.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dengbp
 */
@RestController
@RequestMapping("/video/product-suggestion")
@Slf4j
public class ProductSuggestionController {

    @Resource
    private IProductSuggestionService productSuggestionService;


    @GetMapping("/type-list")
    @ResponseBody
    @ControllerEndpoint(operation = "产品建议类型查询接口", exceptionMessage = "产品建议类型查询接口失败")
    @Log("产品建议类型查询接口")
    public ViewWebResponse typeList(){
        Map<String,String> type = new HashMap<>();
        for (ProductSuggestionType e : ProductSuggestionType.values()) {
            type.put(e.getCode().toString(),e.getDes());
        }
        return new ViewWebResponse().success().data(type);
    }


    @PostMapping("/do")
    @ResponseBody
    @ControllerEndpoint(operation = "产品建议接口", exceptionMessage = "产品建议失败")
    @Log("产品建议接口")
    public ViewWebResponse suggestion(@RequestBody @Valid SuggestionReqDto dto){
        ProductSuggestion suggestion = new ProductSuggestion();
        suggestion.setSuggestionType(dto.getSuggestionType());
        suggestion.setSuggestionDesc(dto.getSuggestionDesc());
        suggestion.setUserId(dto.getUserId());
        suggestion.setSuggestionTypeName(ProductSuggestionType.getByCode(Integer.parseInt(dto.getSuggestionType())).getDes());
        suggestion.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        productSuggestionService.save(suggestion);
        return new ViewWebResponse().success();
    }
}
