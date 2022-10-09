package com.ryota.rabbitmq.one;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Ryota
 * @description 生产者 发消息
 * @date 2022/8/18 23:04
 */
public class Producer {

    /**
     * 队列名称
     */
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂ip 连接rabbitmq的队列
        factory.setHost("1.117.156.46");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("123");
        
        //创建连接
        Connection connection = factory.newConnection();

        //获取信道
        Channel channel = connection.createChannel();
        /**
         * 生成一个队列
         * 参数:1.队列名称
         * 2.队列里的消息是否需要持久化 默认情况消息存储
         * 3.该队列是否只供一个消费者消费 是否进行消息共享 ,true 表示可以多个消费者消费
         * 4.是否自动删除 最后一个消费者断开连接后 该队列是否删除 true自动删除 false 反之
         * 5.其它参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发消息
        String message = "hello world";

        /**
         * 发送一个消费
         * 1.发送到哪个交换机
         * 2.路由的key值是哪个,本次是队列名
         * 3.其它参数
         * 4.发送的消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕");

    }
    
}
