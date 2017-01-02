package com.example.dao;

import com.patsnap.common.dynamodb.DynamodbDaoImpl;
import com.patsnap.common.dynamodb.entity.DynamodbVersionEntity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.xspec.UpdateItemExpressionSpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Copyright with PatSnap company.
 * Author: KevinLi1130
 * Date: 8/24/16
 * Time: 5:59 PM
 */
public abstract class AbstractDynamodbDao<K extends Serializable, E extends DynamodbVersionEntity<K>> extends DynamodbDaoImpl<K, E> {

    public static final Integer PAGE_ZERO = 0;
    public static final Integer UNLIMITED = -1;
    /**
     * DynamoDB table name.
     */
    protected final String tableName;
    /**
     * Entity class type.
     */
    private final Class<E> clazz;
    /**
     * The key name for table
     */
    protected String keyName = "id";


    @Autowired
    protected AmazonDynamoDBClient client;

    /**
     * construct method.
     *
     * @param clazz the special entity clazz
     */
    public AbstractDynamodbDao(Class<E> clazz) {
        this.clazz = clazz;
        DynamoDBTable dynamoDBTable = AnnotationUtils.findAnnotation(this.clazz, DynamoDBTable.class);
        if (dynamoDBTable != null) {
            this.tableName = dynamoDBTable.tableName();
        } else {
            this.tableName = StringUtils.lowerCase(this.clazz.getSimpleName());
        }

        for (Field f : this.clazz.getFields()) {
            DynamoDBHashKey dynamoDBHashKey = f.getAnnotation(DynamoDBHashKey.class);
            if (dynamoDBHashKey != null) {
                this.keyName = dynamoDBHashKey.attributeName();
            }
        }
    }

    public List<E> getAll() {
        return this.scanAll(0, -1, null).getRecords();
    }

    @Override
    @Autowired
    public void setMapper(DynamoDBMapper mapper) {
        super.setMapper(mapper);
    }

    @Override
    @Autowired
    public void setDynamoDB(DynamoDB dynamoDB) {
        super.setDynamoDB(dynamoDB);
    }

    public List<E> queryWithIndex(String indexName, String expression, Map<String, AttributeValue> parameters) {
        DynamoDBQueryExpression<E> queryExpression = new DynamoDBQueryExpression<E>()
                .withIndexName(indexName)
                .withKeyConditionExpression(expression)
                .withExpressionAttributeValues(parameters).withConsistentRead(false);

        return this.mapper.query(clazz, queryExpression);
    }

    protected void updateObject(UpdateItemExpressionSpec updateItemExpressionSpec, String objectIdName, String objectId) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(objectIdName, objectId)
                .withReturnValues(ReturnValue.NONE);
        updateItemSpec.withExpressionSpec(updateItemExpressionSpec);

        this.dynamoDB.getTable(this.tableName).updateItem(updateItemSpec);
    }

    /**
     * Scan all records and do some action for each records.
     *
     * @param consumer action for each records
     */
    public void scanAllRecords(Consumer<List<E>> consumer) {
        Map<String, AttributeValue> lastKeyEvaluated = null;
        do {
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
            dynamoDBScanExpression.setLimit(500);
            dynamoDBScanExpression.setExclusiveStartKey(lastKeyEvaluated);

            ScanResultPage<E> result = this.mapper.scanPage(this.clazz, dynamoDBScanExpression);
            consumer.accept(result.getResults());
            lastKeyEvaluated = result.getLastEvaluatedKey();
        } while (lastKeyEvaluated != null);
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
