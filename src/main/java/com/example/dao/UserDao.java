package com.example.dao;

import com.example.bean.User;

import org.springframework.stereotype.Repository;

/**
 * Copyright with PatSnap company.
 * Author: toryzhou
 * Date: 10/9/16
 * Time: 10:05 AM
 */
@Repository
public class UserDao extends AbstractDynamodbDao<String, User> {
    public UserDao() {
        super(User.class);
    }
}
