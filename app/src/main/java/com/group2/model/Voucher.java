package com.group2.model;

import java.io.Serializable;

public class Voucher implements Serializable {
    private String code;
    private String description;
    private Integer discountAmount;

    public Voucher(String code, String description, Integer discountAmount) {
        this.code = code;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }
}
