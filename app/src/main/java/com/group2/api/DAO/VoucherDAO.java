package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.ResponseValidate;
import com.group2.model.Voucher;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherDAO {
    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

    @JsonProperty("discount_amount")
    private Float discountAmount;

    public Voucher asVoucher() {
        Integer discountAmount = Math.round(this.discountAmount);
        return new Voucher(code, description, discountAmount);
    }
}
