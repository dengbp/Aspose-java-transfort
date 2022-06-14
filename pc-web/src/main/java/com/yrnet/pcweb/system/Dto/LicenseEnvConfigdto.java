package com.yrnet.pcweb.system.Dto;

import com.yrnet.pcweb.common.entity.QueryRequestPage;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LicenseEnvConfigdto  extends QueryRequestPage {
    private Long id;

    /**
     * 资源表名
     */
    @NotNull(message = "tableName 不能为空")
    private String tableName;

    /**
     * 环境名称
     */
    @NotNull(message = "envName 不能为空")
    private String envName;

    /**
     * 项目id
     */
    @NotNull(message = "projectId 不能为空")
    private Long projectId;

    private Long createTime;
}
