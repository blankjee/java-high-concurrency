package com.blankjee.code.producerandconsumer;

/**
 * @Author blankjee
 * @Date 2020/8/23 20:42
 */
public class PCData {
    private final int intData;
    public PCData(int d) {
        intData = d;
    }
    public PCData(String d) {
        intData = Integer.valueOf(d);
    }
    public int getData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}
