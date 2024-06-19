package com.response;

import com.dto.ErrorsDTO;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ErrorResponse {

    private ErrorsDTO errors;

    public ErrorResponse(ErrorsDTO errors){this.errors = errors;}
    //Change pluralize this maybe?

    @JsonProperty("errors")
    public ErrorsDTO getError() {
        return errors;
    }

    public void setError(ErrorsDTO errors) {
        this.errors = errors;
    }
}
