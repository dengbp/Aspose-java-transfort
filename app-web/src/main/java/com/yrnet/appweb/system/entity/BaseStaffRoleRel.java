package com.yrnet.appweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class BaseStaffRoleRel implements Serializable {

    @TableId(value = "REL_ID", type = IdType.AUTO)
    private Long relId;

    private Long staffId;

    private Long roleId;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
        sb.append(", roleId=").append(roleId);
        sb.append(", relState=").append(relState);
        sb.append("]");
        return sb.toString();
    }
}
