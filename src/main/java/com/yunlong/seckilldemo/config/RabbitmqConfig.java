package com.yunlong.seckilldemo.config;



import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




//rabbitmq配置类
@Configuration
public class RabbitmqConfig {

    // 广播模式 准备队列和交换机
    private static final String QUEUE01="queue_fanout01";
    private static final String QUEUE02="queue_fanout02";
    private static final String EXCHANGE="fanoutExchange";

    //准备的一个队列
    @Bean
    public Queue queue(){
        //第二个参数true表示持久化的意思
       return new Queue("queue",true);
    }

    @Bean
    public Queue queue01(){
        return new Queue(QUEUE01);
    }

    @Bean
    public Queue queue02(){
        return new Queue(QUEUE02);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);
    }



    //绑定队列到交换机
    @Bean
    public Binding binding01(){
        //将队列 1绑定到交换机上面
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }

    @Bean
    public Binding binding02(){
        //将队列 2绑定到交换机上面
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }
}
