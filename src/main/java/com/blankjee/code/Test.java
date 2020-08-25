package com.blankjee.code;

/**
 * @Author blankjee
 * @Date 2020/8/24 16:22
 */
public class Test implements Runnable {
    static int x = 0;
    @Override
    public void run() {
        for(int i = 0; i < 100; i++){
            x++;
        }
    }

    public static void main(String[] args) {
        new Thread().start();
        new Thread().start();
        System.out.println(x);
    }


}
