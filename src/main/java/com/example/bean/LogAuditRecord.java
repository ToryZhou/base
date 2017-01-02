package com.example.bean;

import com.patsnap.common.dynamodb.entity.DynamodbVersionEntity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.bean.marshalling.DifferentItem2StringMarshaller;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@DynamoDBTable(tableName = "log_audit")
public class LogAuditRecord extends DynamodbVersionEntity<String> {
    private static final long serialVersionUID = 5166530168764988749L;
    private String logAuditEntityName;
    private String logAuditEntityId;
    private String logAuditEntityAction;
    private List<LogAuditDifferentItem> logAuditContent;

    @DynamoDBHashKey(attributeName = "log_audit_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    @DynamoDBIndexHashKey(attributeName = "log_audit_entity_id")
    @DynamoDBAttribute(attributeName = "log_audit_entity_id")
    public String getLogAuditEntityId() {
        return logAuditEntityId;
    }

    public void setLogAuditEntityId(String logAuditEntityId) {
        this.logAuditEntityId = logAuditEntityId;
    }

    @DynamoDBAttribute(attributeName = "log_audit_entity_name")
    public String getLogAuditEntityName() {
        return logAuditEntityName;
    }

    public void setLogAuditEntityName(String logAuditEntityName) {
        this.logAuditEntityName = logAuditEntityName;
    }

    @DynamoDBAttribute(attributeName = "log_audit_entity_action")
    public String getLogAuditEntityAction() {
        return logAuditEntityAction;
    }

    public void setLogAuditEntityAction(String logAuditEntityAction) {
        this.logAuditEntityAction = logAuditEntityAction;
    }

    @DynamoDBMarshalling(marshallerClass = DifferentItem2StringMarshaller.class)
    @DynamoDBAttribute(attributeName = "log_audit_content")
    public List<LogAuditDifferentItem> getLogAuditContent() {
        return logAuditContent;
    }

    public void setLogAuditContent(List<LogAuditDifferentItem> logAuditContent) {
        this.logAuditContent = logAuditContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("logAuditEntityName", logAuditEntityName)
                .append("logAuditEntityId", logAuditEntityId)
                .append("logAuditEntityAction", logAuditEntityAction)
                .append("logAuditContent", logAuditContent)
                .toString();
    }
}

