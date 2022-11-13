package com.artinus.community.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user-service.api.url}", configuration = UserFeignClientConfig.class)
public interface UserServiceFeignClient {
    @GetMapping("/users/{email}")
    ResponseUser getUserInfo(@PathVariable String email);
}
