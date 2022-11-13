package com.artinus.community.componet;

import com.artinus.community.exception.PostCommonException;
import com.artinus.community.exception.ReplyCommonException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @Getter
    private static class ErrorResponse {
        private int code;
        private String message;

        private ErrorResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public static ErrorResponse fail(int code, String message) {
            return new ErrorResponse(code, message);
        }
    }

    @ExceptionHandler({PostCommonException.class})
    public ResponseEntity<Object> exceptionHandler(final PostCommonException e) {
        log.error(String.format("[PostCommonException] message=%s", e.getMessage()));
        return ResponseEntity.badRequest()
                .body(ErrorResponse.fail(400 ,e.getMessage()));
    }

    @ExceptionHandler({ReplyCommonException.class})
    public ResponseEntity<Object> exceptionHandler(final ReplyCommonException e) {
        log.error(String.format("[ReplyCommonException] message=%s", e.getMessage()));
        return ResponseEntity.badRequest()
                .body(ErrorResponse.fail(400 ,e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exceptionHandler(final Exception e) {
        log.error(String.format("[Exception] message=%s", e.getMessage()));
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.fail(500 ,e.getMessage()));
    }
}
