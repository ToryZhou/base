package com.example.aop;

import com.example.bean.LogAuditDifferentItem;
import com.example.bean.LogAuditRecord;
import com.example.bean.User;
import com.example.enumerate.StaticConstant;
import com.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Copyright with PatSnap company.
 * Author: Tory
 * Date: 11/15/16
 * Time: 1:34 PM
 */
@Component
public class UserLogAuditProcessor extends AbstractLogAuditProcessor implements LogAuditProcessor<User> {
    private UserService userService;

    @Autowired
    public UserLogAuditProcessor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void processCreateLogAudit(Object[] args, Object returnValue, String[] needCompareFields) {
        LogAuditRecord logAuditRecord = new LogAuditRecord();
        User user = (User) returnValue;
        logAuditRecord.setLogAuditEntityName(User.class.getSimpleName());
        logAuditRecord.setLogAuditEntityAction(StaticConstant.CREATE);
        logAuditRecord.setLogAuditEntityId(user.getId());
        List<LogAuditDifferentItem> logAuditContent = generateDifferentContent(User.class, new User(), userService.getUser(user.getId()), needCompareFields);
        logAuditRecord.setLogAuditContent(logAuditContent);
        logAuditService.saveLogAudit(logAuditRecord);
    }

    @Override
    public void processUpdateLogAudit(Object[] args, Object oldImage, Object newImage, String[] needCompareFields) {
        List<LogAuditDifferentItem> differentContent = generateDifferentContent(User.class, oldImage, newImage, needCompareFields);
        if (differentContent.isEmpty()) {
            return;
        }
        LogAuditRecord logAuditRecord = new LogAuditRecord();
        logAuditRecord.setLogAuditEntityId(getEntityFromArgs(args, User.class).getId());
        logAuditRecord.setLogAuditEntityName(User.class.getSimpleName());
        logAuditRecord.setLogAuditEntityAction(StaticConstant.UPDATE);
        logAuditRecord.setLogAuditContent(differentContent);
        logAuditService.saveLogAudit(logAuditRecord);
    }

    @Override
    public void processDeleteLogAudit(Object[] args) {
        LogAuditRecord logAuditRecord = new LogAuditRecord();
        String entityId = (String) args[0];
        if (StringUtils.isEmpty(entityId)) {
            return;
        }
        logAuditRecord.setLogAuditEntityId(entityId);
        logAuditRecord.setLogAuditEntityName(User.class.getSimpleName());
        logAuditRecord.setLogAuditEntityAction(StaticConstant.DELETE);
        logAuditService.saveLogAudit(logAuditRecord);

    }

    @Override
    public Object getImage(Object[] args, Class clazz) {
        for (Object arg : args) {
            if (clazz.isInstance(arg) && clazz.getName().equals(User.class.getName())) {
                return userService.getUser(((User) arg).getId());
            }
        }
        return null;
    }
}