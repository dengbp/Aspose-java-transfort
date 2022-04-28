package com.yrnet.appweb.business.file.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductSuggestion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 建议类型名称
     */
    private String suggestionTypeName;

    /**
     * 建议描述
     */
    private String suggestionDesc;

    /**
     * 建议类型
     */
    private String suggestionType;

    private String userId;

    private Long createTime;


}
