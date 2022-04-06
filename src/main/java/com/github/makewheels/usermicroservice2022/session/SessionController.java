package com.github.makewheels.usermicroservice2022.session;

import cn.hutool.json.JSONObject;
import com.github.makewheels.usermicroservice2022.response.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("session")
public class SessionController {
    @Resource
    private SessionService sessionService;

    @CrossOrigin
    @GetMapping("requestSessionId")
    public Result<JSONObject> requestSessionId(HttpServletRequest request) {
        return sessionService.requestSessionId(request);
    }
}
