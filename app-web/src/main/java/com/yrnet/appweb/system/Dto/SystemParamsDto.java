package com.yrnet.appweb.system.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SystemParamsDto implements Serializable {
    private Long id;
    private Long parentId;
    @NotNull(message = "paramType 不能为空")
    private Integer paramType;
    @NotNull(message = "paramCode 不能为空")
    private String paramCode;
    @NotNull(message = "paramName 不能为空")
    private String paramName;
    @NotNull(message = "paramValue 不能为空")
    private String paramValue;

    private String remarks;

    private String createBy;

    private Long createTime;

    private String updateBy;

    private Long updateTime;

    private Integer pageNum;

    private Integer pageSize;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", paramType=").append(paramType);
        sb.append(", paramCode=").append(paramCode);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", remarks=").append(remarks);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
