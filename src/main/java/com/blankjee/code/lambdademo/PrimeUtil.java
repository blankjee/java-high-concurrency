package com.blankjee.code.lambdademo;

import java.util.stream.IntStream;

/**
 * @Author blankjee
 * @Date 2020/8/25 20:01
 */
public class PrimeUtil {
    public static boolean isPrime(int number) {
        int tmp = number;
        if (tmp < 2) {
            return false;
        }
        for (int i = 2; Math.sqrt(tmp) >= i; i++) {
            if (tmp % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 串行
        System.out.println(IntStream.range(1, 10000000).filter(PrimeUtil::isPrime).count());
        // 并行
        // System.out.println(IntStream.range(1, 10000000).parallel().filter(PrimeUtil::isPrime).count());
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }
}
