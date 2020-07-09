package com.alben.destinations.controllers.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConnectedApiResponse {
    private String result;

    @JsonInclude(Include.NON_NULL)
    private ErrorResponse error;

    @Builder
    @Getter
    public static class ErrorResponse {
        private String message;
    }
}
