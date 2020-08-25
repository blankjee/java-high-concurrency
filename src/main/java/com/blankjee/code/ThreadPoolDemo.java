package com.blankjee.code;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author blankjee
 * @Date 2020/8/22 9:32
 */
public class ThreadPoolDemo {
    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + " :Thread ID:"
                + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            service.submit(myTask);
        }
    }
}
