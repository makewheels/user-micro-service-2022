package com.github.makewheels.usermicroservice2022;

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
        //如果redis里已经有了，直接返回
        //如果redis里没有，发验证码，放redis里，返回
        return Result.ok();
    }

    public void submitVerificationCode() {
        //根据requestId找

    }
}
