package com.ryota.rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description TODO
 * @Date 2022/10/8 20:22
 * @Author ryota
 */
public class ReceiveLogsDirect01 {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel localChannel = RabbitMqUtils.getLocalChannel();
        //声明一个交换机
        localChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        localChannel.queueDeclare("console",false,false,false,null);
        localChannel.queueBind("console",EXCHANGE_NAME,"info");
        localChannel.queueBind("console",EXCHANGE_NAME,"warning");

        //接受消息
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("01控制台打印接收到的消息"+new String(message.getBody(),"UTF-8"));
        };

        //消费者取消消息时回调接口
        localChannel.basicConsume("console",true,deliverCallback,consumerTag -> {});


    }

}
