package com.yunlong.seckilldemo.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

//消息的接送者
@Service
@Slf4j
public class MQReceiver {


    @RabbitListener(queues = "queue")
    public void receiver(Object msg){
        log.info("接收到消息："+msg);
    }


    @RabbitListener(queues = "queue_fanout01")
    public void receiver01(Object msg){
        log.info("QUEUE01接收到消息："+msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void receiver02(Object msg){
        log.info("QUEUE02接收到消息："+msg);
    }

}
