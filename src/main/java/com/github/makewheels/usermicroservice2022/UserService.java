package com.github.makewheels.usermicroservice2022;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.makewheels.usermicroservice2022.redis.RedisService;
import com.github.makewheels.usermicroservice2022.redis.UserRedisService;
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
        if (!verificationCode.getCode().equals(code)) {
            return Result.error(ErrorCode.MODIFY_PHONE_VERIFICATION_CODE_WRONG);
        }
        //验证码校验成功
        //干掉Redis
        userRedisService.delVerificationCode(phone);
        //先查询用户
        User user = userRepository.getUserByPhone(phone);
        //不存在，创建新用户
        if (user == null) {
            user = new User();
            user.setShowId(IdUtil.getSnowflake().nextIdStr());
            user.setPhone(phone);
            user.setCreateTime(new Date());
        }
        //刷新token，保存或更新用户
        user.setToken(IdUtil.randomUUID());
        mongoTemplate.save(user);
        return Result.ok(user);
    }
}
