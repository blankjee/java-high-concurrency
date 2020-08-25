package com.blankjee.code.socketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author blankjee
 * @Date 2020/8/24 15:50
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;
        while (true) {
            try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost", 8000));
            writer = new PrintWriter(client.getOutputStream(), true);

            writer.println("Hello!");
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("from server:" + reader.readLine());
            Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (writer != null)     writer.close();
                if (reader != null)     reader.close();
                if (client != null)     client.close();
            }
        }

    }
}
