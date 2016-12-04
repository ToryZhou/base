package com.example.javanew;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tory on 2016/12/4.
 */
public class ConcurrentTest {


    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("a","b");
        System.out.println(concurrentHashMap.get("a"));
    }
}
