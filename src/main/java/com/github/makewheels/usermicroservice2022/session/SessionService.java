package com.github.makewheels.usermicroservice2022.session;

import cn.hutool.json.JSONObject;
import com.github.makewheels.usermicroservice2022.response.Result;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class SessionService {
    @Resource
    private MongoTemplate mongoTemplate;

    public Result<JSONObject> requestSessionId(HttpServletRequest request) {
        Session session = new Session();
        session.setCreateTime(new Date());
        session.setIp(request.getRemoteAddr());
        session.setUserAgent(request.getHeader("User-Agent"));
        mongoTemplate.save(session);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("sessionId", session.getId());
        return Result.ok(jsonObject);
    }
}
