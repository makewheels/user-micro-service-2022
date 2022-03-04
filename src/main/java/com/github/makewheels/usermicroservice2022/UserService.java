package com.github.makewheels.usermicroservice2022;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService {
    public Result<Void> requestVerificationCode(@RequestParam String phone) {
        //如果redis里已经有了，直接返回
        //如果redis里没有，发验证码，放redis里，返回
        return Result.ok();
    }
}
