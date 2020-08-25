package com.blankjee.code.futuredemo;

/**
 * @Author blankjee
 * @Date 2020/8/23 22:10
 */
public class Client {
    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        new Thread() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                future.setRealData(realData);
            }
        }.start();
        return future;
    }
}
