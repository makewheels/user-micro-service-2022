package com.github.makewheels.usermicroservice2022.client;

import cn.hutool.json.JSONObject;
import com.github.makewheels.usermicroservice2022.response.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("client")
public class ClientController {
    @Resource
    private ClientService clientService;

    @CrossOrigin
    @GetMapping("requestClientId")
    public Result<JSONObject> requestClientId(HttpServletRequest request) {
        return clientService.requestClientId(request);
    }
}
