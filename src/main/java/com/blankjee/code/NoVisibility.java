package com.blankjee.code;

/**
 * @Author blankjee
 * @Date 2020/8/21 13:45
 */
public class NoVisibility {
    //使用了volatile将会保证变量的可见性
    //未使用情况下将无法保证
    //在jvm的client模式并不使用volatile关键字的情况下任然会有可见性，这是因为JIT没有并足够的优化。
    //而在jvm的server模式并不使用volatile关键字的情况下不会有可见性，程序将一直执行。
    private volatile static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number = 100;
        ready = true;
        Thread.sleep(10000);
    }
}
