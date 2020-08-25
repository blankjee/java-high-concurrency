package com.blankjee.code.parallelpipelinedemo;

/**
 * @Author blankjee
 * @Date 2020/8/24 10:08
 */
public class PStreamMain {
    public static void main(String[] args) {
        // 开启三个工作线程
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                Msg msg = new Msg();
                msg.i = i;
                msg.j = j;
                msg.orgStr = "((" + i + "+" + j + ")*" + i + ")/2";
                Plus.bq.add(msg);
            }
        }
    }
}
