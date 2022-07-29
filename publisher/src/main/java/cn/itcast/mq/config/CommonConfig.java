package cn.itcast.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            Integer delay = message.getMessageProperties().getReceivedDelay();
            if (!ObjectUtils.isEmpty(delay)) {
                log.info("接受到延迟消息的信息");
                return;
            }
            log.warn("消息发送失败，应答码{}，原因{}，交换机{}，路由键{}，消息{}", replyCode, replyText, exchange, routingKey, message.toString());
        });


    }


}
