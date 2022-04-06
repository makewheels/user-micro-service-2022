package com.github.makewheels.usermicroservice2022.client;

import cn.hutool.json.JSONObject;
import com.github.makewheels.usermicroservice2022.response.Result;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ClientService {
    @Resource
    private MongoTemplate mongoTemplate;

    public Result<JSONObject> requestClientId(HttpServletRequest request) {
        Client client = new Client();
        client.setCreateTime(new Date());
        client.setIp(request.getRemoteAddr());
        client.setUserAgent(request.getHeader("User-Agent"));
        mongoTemplate.save(client);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("clientId", client.getId());
        return Result.ok(jsonObject);
    }
}
