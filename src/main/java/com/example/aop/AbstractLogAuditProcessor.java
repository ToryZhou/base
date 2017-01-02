package com.example.aop;


import com.example.bean.LogAuditDifferentItem;
import com.example.service.LogAuditService;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractLogAuditProcessor {
    LogAuditService logAuditService;

    @Autowired
    public void setLogAuditManager(LogAuditService logAuditService) {
        this.logAuditService = logAuditService;
    }

    List<LogAuditDifferentItem> generateDifferentContent(Class clazz, Object oldImage, Object newImage, String[] compareFields) {
        try {
            List<LogAuditDifferentItem> differentItems = new ArrayList<>();
            for (String field : compareFields) {
                Method method = ReflectUtils.findDeclaredMethod(clazz, "get" + WordUtils.capitalizeFully(field, '.').replace(".", ""), null);
                Object oldValue = method.invoke(oldImage);
                Object newValue = method.invoke(newImage);

                if (String.class.isInstance(oldValue) || String.class.isInstance(newValue)
                        || Boolean.class.isInstance(oldValue) || Boolean.class.isInstance(newValue)
                        || Integer.class.isInstance(oldValue) || Integer.class.isInstance(newValue)) {
                    String oldValueStr = null == oldValue ? StringUtils.EMPTY : oldValue.toString();
                    String newValueStr = null == newValue ? StringUtils.EMPTY : newValue.toString();
                    if (ObjectUtils.compare(oldValueStr, newValueStr) != 0) {
                        LogAuditDifferentItem differentItem = LogAuditDifferentItem.createItem(field, oldValueStr, newValueStr,
                                method.getReturnType().getSimpleName());
                        differentItems.add(differentItem);
                    }
                } else if ((DateTime.class.isInstance(oldValue) || DateTime.class.isInstance(newValue))
                        && DateTimeComparator.getInstance().compare(oldValue, newValue) != 0) {
                    String oldValueStr = oldValue == null ? StringUtils.EMPTY : String.valueOf(((DateTime) oldValue).getMillis());
                    String newValueStr = newValue == null ? StringUtils.EMPTY : String.valueOf(((DateTime) newValue).getMillis());
                    LogAuditDifferentItem differentItem = LogAuditDifferentItem.createItem(field, oldValueStr, newValueStr,
                            DateTime.class.getSimpleName());
                    differentItems.add(differentItem);
                }
            }

            return differentItems;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
