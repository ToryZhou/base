package com.example.aop;

import com.patsnap.common.dynamodb.entity.DynamodbVersionEntity;

/**
 * Copyright with PatSnap company.
 * Author: KevinLi1130
 * Date: 02/11/2016
 * Time: 3:13 PM
 */
public interface LogAuditProcessor<T extends DynamodbVersionEntity<?>> {
    /**
     * for create method, process the log audit
     */
    void processCreateLogAudit(Object[] args, Object returnValue, String[] needCompareFields);

    /**
     * for update method, process the log audit
     */
    void processUpdateLogAudit(Object[] args, Object oldImage, Object newImage, String[] needCompareFields);

    /**
     * for delete method, process the log audit
     */
    void processDeleteLogAudit(Object[] args);

    /**
     * Get entity image.
     *
     * @param args  args
     * @param clazz clazz
     * @return the type of <clazz> object </clazz>
     */
    Object getImage(Object[] args, Class clazz);


    default T getEntityFromArgs(Object[] args, Class<T> clazz) {
        for (Object arg : args) {
            if (clazz.isInstance(arg)) {
                return clazz.cast(arg);
            }
        }

        throw new RuntimeException("No given type arg found!");
    }
}
