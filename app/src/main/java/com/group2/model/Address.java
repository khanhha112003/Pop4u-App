package com.group2.model;

public class Address {
    String cus_name;
    String cus_phone;
    String cus_address;

    public Address(String cus_name, String cus_phone, String cus_address) {
        this.cus_name = cus_name;
        this.cus_phone = cus_phone;
        this.cus_address = cus_address;
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
