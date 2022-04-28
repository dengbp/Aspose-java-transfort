package com.yrnet.appweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 服务类型定义表，作为基础数据
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LicenseServiceDefine implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务类型id
     */
            @TableId(value = "service_id", type = IdType.AUTO)
    private Integer serviceId;

    /**
     * 服务类型编码
     */
    private String serviceCode;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 描述
     */
    private String remark;

    /**
     * 状态 1 有效，0 失效
     */
    private Integer status;


}
