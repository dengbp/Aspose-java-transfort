package com.yrnet.appweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class BaseStaff implements Serializable {

    @TableId(value = "STAFF_ID", type = IdType.AUTO)
    private Long staffId;

    private String staffCode;

    private String staffName;

    private Integer staffSex;

    private String staffTel;

    private String staffEmail;

    private Long belongOrgId;

    private Date entryDate;

    private String password;

    private String pwdSalt;

    private Date passwordTime;

    private String portalStyle;

    private Integer acctState;

    private Integer staffState;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode == null ? null : staffCode.trim();
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public Integer getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(Integer staffSex) {
        this.staffSex = staffSex;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel == null ? null : staffTel.trim();
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail == null ? null : staffEmail.trim();
    }

    public Long getBelongOrgId() {
        return belongOrgId;
    }

    public void setBelongOrgId(Long belongOrgId) {
        this.belongOrgId = belongOrgId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt == null ? null : pwdSalt.trim();
    }

    public Date getPasswordTime() {
        return passwordTime;
    }

    public void setPasswordTime(Date passwordTime) {
        this.passwordTime = passwordTime;
    }

    public String getPortalStyle() {
        return portalStyle;
    }

    public void setPortalStyle(String portalStyle) {
        this.portalStyle = portalStyle == null ? null : portalStyle.trim();
    }

    public Integer getAcctState() {
        return acctState;
    }

    public void setAcctState(Integer acctState) {
        this.acctState = acctState;
    }

    public Integer getStaffState() {
        return staffState;
    }

    public void setStaffState(Integer staffState) {
        this.staffState = staffState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", staffId=").append(staffId);
        sb.append(", staffCode=").append(staffCode);
        sb.append(", staffName=").append(staffName);
        sb.append(", staffSex=").append(staffSex);
        sb.append(", staffTel=").append(staffTel);
        sb.append(", staffEmail=").append(staffEmail);
        sb.append(", belongOrgId=").append(belongOrgId);
        sb.append(", entryDate=").append(entryDate);
        sb.append(", password=").append(password);
        sb.append(", pwdSalt=").append(pwdSalt);
        sb.append(", passwordTime=").append(passwordTime);
        sb.append(", portalStyle=").append(portalStyle);
        sb.append(", acctState=").append(acctState);
        sb.append(", staffState=").append(staffState);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
