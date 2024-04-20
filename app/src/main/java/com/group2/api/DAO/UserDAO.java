package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.User;

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

    public User asUser() {
        return new User(username, email, fullname, birthdate, phone_number);
    }
}
