package com.group2.model;

public class ItemVoucher {
    private String voucher_id;
    private String voucher_description;
    private boolean isSelected;


    public ItemVoucher(String voucher_id, String voucher_description) {
        this.voucher_id = voucher_id;
        this.voucher_description = voucher_description;
        this.isSelected = false; // By default, not selected
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
