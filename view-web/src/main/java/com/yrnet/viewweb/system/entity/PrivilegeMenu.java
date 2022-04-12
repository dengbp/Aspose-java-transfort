package com.yrnet.viewweb.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class PrivilegeMenu implements Serializable {

    @TableId(value = "PRIVILEGE_ID", type = IdType.AUTO)
    private Long privilegeId;

    private String privilegeCode;

    private String privilegeName;

    private Integer privilegeType;

    private String privilegeDesc;

    private Long parentPrivilegeId;

    private String superCode;

    private String url;

    private Integer position;

    private String menuTarget;

    private Integer isLeaf;

    private Integer levelId;

    private String imageDis;

    private String hotKey;

    private Integer privilegeState;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeCode() {
        return privilegeCode;
    }

    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode == null ? null : privilegeCode.trim();
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName == null ? null : privilegeName.trim();
    }

    public Integer getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getPrivilegeDesc() {
        return privilegeDesc;
    }

    public void setPrivilegeDesc(String privilegeDesc) {
        this.privilegeDesc = privilegeDesc == null ? null : privilegeDesc.trim();
    }

    public Long getParentPrivilegeId() {
        return parentPrivilegeId;
    }

    public void setParentPrivilegeId(Long parentPrivilegeId) {
        this.parentPrivilegeId = parentPrivilegeId;
    }

    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode == null ? null : superCode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget == null ? null : menuTarget.trim();
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getImageDis() {
        return imageDis;
    }

    public void setImageDis(String imageDis) {
        this.imageDis = imageDis == null ? null : imageDis.trim();
    }

    public String getHotKey() {
        return hotKey;
    }

    public void setHotKey(String hotKey) {
        this.hotKey = hotKey == null ? null : hotKey.trim();
    }

    public Integer getPrivilegeState() {
        return privilegeState;
    }

    public void setPrivilegeState(Integer privilegeState) {
        this.privilegeState = privilegeState;
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
        sb.append(", privilegeId=").append(privilegeId);
        sb.append(", privilegeCode=").append(privilegeCode);
        sb.append(", privilegeName=").append(privilegeName);
        sb.append(", privilegeType=").append(privilegeType);
        sb.append(", privilegeDesc=").append(privilegeDesc);
        sb.append(", parentPrivilegeId=").append(parentPrivilegeId);
        sb.append(", superCode=").append(superCode);
        sb.append(", url=").append(url);
        sb.append(", position=").append(position);
        sb.append(", menuTarget=").append(menuTarget);
        sb.append(", isLeaf=").append(isLeaf);
        sb.append(", levelId=").append(levelId);
        sb.append(", imageDis=").append(imageDis);
        sb.append(", hotKey=").append(hotKey);
        sb.append(", privilegeState=").append(privilegeState);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}
