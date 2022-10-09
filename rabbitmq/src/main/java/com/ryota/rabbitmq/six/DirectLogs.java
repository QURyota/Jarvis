package com.ryota.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Description TODO
 * @Date 2022/10/8 20:27
 * @Author ryota
 */
public class DirectLogs {
    //交换机的名称
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel localChannel = RabbitMqUtils.getLocalChannel();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            localChannel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息" + message);
        }
    }
}
