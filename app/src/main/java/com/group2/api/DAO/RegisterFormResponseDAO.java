package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.ResponseValidate;

public class RegisterFormResponseDAO {
    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private String message;

    public ResponseValidate asResponseValidate() {
        return new ResponseValidate(message, status);
    }

    public Integer getStatus() {
        return status;
    }
}
