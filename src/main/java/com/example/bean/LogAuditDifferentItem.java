package com.example.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class LogAuditDifferentItem implements Serializable {
    private static final long serialVersionUID = 5035898579473322174L;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String type;

    public static LogAuditDifferentItem createItem(String fieldName, String oldValue, String newValue, String type) {
        LogAuditDifferentItem differentItem = new LogAuditDifferentItem();
        differentItem.setFieldName(fieldName);
        differentItem.setOldValue(oldValue);
        differentItem.setNewValue(newValue);
        differentItem.setType(type);
        return differentItem;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fieldName", fieldName)
                .append("oldValue", oldValue)
                .append("newValue", newValue)
                .append("type", type)
                .toString();
    }
}
