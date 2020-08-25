package com.blankjee.code.disruptordemo;

import com.lmax.disruptor.WorkHandler;

/**
 * @Author blankjee
 * @Date 2020/8/23 21:19
 */
public class Consumer implements WorkHandler<PCData> {
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event: --" + pcData.get() * pcData.get() + "--");
    }
}
