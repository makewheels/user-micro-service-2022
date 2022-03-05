package com.github.makewheels.usermicroservice2022;

import com.github.makewheels.usermicroservice2022.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("requestVerificationCode")
    public Result<Void> requestVerificationCode(@RequestParam String phone) {
        return userService.requestVerificationCode(phone);
    }

    @GetMapping("submitVerificationCode")
    public Result<User> submitVerificationCode(@RequestParam String phone, @RequestParam String code) {
        return userService.submitVerificationCode(phone, code);
    }

    @GetMapping("authToken")
    public Result<Void> authToken(@RequestParam String token) {
        return userService.authToken(token);
    }

    @GetMapping("getUserById")
    public Result<User> getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }
}
