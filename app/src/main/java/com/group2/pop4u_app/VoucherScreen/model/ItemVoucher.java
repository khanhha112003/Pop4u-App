package com.group2.pop4u_app.VoucherScreen.model;

public class ItemVoucher {
    String voucher_id;
    String voucher_description;

    public ItemVoucher(String voucher_id, String voucher_description) {
        this.voucher_id = voucher_id;
        this.voucher_description = voucher_description;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getVoucher_description() {
        return voucher_description;
    }

    public void setVoucher_description(String voucher_description) {
        this.voucher_description = voucher_description;
    }
}
