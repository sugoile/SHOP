package com.xsg.shop.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PmsProductFullReduce implements Serializable {
    private Long id;

    private Long productId;

    @ApiModelProperty(value = "满多少减免多少")
    private Integer fullReduce;

    @ApiModelProperty(value = "满多少钱")
    private Integer fullPrice;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getFullReduce() {
        return fullReduce;
    }

    public void setFullReduce(Integer fullReduce) {
        this.fullReduce = fullReduce;
    }

    public Integer getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Integer fullPrice) {
        this.fullPrice = fullPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", fullReduce=").append(fullReduce);
        sb.append(", fullPrice=").append(fullPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}