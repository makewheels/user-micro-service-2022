package com.github.makewheels.usermicroservice2022.redis;

import com.alibaba.fastjson.JSON;
import com.github.makewheels.usermicroservice2022.VerificationCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRedisService {
    @Resource
    private RedisService redisService;

    public VerificationCode getVerificationCode(String phone) {
        String json = (String) redisService.get(RedisKey.verificationCode(phone));
        return JSON.parseObject(json, VerificationCode.class);
    }

    public VerificationCode setVerificationCode(String phone, String code) {
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setPhone(phone);
        verificationCode.setCode(code);
        redisService.set(RedisKey.verificationCode(phone),
                JSON.toJSONString(verificationCode), RedisTime.TEN_MINUTES);
        return verificationCode;
    }

    public void delVerificationCode(String phone) {
        redisService.del(RedisKey.verificationCode(phone));
    }

}
