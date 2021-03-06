package com.blankjee.code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author blankjee
 * @Date 2020/8/21 20:17
 */
public class SemapDemo implements Runnable {
    final Semaphore semp = new Semaphore(5);
    @Override
    public void run() {
        try {
            semp.acquire();
            // 模拟耗时操作
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId() + ": done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semp.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemapDemo semapDemo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            exec.submit(semapDemo);
        }
    }
}
