package com.ryota.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * 生产者 发送大量的消息
 */
public class Task01 {

    //队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //队列的声明
        /**
         * 生成一个队列
         * 参数:1.队列名称
         * 2.队列里的消息是否需要持久化 默认情况消息存储
         * 3.该队列是否只供一个消费者消费 是否进行消息共享 ,true 表示可以多个消费者消费
         * 4.是否自动删除 最后一个消费者断开连接后 该队列是否删除 true自动删除 false 反之
         * 5.其它参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //从控制台接受消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            /**
             * 发送一个消费
             * 1.发送到哪个交换机
             * 2.路由的key值是哪个,本次是队列名
             * 3.其它参数
             * 4.发送的消息体
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成" + message);
        }


    }

}
