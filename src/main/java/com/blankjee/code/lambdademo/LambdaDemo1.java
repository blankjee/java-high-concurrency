package com.blankjee.code.lambdademo;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * @Author blankjee
 * @Date 2020/8/25 19:57
 */
public class LambdaDemo1 {
    static int[] arr = {1,3,4,5,6,7,8,9};

    public static void main(String[] args) {
        IntConsumer outprintln = System.out::println;
        IntConsumer errprintln = System.err::println;
        Arrays.stream(arr).forEach(outprintln.andThen(errprintln));
    }
}
