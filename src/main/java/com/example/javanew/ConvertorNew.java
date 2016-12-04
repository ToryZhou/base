package com.example.javanew;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by Tory on 2016/12/3.
 */
public class ConvertorNew {
    public static void main(String[] args) {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);   // 123


        Runnable runnable = String::new;

    }

}
