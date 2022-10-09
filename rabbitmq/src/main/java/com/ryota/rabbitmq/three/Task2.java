package com.ryota.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.ryota.rabbitmq.utils.RabbitMqUtils;
import sun.nio.ch.sctp.SctpNet;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 消息在手动应答时 不丢失,放回队列中重新消费
 */
public class Task2 {
    //队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明队列
        /**
         * 生成一个队列
         * 参数:1.队列名称
         * 2.队列里的消息是否需要持久化 默认情况消息存储
         * 3.该队列是否只供一个消费者消费 是否进行消息共享 ,true 表示可以多个消费者消费
         * 4.是否自动删除 最后一个消费者断开连接后 该队列是否删除 true自动删除 false 反之
         * 5.其它参数
         */
        //需要让队列持久化
        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            /**
             * 发送一个消费
             * 1.发送到哪个交换机
             * 2.路由的key值是哪个,本次是队列名
             * 3.其它参数
             * 4.发送的消息体
             *
             * 设置生产者发送消息为持久化消息(要求保存在磁盘上)
             */
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息:" + message);
        }
    }
}
