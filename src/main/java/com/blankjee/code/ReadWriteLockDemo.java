package com.blankjee.code;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author blankjee
 * @Date 2020/8/21 20:26
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = reentrantReadWriteLock.readLock();
    private static Lock writeLock = reentrantReadWriteLock.writeLock();
    private int value;

    /**
     * 模拟读操作
     * @param lock
     * @return
     */
    public Object handleRead(Lock lock) {
        try {
            lock.lock();
            // 模拟读操作的耗时
            Thread.sleep(1000);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            return value;
        }
    }

    /**
     * 模拟写操作
     * @param lock
     * @param index
     */
    public void handleWrite(Lock lock, int index) {
        try {
            lock.lock();;
            Thread.sleep(1000);
            value = index;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                // demo.handleRead(readLock);
                demo.handleRead(lock);
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                // demo.handleWrite(writeLock, new Random().nextInt());
                demo.handleWrite(lock, new Random().nextInt());
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(writeRunnable).start();
        }
    }
}
