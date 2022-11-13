package com.artinus.community.infra;

import com.artinus.community.exception.RestApiClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserFeignError implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new RestApiClientException("Not found user");
            case 500:
                return new RestApiClientException("Server error");
        }
        return null;
    }
}
