package com.blankjee.code.producerandconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author blankjee
 * @Date 2020/8/23 21:04
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>(100);
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(producer1);
        es.execute(producer2);
        es.execute(producer3);
        es.execute(consumer1);
        es.execute(consumer2);
        es.execute(consumer3);
        Thread.sleep(10 * 1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();
        es.shutdown();
    }
}
