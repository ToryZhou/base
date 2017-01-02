package com.example.service;

import com.patsnap.common.entity.Pagination;
import com.patsnap.common.utils.UniqueString;

import com.example.bean.LogAuditRecord;
import com.example.dao.LogAuditRecordDao;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogAuditService {

    private LogAuditRecordDao logAuditRecordDao;

    public LogAuditService(LogAuditRecordDao logAuditRecordDao) {
        this.logAuditRecordDao = logAuditRecordDao;
    }

    /**
     * Save log audit record to dynamoDB
     *
     * @param logAuditRecord log audit record.
     */
    public void saveLogAudit(LogAuditRecord logAuditRecord) {
        logAuditRecord.setId(UniqueString.uuidUniqueString());
        this.logAuditRecordDao.save(logAuditRecord);
    }

    public List<LogAuditRecord> getLogAuditRecords() {
        return logAuditRecordDao.getAll();
    }

    public List<LogAuditRecord> queryLogAuditRecords(String entityId) {
        Pagination<LogAuditRecord> logAuditRecordPagination = logAuditRecordDao.queryLogAuditRecordWithEntityId(entityId);
        return logAuditRecordPagination.getRecords();
    }
}
