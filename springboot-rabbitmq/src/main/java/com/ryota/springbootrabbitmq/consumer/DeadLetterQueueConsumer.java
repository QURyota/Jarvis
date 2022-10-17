package com.ryota.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.util.Date;


/**
 * @Description TODO
 * @Date 2022/10/12 21:07
 * @Author ryota
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receivedD(Message message, Channel channel)throws Exception{
        String msg = new String(message.getBody());
        log.info("当前时间是:{},收到死信队列消息:{}",new Date().toString(),msg);
    }
}
