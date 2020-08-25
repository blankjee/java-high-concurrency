package com.blankjee.code.futuredemo;

import java.util.StringTokenizer;

/**
 * @Author blankjee
 * @Date 2020/8/23 22:01
 */
public class FutureData implements Data {
    protected RealData realData = null;
    protected boolean isReady = false;
    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        notifyAll();
    }
    public synchronized String getResult() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }

}
