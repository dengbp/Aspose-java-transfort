package com.yrnet.viewweb.common.enums;

/**
 * @author dengbp
 * @ClassName SysParamType
 * @Description TODO
 * @date 2019-12-14 11:06
 */
public enum ProductSuggestionType {

    PROJECTCODETYPE(1000,"功能建议"),
    PLANSLEVEL(1001,"内容建议"),
    LICENSEVERSION(1002,"BUG反馈"),
    PLANSFEATURE(1003,"界面建议");



    ProductSuggestionType(Integer code, String des) {
        this.code = code;
        this.des = des;
    }

    private final Integer code;
    private final String des;

    public static ProductSuggestionType getByCode(Integer code){
        if(code == null){
            return null;
        }
        for(ProductSuggestionType e : ProductSuggestionType.values()){
            if(e.getCode().equals(code)){
                return e;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
