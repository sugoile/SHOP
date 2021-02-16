package com.xsg.shop.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class PmsProductMemberReduce implements Serializable {
    private Long id;

    private Long productId;

    @ApiModelProperty(value = "plus会员减免价格")
    private Integer plusMemberReduce;

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

    public Integer getPlusMemberReduce() {
        return plusMemberReduce;
    }

    public void setPlusMemberReduce(Integer plusMemberReduce) {
        this.plusMemberReduce = plusMemberReduce;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", plusMemberReduce=").append(plusMemberReduce);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}