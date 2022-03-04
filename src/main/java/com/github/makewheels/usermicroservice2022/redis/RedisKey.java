package com.github.makewheels.usermicroservice2022.redis;

/**
 * @Author makewheels
 * @Time 2021.01.30 13:00:45
 */
public class RedisKey {
    private static final String ROOT = "uus";
    private static final String USER = ROOT + ":user";

    public static String loginToken(String loginToken) {
        return USER + ":loginToken:" + loginToken;
    }

    public static String userId(String userId) {
        return USER + ":userId:" + userId;
    }

}
