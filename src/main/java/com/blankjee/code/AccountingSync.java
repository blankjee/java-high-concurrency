package com.blankjee.code;

/**
 * @Author blankjee
 * @Date 2020/8/21 14:39
 */
public class AccountingSync implements Runnable {
    static AccountingSync instance = new AccountingSync();
    static int i = 0;
    public static synchronized void increase() {
        i++;
    }


    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AccountingSync());
        Thread t2 = new Thread(new AccountingSync());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
