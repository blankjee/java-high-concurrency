package com.blankjee.code;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author blankjee
 * @Date 2020/8/21 15:34
 */
public class ReenterLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;
    @Override
    public void run() {
        for (int j = 0; j < 10000000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                // 写在这里是为了尽可能的安全
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock reenterLock = new ReenterLock();
        Thread t1 = new Thread(reenterLock);
        Thread t2 = new Thread(reenterLock);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);
    }
}
