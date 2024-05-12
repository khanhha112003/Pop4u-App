package com.group2.api.DAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.group2.model.ResponseValidate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResponseDAO {
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


