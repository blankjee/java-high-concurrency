package com.blankjee.code;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author blankjee
 * @Date 2020/8/21 16:20
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock, "Thread_t1");
        Thread t2 = new Thread(fairLock, "Thread_t2");
        t1.start();
        t2.start();
    }
}
