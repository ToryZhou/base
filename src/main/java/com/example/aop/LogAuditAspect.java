package com.example.aop;


import com.example.aop.annotation.CreateLogAudit;
import com.example.aop.annotation.DeleteLogAudit;
import com.example.aop.annotation.UpdateLogAudit;
import com.example.bean.User;
import com.example.util.SpringContextUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAuditAspect {

    @AfterReturning(value = " execution(* com.example.service.*.*(..)) && @annotation(com.example.aop.annotation.CreateLogAudit))", returning = "returnValue")
    public void processCreateMethod(JoinPoint jp, Object returnValue) throws Throwable {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        CreateLogAudit createLogAudit = method.getDeclaredAnnotation(CreateLogAudit.class);
        LogAuditProcessor logAuditProcessor = getLogAuditProcessor(createLogAudit.auditClass());
        logAuditProcessor.processCreateLogAudit(jp.getArgs(), returnValue, createLogAudit.auditFields());
    }

    @AfterReturning("execution(* com.example.service.*.*(..)) && @annotation(com.example.aop.annotation.DeleteLogAudit))")
    public void processDeleteMethod(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        DeleteLogAudit deleteLogAudit = method.getDeclaredAnnotation(DeleteLogAudit.class);
        LogAuditProcessor logAuditProcessor = getLogAuditProcessor(deleteLogAudit.auditClass());
        logAuditProcessor.processDeleteLogAudit(jp.getArgs());
    }

    @Around("execution(* com.example.service.*.*(..)) && @annotation(com.example.aop.annotation.UpdateLogAudit))")
    public Object processUpdateMethod(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        UpdateLogAudit updateLogAudit = method.getDeclaredAnnotation(UpdateLogAudit.class);

        LogAuditProcessor logAuditProcessor = getLogAuditProcessor(updateLogAudit.auditClass());
        Object oldImage = logAuditProcessor.getImage(pjp.getArgs(), updateLogAudit.auditClass());
        /*Process the original method calling*/
        Object result = pjp.proceed();
        try {
            if (null == oldImage) {
                return result;
            }
            Object newImage = logAuditProcessor.getImage(pjp.getArgs(), updateLogAudit.auditClass());
            logAuditProcessor.processUpdateLogAudit(pjp.getArgs(), oldImage, newImage, updateLogAudit.auditFields());
        } catch (Exception ignored) {
        }

        return result;
    }


    private LogAuditProcessor getLogAuditProcessor(Class clazz) {
        if (clazz.getSimpleName().equals(User.class.getSimpleName())) {
            return SpringContextUtils.getBeanByClass(UserLogAuditProcessor.class);
        }
        throw new RuntimeException("No log audit process found exception");
    }

}

