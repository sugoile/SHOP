package com.xsg.shop.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PmsProductClassify implements Serializable {
    private Long id;

    private Long parentid;

    private String name;

    private String productUnit;

    private Integer level;

    private Integer showStatu;

    private Integer navStatu;

    private Integer productCount;

    private String keyword;

    private String description;

    private String icon;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getShowStatu() {
        return showStatu;
    }

    public void setShowStatu(Integer showStatu) {
        this.showStatu = showStatu;
    }

    public Integer getNavStatu() {
        return navStatu;
    }

    public void setNavStatu(Integer navStatu) {
        this.navStatu = navStatu;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentid=").append(parentid);
        sb.append(", name=").append(name);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", level=").append(level);
        sb.append(", showStatu=").append(showStatu);
        sb.append(", navStatu=").append(navStatu);
        sb.append(", productCount=").append(productCount);
        sb.append(", keyword=").append(keyword);
        sb.append(", description=").append(description);
        sb.append(", icon=").append(icon);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}