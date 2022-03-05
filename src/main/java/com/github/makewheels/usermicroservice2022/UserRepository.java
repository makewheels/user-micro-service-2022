package com.github.makewheels.usermicroservice2022;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class UserRepository {
    @Resource
    private MongoTemplate mongoTemplate;

    public User getByPhone(String phone) {
        Query query = Query.query(Criteria.where("phone").is(phone));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getByToken(String token) {
        Query query = Query.query(Criteria.where("token").is(token));
        return mongoTemplate.findOne(query, User.class);
    }
}
