package com.artinus.community.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {
    private T data;
    private String message;

    private ResponseVO(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(data, "SUCCESS");
    }

    public static <T> ResponseVO<T> fail(String message) {
        return new ResponseVO<>(null, message);
    }
}
