package com.blankjee.code;

/**
 * @Author blankjee
 * @Date 2020/8/21 14:20
 */
public class PriorityDemo {

    public static class HighPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10_000_000) {
                        System.out.println("High Priority is completed");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10_000_000) {
                        System.out.println("Low Priority is completed");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread high = new HighPriority();
        Thread low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);

        low.start();
        Thread.sleep(10);
        high.start();

    }
}
