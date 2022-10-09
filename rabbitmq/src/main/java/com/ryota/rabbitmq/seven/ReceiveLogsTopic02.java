package com.ryota.rabbitmq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;

/**
 * @Description TODO
 * @Date 2022/10/8 20:49
 * @Author ryota
 */
public class ReceiveLogsTopic02 {
    //交换机声明
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel localChannel = RabbitMqUtils.getLocalChannel();
        //声明交换机
        localChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //声明队列
        String queueName = "Q2";
        localChannel.queueDeclare(queueName,false,false,false,null);
        localChannel.queueBind(queueName,EXCHANGE_NAME,"*.*.rabbit");
        localChannel.queueBind(queueName,EXCHANGE_NAME,"lazy.#");
        System.out.println("等待接受消息....");

        //接受消息
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody(),"UTF-8"));
            System.out.println("接受队列是:"+queueName +"绑定键是:"+message.getEnvelope().getRoutingKey());
        };

        //接受消息
        localChannel.basicConsume(queueName,true,deliverCallback,consumerTag -> {});
    }
}
