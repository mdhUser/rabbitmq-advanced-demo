package cn.itcast.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Maxwell
 * @email: maodihui@foxmail.com
 * @date: 2022/6/11 1:22
 */
@Configuration
public class LazyConfig {


    @Bean
    public DirectExchange lazyExchange() {
        return new DirectExchange("lazy.exchange");
    }

    @Bean
    public Queue lazyQueue() {
        return QueueBuilder.durable("lazy.queue")
                .lazy()
                .build();
    }

    @Bean
    public Binding lazyBinding() {
        return BindingBuilder.bind(lazyQueue()).to(lazyExchange()).with("lazy");
    }


}
