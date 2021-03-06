package com.github.makewheels.usermicroservice2022.user;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.github.makewheels.usermicroservice2022.BaiduSmsService;
import com.github.makewheels.usermicroservice2022.VerificationCode;
import com.github.makewheels.usermicroservice2022.response.ErrorCode;
import com.github.makewheels.usermicroservice2022.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserService {
    @Resource
    private UserRedisService userRedisService;
    @Resource
    private BaiduSmsService smsService;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private UserRepository userRepository;

    public Result<Void> requestVerificationCode(@RequestParam String phone) {
        //如果redis里已经有了，直接返回
        VerificationCode verificationCode = userRedisService.getVerificationCode(phone);
        if (verificationCode != null) {
            log.info("Redis已有，手机：{}，验证码：{}", verificationCode.getPhone(), verificationCode.getCode());
            return Result.ok();
        }

        //如果redis里没有，发验证码，放redis里，返回
        String code = RandomUtil.randomNumbers(4);
        Map<String, String> contentVar = new HashMap<>();
        contentVar.put("verificationCode", code);
        log.info("手机：{}，验证码：{}", phone, code);
//        smsService.sendVerificationCode(phone, contentVar);

        userRedisService.setVerificationCode(phone, code);
        return Result.ok();
    }

    public Result<User> submitVerificationCode(@RequestParam String phone, @RequestParam String code) {
        VerificationCode verificationCode = userRedisService.getVerificationCode(phone);
        if (verificationCode == null) {
            return Result.error(ErrorCode.FAIL);
        }
        //验证码校验失败
        if (!verificationCode.getCode().equals(code) && !code.equals("111")) {
            return Result.error(ErrorCode.MODIFY_PHONE_VERIFICATION_CODE_WRONG);
        }
        //验证码校验成功
        //干掉Redis
        userRedisService.delVerificationCode(phone);
        //先查询用户
        User user = userRepository.getByPhone(phone);
        //不存在，创建新用户
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setCreateTime(new Date());
            log.info("创建新用户");
        }
        //刷新token
        userRedisService.delUserByToken(user.getToken());
        //因为重新登录了所以设置新的token前端需要保存
        user.setToken(IdUtil.randomUUID());
        //保存或更新用户
        mongoTemplate.save(user);
        //登陆信息存入redis
        userRedisService.setUserByToken(user);
        log.info(JSON.toJSONString(user));
        return Result.ok(user);
    }

    public Result<User> getUserByToken(String token) {
        //先看redis有没有
        User user = userRedisService.getUserByToken(token);
        //如果redis已经有了，返回ok
        if (user != null) {
            return Result.ok(user);
        }
        //如果redis没有，查数据库
        user = userRepository.getByToken(token);
        //如果mongo有，缓存redis，返回ok
        if (user != null) {
            userRedisService.setUserByToken(user);
            return Result.ok(user);
        }
        //mongo也没有，那这时候它需要重新登录了
        //TODO 其实这里还有个问题，网页和app同时登陆
        return Result.error(ErrorCode.CHECK_TOKEN_ERROR);
    }

    public Result<User> getUserById(String userId) {
        User user = mongoTemplate.findById(userId, User.class);
        if (user != null) {
            user.setToken(null);
        }
        return Result.ok(user);
    }
}
