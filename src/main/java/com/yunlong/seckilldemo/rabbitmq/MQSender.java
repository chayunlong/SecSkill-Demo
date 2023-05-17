package com.yunlong.seckilldemo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

//消息的发送者
@Service
@Slf4j
public class MQSender {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(Object msg){
        log.info("发送消息："+msg);
        //将消息发送到交换机  fanoutExchange
        rabbitTemplate.convertAndSend("fanoutExchange","",msg);
    }
}
