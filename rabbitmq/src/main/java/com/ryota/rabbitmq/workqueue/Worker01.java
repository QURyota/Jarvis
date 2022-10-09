package com.ryota.rabbitmq.workqueue;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 这是一个工作县城(相当于消费者)
 */
public class Worker01 {
    //队列的名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();


        //声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息" + new String(message.getBody()));
        };
        //声明  取消消息的回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消费接口回调逻辑 ");
        };
        //消息的接受
        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费之后是否自动应答 true代表自动应答
         * 3.消费未成功消息的回调
         * 4.消费者取消消费的回调
         */
        System.out.println("c2等待接受消息.....");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
