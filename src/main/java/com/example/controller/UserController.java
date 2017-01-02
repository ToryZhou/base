/**
 * ______
 * /__	__/__  ____
 * / / / _ \/ __) _ `/
 * /_/ ()___/_/  \_, /
 * /___/
 * 2016年12月3日 上午9:28:17
 */
package com.example.controller;

import com.example.bean.User;
import com.example.service.UserService;
import com.example.util.ApplicationConfiguration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok().body(userService.getUserList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PostMapping
    public ResponseEntity<?> saveOrUpdateUser(@RequestBody User user) {
        if (StringUtils.isBlank(user.getId())) {
            if (StringUtils.isBlank(user.getEmail())) {
                user.setEmail(ApplicationConfiguration.DEFAULT_EMAIL);
            }
            userService.saveUser(user);
        } else {
            userService.updateUser(user);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
