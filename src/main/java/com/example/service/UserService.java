/**
 * ______
 * /__	__/__  ____
 * / / / _ \/ __) _ `/
 * /_/ ()___/_/  \_, /
 * /___/
 * 2016年12月3日 上午9:33:32
 */
package com.example.service;

import com.patsnap.common.utils.UniqueString;

import com.example.aop.annotation.CreateLogAudit;
import com.example.aop.annotation.DeleteLogAudit;
import com.example.aop.annotation.UpdateLogAudit;
import com.example.bean.User;
import com.example.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @CreateLogAudit(auditClass = User.class,
            auditFields = {"name", "email"})
    public User saveUser(User user) {
        user.setId(UniqueString.uuidUniqueString());
        userDao.save(user);
        return user;
    }

    @UpdateLogAudit(auditClass = User.class,
            auditFields = {"name", "email"})
    public User updateUser(User user) {
        User userOriginal = userDao.get(user.getId());
        userOriginal.setName(user.getName());
        userOriginal.setEmail(user.getEmail());
        userDao.save(userOriginal);
        return userOriginal;
    }

    @DeleteLogAudit(auditClass = User.class)
    public void deleteUser(String userId) {
        userDao.delete(userId);
    }

    public User getUser(String userId) {
        return userDao.get(userId);
    }

    public List<User> getUserList() {
        return userDao.getAll();
    }
}
