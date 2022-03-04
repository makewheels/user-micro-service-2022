package com.github.makewheels.usermicroservice2022;

import lombok.Data;

@Data
public class VerificationCode {
    private String phone;
    private String code;
}
