package com.ryota.rabbitmq.five;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息的接受
 */
public class ReceiveLog02 {
    //交换机的名称
    public static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel localChannel = RabbitMqUtils.getLocalChannel();
        //声明一个交换机
        /**
         * 1.交换机名称
         * 2.类型
         */
        localChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //声明一个队列 临时队列
        /**
         * 生成一个临时的,队列的名称是随机的
         * 当消费者断开与队列的连接的时候 队列就自动删除
         */
        String queue = localChannel.queueDeclare().getQueue();

        /**
         * 绑定交换机与队列
         */
        localChannel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("等待接受消息,把接受到消息打印在屏幕上...");
        //接受消息
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println("02控制台打印接收到的消息"+new String(message.getBody(),"UTF-8"));
        };

        //消费者取消消息时回调接口
        localChannel.basicConsume(queue,true,deliverCallback,consumerTag -> {});
    }
}
