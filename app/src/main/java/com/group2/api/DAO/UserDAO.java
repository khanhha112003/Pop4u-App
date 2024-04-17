package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDAO {
    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("phone_number")
    private String phone_number;

    public UserDAO(String username, String email, String fullname, String birthdate, String phone_number) {
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.birthdate = birthdate;
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
