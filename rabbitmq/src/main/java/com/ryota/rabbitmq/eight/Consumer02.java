package com.ryota.rabbitmq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Description TODO
 * @Date 2022/10/10 21:26
 * @Author ryota
 * 消费者2
 */
public class Consumer02 {

    //死信队列名
    public static final String DEAD_QUEUE = "dead_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getLocalChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("consumer02接受的消息是:" + new String(message.getBody(), "UTF-8"));
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {
        });
    }
}
