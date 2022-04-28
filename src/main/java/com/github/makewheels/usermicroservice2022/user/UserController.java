package com.github.makewheels.usermicroservice2022.user;

import com.github.makewheels.usermicroservice2022.response.Result;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("getUserByToken")
    @CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET})
    public Result<User> getUserByToken(@RequestParam String token) {
        return userService.getUserByToken(token);
    }

    @GetMapping("getUserById")
    public Result<User> getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }

}
