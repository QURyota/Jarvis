package com.ryota.rabbitmq.utils;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 此类为连接工厂创建信道的工具类
 * @author ryota
 */
public class RabbitMqUtils {

    public static Channel getChannel() throws IOException, TimeoutException {
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
        return channel;
    }

    public static Channel getLocalChannel() throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂ip 连接rabbitmq的队列
        factory.setHost("127.0.0.1");
        //用户名
        factory.setUsername("guest");
        //密码
        factory.setPassword("guest");

        //创建连接
        Connection connection = factory.newConnection();

        //获取信道
        Channel channel = connection.createChannel();
        return channel;
    }

}
