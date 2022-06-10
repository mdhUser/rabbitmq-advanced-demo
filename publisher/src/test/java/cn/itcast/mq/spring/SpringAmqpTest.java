package cn.itcast.mq.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        String routingKey = "direct";
        String message = "hello, spring amqp!";
        //设置confirm callback
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().addCallback(
                //交换机服务接收信息成功
                result -> {
                    if (result.isAck()) {
                        log.info("消息发送成功-》连接MQ成功:ID{}", correlationData.getId());
                    } else
                        log.info("消息发送失败-》连接MQ成功:ID{}", correlationData.getId());

                },
                //交换机服务接收信息异常（mq服务出现问题）
                ex -> {
                    log.info("消息发送失败-》连接MQ异常:ID{}", correlationData.getId());
                }
        );

        //设置每次发消息的confirmcallback信息
        rabbitTemplate.convertAndSend("cast.direct", routingKey, message, correlationData);
    }

    @Test
    void testSendTTlQueue() {
        String message = "test ttl msg1";
        rabbitTemplate.convertAndSend("ttl.exchange", "ttl", message);
    }

    @Test
    void testSendDelayQueue() {

        Message message = MessageBuilder.withBody("test delay message".getBytes(StandardCharsets.UTF_8))
                .setHeader("x-delay", 5000)
                .build();
        //消息唯一标识
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());


        rabbitTemplate.convertAndSend("delay.exchange", "ttl", message,correlationData);

    }


    @Test
    void testSendLazyQueue() {
        String message = "test ttl msg1";
        rabbitTemplate.convertAndSend("ttl.exchange", "ttl", message);
    }

}
