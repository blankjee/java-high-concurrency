package com.blankjee.code.futuredemo;

/**
 * @Author blankjee
 * @Date 2020/8/23 22:12
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据：" + data.getResult());
    }
}
