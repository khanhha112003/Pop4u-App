package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "status"})
public class LoginResponseDAO {

    @JsonProperty(value = "role",  required = false)
    private String role;
    @JsonProperty(value = "access_token", required = false)
    private String access_token;

    public String getToken() {
        return access_token;
    }

    public String getRole() {
        return role;
    }
}
