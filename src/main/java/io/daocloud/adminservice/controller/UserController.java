package io.daocloud.adminservice.controller;

import com.google.common.util.concurrent.RateLimiter;
import io.daocloud.adminservice.dto.UserDto;
import io.daocloud.adminservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * Author: Garroshh
 * date: 2020/7/9 8:42 下午
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public Object add(@RequestBody @Valid UserDto userDto){
        return userService.add(userDto);
    }

    private final RateLimiter limiter = RateLimiter.create(100.0);

    @GetMapping("/hello")
    public HttpStatus Test(){
        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            return HttpStatus.TOO_MANY_REQUESTS;
        }
        return HttpStatus.ACCEPTED;
    }
}
