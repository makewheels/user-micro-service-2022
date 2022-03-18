package com.github.makewheels.usermicroservice2022;

import com.alibaba.fastjson.JSON;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class BaiduSmsService {
    private SmsClient client;

    private SmsClient getClient() {
        if (client == null) {
            String AccessKeyID = "2878b713e97c4dd784d07fb41f5bbb5f";
            String AccessKeySecret = "b37684274a394dcbacf594f52d881b1d";
            SmsClientConfiguration config = new SmsClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(AccessKeyID, AccessKeySecret));
            config.setEndpoint("https://smsv3.bj.baidubce.com");
            client = new SmsClient(config);
        }
        return client;
    }

    /**
     * 发送验证码短信
     *
     * @param phone
     * @param contentVar
     * @return
     */
    public SendMessageV3Response sendVerificationCode(String phone, Map<String, String> contentVar) {
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(phone);
        request.setSignatureId("sms-sign-QeEHQe10478");
        request.setTemplate("sms-tmpl-HNbGLw26882");
        request.setContentVar(contentVar);
        SendMessageV3Response sendMessageV3Response = getClient().sendMessage(request);
        log.info(JSON.toJSONString(sendMessageV3Response));
        return sendMessageV3Response;
    }

}
