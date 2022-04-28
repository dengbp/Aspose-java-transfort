package com.yrnet.appweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class BaseStaffOrgRel implements Serializable {
    @TableId(value = "REL_ID", type = IdType.AUTO)
    private Long relId;

    private Long staffId;

    private Long orgId;

    private Integer relState;

    private static final long serialVersionUID = 1L;

    public Long getRelId() {
        return relId;
    }

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getRelState() {
        return relState;
    }

    public void setRelState(Integer relState) {
        this.relState = relState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", relId=").append(relId);
        sb.append(", staffId=").append(staffId);
        sb.append(", orgId=").append(orgId);
        sb.append(", relState=").append(relState);
        sb.append("]");
        return sb.toString();
    }
}
