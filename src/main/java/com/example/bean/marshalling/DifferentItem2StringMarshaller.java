package com.example.bean.marshalling;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.amazonaws.util.json.Jackson;
import com.example.bean.LogAuditDifferentItem;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DifferentItem2StringMarshaller implements DynamoDBMarshaller<List<LogAuditDifferentItem>> {
    @Override
    public String marshall(List<LogAuditDifferentItem> getterReturnResult) {
        if (getterReturnResult == null || getterReturnResult.isEmpty()) {
            return StringUtils.EMPTY;
        }

        return Jackson.toJsonString(getterReturnResult);
    }

    @Override
    public List<LogAuditDifferentItem> unmarshall(Class<List<LogAuditDifferentItem>> clazz, String obj) {
        if (StringUtils.isEmpty(obj)) {
            return new ArrayList<>();
        }

        try {
            return Jackson.getObjectMapper().readValue(obj, Jackson.getObjectMapper().getTypeFactory().constructCollectionType(List.class, LogAuditDifferentItem.class));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
