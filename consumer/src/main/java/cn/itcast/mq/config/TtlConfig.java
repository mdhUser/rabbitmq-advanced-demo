package cn.itcast.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Maxwell
 * @email: maodihui@foxmail.com
 * @date: 2022/6/11 0:16
 */
@Configuration
public class TtlConfig {


    @Bean
    public DirectExchange ttlExchange(){
        return new DirectExchange("ttl.exchange", true, false);
    }


    @Bean
    public Queue ttlQueue(){
        return QueueBuilder.durable("ttl.queue") // 指定队列名称，并持久化
                .deadLetterExchange("dl.ttl.exchange") // 指定死信交换机
                .ttl(5000)
                .build();
    }


    @Bean
    public Binding ttlBinding(DirectExchange ttlExchange, Queue ttlQueue){
        return BindingBuilder.bind(ttlQueue).to(ttlExchange).with("ttl");
    }


}
