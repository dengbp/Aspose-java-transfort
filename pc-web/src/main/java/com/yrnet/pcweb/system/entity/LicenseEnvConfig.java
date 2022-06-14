package com.yrnet.pcweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 环境配置表
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LicenseEnvConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
            @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 资源表名
     */
    private String tableName;

    /**
     * 环境名称
     */
    private String envName;

    /**
     * 项目id
     */
    private Long projectId;


    private Long createTime;
}
