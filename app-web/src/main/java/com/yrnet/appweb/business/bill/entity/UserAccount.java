package com.yrnet.appweb.business.bill.entity;

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
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 0正常，1禁用
     */
    private Integer status;

    /**
     * 可以用次数
     */
    private Integer ableTimes;

    /**
     * 总次数
     */
    private Integer allTimes;


    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;


}
