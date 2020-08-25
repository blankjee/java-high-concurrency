package com.blankjee.code.multhreaddebug;

/**
 * @Author blankjee
 * @Date 2020/8/25 21:35
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + "----------进入");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(currentThread.getName() + "----------离开");
        }
    }
}
