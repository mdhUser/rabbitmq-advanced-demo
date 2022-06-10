package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Maxwell
 * @email: maodihui@foxmail.com
 * @date: 2022/6/10 10:08
 */
//@Configuration
public class DirectConfig {

    @Bean
    public DirectExchange directExchange(){
    return new DirectExchange("cast.direct");
    }


    @Bean
    public Queue queue(){
        return new Queue("direct.queue");
    }


    @Bean
    public Binding binding(){
      return BindingBuilder.bind(queue()).to(directExchange()).with("direct");
    }



}
