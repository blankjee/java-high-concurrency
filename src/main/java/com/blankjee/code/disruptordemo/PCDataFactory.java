package com.blankjee.code.disruptordemo;

import com.lmax.disruptor.EventFactory;

/**
 * @Author blankjee
 * @Date 2020/8/23 21:22
 */
public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
