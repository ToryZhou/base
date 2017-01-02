package com.example.bean;

import com.patsnap.common.dynamodb.entity.DynamodbVersionEntity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Copyright with Patsnap company.
 * Author: Tory
 * Date: 1/2/17
 * Time: 9:38 PM
 */
@DynamoDBTable(tableName = "base_user")
public class User extends DynamodbVersionEntity<String> {


    private String name;

    private String email;

    @DynamoDBHashKey(attributeName = "user_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @DynamoDBAttribute(attributeName = "user_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("email", email)
                .toString();
    }
}
