package com.ryota.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.ryota.rabbitmq.utils.RabbitMqUtils;
import com.ryota.rabbitmq.utils.SleepUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息在手动应答时 不丢失,放回队列中重新消费
 *
 * 在等待30s的过程中 关闭此进程,dd则重新回到队列中,由c1消费者消费
 */
public class Worker04 {

    //队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("c2等待接受消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag,message)->{
            //沉睡1s
            SleepUtils.sleep(30);
            System.out.println("接受到底的消息:"+new String(message.getBody(),"UTF-8"));
            //手动应答
            /**
             * 1.消息的标记
             * 2.是否批量应答 false:不批量应答信道中的消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

        };

        //设置不公平分发
//        int prefetchCount = 1;
        //预取值为5
        int prefetchCount = 5;
        channel.basicQos(prefetchCount);

        //采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,(consumerTag)->{
            System.out.println(consumerTag +"消费者取消消费回调逻辑");
        });
    }

}
