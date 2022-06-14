package com.yrnet.iosweb.business.bill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class TSwitch implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 唯一静态识别标识
     */
    private String paramKey;

    /**
     * 可配置内容1
     */
    private String param1;

    /**
     * 可配置内容2
     */
    private String param2;

    /**
     * 可配置内容3
     */
    private String param3;
}
