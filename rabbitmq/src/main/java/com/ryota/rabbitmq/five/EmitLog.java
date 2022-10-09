package com.ryota.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Description 发消息 交换机
 * @Date 2022/10/8 20:05
 * @Author ryota
 */
public class EmitLog {
    //交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel localChannel = RabbitMqUtils.getLocalChannel();
//        localChannel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            localChannel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息" + message);
        }
    }
}
