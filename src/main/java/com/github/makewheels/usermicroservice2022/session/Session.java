package com.github.makewheels.usermicroservice2022.session;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Session {
    @Id
    private String id;

    @Indexed
    private Date createTime;

    private String userAgent;
    private String ip;

}
