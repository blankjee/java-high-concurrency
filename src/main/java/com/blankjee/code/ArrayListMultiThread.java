package com.blankjee.code;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @Author blankjee
 * @Date 2020/8/21 14:54
 */
public class ArrayListMultiThread {
    static Vector<Integer> al = new Vector<>(10);
    public static class AddThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                al.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(al.size());
    }
}
