package com.blankjee.code.parallelpipelinedemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author blankjee
 * @Date 2020/8/24 10:03
 */
public class Multiply implements Runnable {
    public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<>();
    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = bq.take();
                msg.i = msg.i * msg.j;
                Div.bq.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
