package com.blankjee.code;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author blankjee
 * @Date 2020/8/21 13:31
 */


public class PlusTask implements Runnable {
    static volatile int k = 0;
    static AtomicInteger newK = new AtomicInteger(0);

    @Override
    public void run() {
        for (int i = 0; i < 10_000; i++) {
            k++;
            newK.getAndAdd(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new PlusTask());
            threads[i].start();
        }
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        System.out.println("k:" + k);
        System.out.println("newK:" + newK);
    }
}

