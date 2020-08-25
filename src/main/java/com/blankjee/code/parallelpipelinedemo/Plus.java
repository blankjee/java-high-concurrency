package com.blankjee.code.parallelpipelinedemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author blankjee
 * @Date 2020/8/24 9:59
 */
public class Plus implements Runnable {
    public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = bq.take();
                msg.j = msg.i + msg.j;
                Multiply.bq.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
