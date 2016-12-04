package com.example.javanew;

import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Created by Tory on 2016/12/4.
 */
public class Lambda4 {

    static int outerStaticNum;
    int outerNum;

    void testScopes() {
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };
        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }

    void predicate(){
        Predicate<Boolean> nonNull = Objects::nonNull;
        nonNull.test(true);
    }

    public static void main(String[] args) {
        BiFunction<String, Integer, Integer> stringIntegerIntegerBiFunction = Integer::valueOf;
        Integer apply = stringIntegerIntegerBiFunction.apply("1", 2);
        System.out.println(apply);

        Integer integer = Integer.valueOf("56");
        System.out.println(integer);
        Predicate<Boolean> nonNull = Objects::nonNull;
        boolean test = nonNull.test(true);
        System.out.println(test);

    }
}
