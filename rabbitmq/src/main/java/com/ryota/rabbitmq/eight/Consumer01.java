package com.ryota.rabbitmq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;
import sun.plugin2.main.client.MozillaServiceDelegate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Description TODO
 * @Date 2022/10/10 20:40
 * @Author ryota
 * 死信队列 实战
 * 消费者1
 */
public class Consumer01 {

    //普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    //死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    //普通队列名
    public static final String NORMAL_QUEUE = "normal_queue";

    //死信队列名
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getLocalChannel();

        //声明死信和普通交换机类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        //声明普通队列
        Map<String, Object> arguments = new HashMap<>();
        //过期时间 10s = 10000ms
//        arguments.put("x-message-ttl",10000);
        //正常队列设置过期之后死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "lisi");

        //设置正常队列的长度限制
//        arguments.put("x-max-length",6);
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        //绑定普通的交换机与队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        //绑定死信的交换机与队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收消息....");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            if (msg.equals("5info")) {
                System.out.println("consumer01接受的消息是:" + msg + "此消息是被拒绝的");
                /**
                 * false 拒绝之后不放回队列
                 */
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("consumer01接受的消息是:" + msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };
        //开启手动应答
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, consumerTag -> {
        });
    }
}
