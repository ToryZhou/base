package com.example.controller;


import com.example.bean.LogAuditRecord;
import com.example.service.LogAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1/logauditrecords")
@RestController
public class LogAuditRecordController {
    private LogAuditService logAuditService;

    @Autowired
    public LogAuditRecordController(LogAuditService logAuditService) {
        this.logAuditService = logAuditService;
    }

    @GetMapping
    public ResponseEntity<List<LogAuditRecord>> getLogAuditRecords() {
        return ResponseEntity.ok().body(logAuditService.getLogAuditRecords());
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<List<LogAuditRecord>> queryLogAuditRecords(@PathVariable String entityId) {
        return ResponseEntity.ok().body(logAuditService.queryLogAuditRecords(entityId));
    }
}
