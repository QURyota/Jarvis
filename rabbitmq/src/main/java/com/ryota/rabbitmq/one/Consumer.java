package com.ryota.rabbitmq.one;

import com.rabbitmq.client.*;
//import com.sun.org.apache.xpath.internal.operations.String;

//import java.lang.String;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接受消息的 消费者
 *
 * @author ryota
 */
public class Consumer {

    //队列的名称
    public static final java.lang.String QUEUE_NAME = "hello";

    //接受消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂ip 连接rabbitmq的队列
        factory.setHost("1.117.156.46");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(message.getBody().toString());
        };
        //声明  取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费消息被中断 ");
        };
        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费之后是否自动应答 true代表自动应答
         * 3.消费未成功消息的回调
         * 4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }

}
