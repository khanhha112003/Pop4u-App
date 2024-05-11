package com.group2.model;

import java.io.Serializable;

public class Address implements Serializable {
    String cus_name;
    String cus_phone;
    String cus_address;
    Boolean isDefault = false;

    public Address(String cus_name, String cus_phone, String cus_address, Boolean isDefault) {
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_address = cus_address;
        this.isDefault = isDefault;
    }

    public void setDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean isDefault() {
        return isDefault;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_phone() {
        return cus_phone;
    }

    public void setCus_phone(String cus_phone) {
        this.cus_phone = cus_phone;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }
}
