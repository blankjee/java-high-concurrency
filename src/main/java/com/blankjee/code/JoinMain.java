package com.blankjee.code;

/**
 * @Author blankjee
 * @Date 2020/8/21 13:24
 */
public class JoinMain {
    public volatile static int i = 0;
    public static class AddTread extends Thread {
        @Override
        public void run() {
            for (i = 0; i < 100000000; i++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddTread at = new AddTread();
        at.start();
        Thread.sleep(5);
        // at.join();
        System.out.println(i);
    }
}
