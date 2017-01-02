package com.example.dao;


import com.patsnap.common.entity.Pagination;

import com.example.bean.LogAuditRecord;

import org.springframework.stereotype.Repository;

@Repository
public class LogAuditRecordDao extends AbstractDynamodbDao<String, LogAuditRecord> {
    /**
     * construct method.
     */
    public LogAuditRecordDao() {
        super(LogAuditRecord.class);
    }


    public Pagination<LogAuditRecord> queryLogAuditRecordWithEntityId(String entityId) {
        return this.indexQuery("log_audit_entity_id", entityId, PAGE_ZERO, UNLIMITED);
    }

}
