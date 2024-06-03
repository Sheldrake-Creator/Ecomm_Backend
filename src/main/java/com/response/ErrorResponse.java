package com.response;

import com.dto.ErrorsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONPropertyName;


public class ErrorResponse {

    private ErrorsDTO errors;

    public ErrorResponse(ErrorsDTO errors){this.errors = errors;}
    //Change pluralize this maybe?

    @JsonProperty("errors")
    public ErrorsDTO getError() {
        return errors;
    }

    public void setError(ErrorsDTO error) {
        this.errors = errors;
    }
}
