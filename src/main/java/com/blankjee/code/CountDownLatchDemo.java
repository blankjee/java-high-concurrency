package com.blankjee.code;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author blankjee
 * @Date 2020/8/21 20:47
 */
public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch end = new CountDownLatch(10);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();
    @Override
    public void run() {
        try {
            // 模拟检查任务
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println("check completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            end.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 12; i++) {
            executorService.submit(demo);
        }
        // 等待检查
        end.await();
        // 发射火箭
        System.out.println("Fire!!!");
        executorService.shutdown();
    }
}
