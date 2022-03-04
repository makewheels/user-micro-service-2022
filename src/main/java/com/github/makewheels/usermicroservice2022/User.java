package com.github.makewheels.usermicroservice2022;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class User {
    @Id
    private String id;

    private String showId;
    private String phone;
    private Date createTime;

    private String token;
}
