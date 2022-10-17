package com.ryota.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2022/10/12 21:03
 * @Author ryota
 */
@RestController
@RequestMapping("ttl")
@Slf4j
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendMsg")
    public void sendMsg(@RequestParam("msg") String msg) {
        log.info("当前时间:{},发送一条消息给两个TTL队列:{}", new Date().toString(), msg);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s队列" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s队列" + msg);
    }

    //开始发消息
    @GetMapping("sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间:{},发送一条消息时长{}毫秒TTL信息给队列QC:{}",
                new Date().toString(),ttlTime.toString(),message);
        rabbitTemplate.convertAndSend("X","XC",message,msg ->{
            //发送消息时候的延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        } );
    }

}
