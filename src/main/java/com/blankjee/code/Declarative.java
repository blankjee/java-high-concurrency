package com.blankjee.code;

import java.util.Arrays;

/**
 * @Author blankjee
 * @Date 2020/8/25 11:34
 */
public class Declarative {
    public static void main(String[] args) {
        int[] iArr = {1, 3, 4, 5, 6, 9, 8, 7, 4, 2};
        // Arrays.stream(iArr).forEach(System.out::println);
        Arrays.stream(iArr).map((x)->x=x+1).forEach(System.out::println);
        System.out.println();
        Arrays.stream(iArr).forEach(System.out::println);
    }
}
