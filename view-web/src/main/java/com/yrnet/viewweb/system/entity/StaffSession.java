package com.yrnet.viewweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class StaffSession implements Serializable {

    @TableId(value = "STAFF_SESSION_ID", type = IdType.AUTO)
    private Long staffSessionId;

    private Long staffId;

    private Integer loginErrNum;

    private String username;

    private Integer acctType;

    private Integer loginState;

    private Date lastLoginTime;

    private Date lastLogoutTime;

    private static final long serialVersionUID = 1L;

    public Long getStaffSessionId() {
        return staffSessionId;
    }

    public void setStaffSessionId(Long staffSessionId) {
        this.staffSessionId = staffSessionId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Integer getLoginErrNum() {
        return loginErrNum;
    }

    public void setLoginErrNum(Integer loginErrNum) {
        this.loginErrNum = loginErrNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getAcctType() {
        return acctType;
    }

    public void setAcctType(Integer acctType) {
        this.acctType = acctType;
    }

    public Integer getLoginState() {
        return loginState;
    }

    public void setLoginState(Integer loginState) {
        this.loginState = loginState;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(Date lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", staffSessionId=").append(staffSessionId);
        sb.append(", staffId=").append(staffId);
        sb.append(", loginErrNum=").append(loginErrNum);
        sb.append(", username=").append(username);
        sb.append(", acctType=").append(acctType);
        sb.append(", loginState=").append(loginState);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLogoutTime=").append(lastLogoutTime);
        sb.append("]");
        return sb.toString();
    }
}
