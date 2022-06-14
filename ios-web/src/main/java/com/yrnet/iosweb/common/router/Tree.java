package com.yrnet.iosweb.common.router;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {

    private String id;

    private String key;

    private String icon;

    private String title;

    private String value;

    private String text;

    private String permission;

    private String type;

    private Double order;

    private String path;

    private String component;

    private List<Tree<T>> children;

    private String parentId;

    private Tree parent;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    private Map<String,Object> attach;


    private String target;
    private String  url;
    private String resCode;
    private String appUrl;
    private Integer resOwner;
    private String linkUrl;
    private Integer linkType;
    private Integer defaultFlag;
    private Integer operDescId;

    /** 是否可选设置 */
    private transient boolean disabled = false;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

}
