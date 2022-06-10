package cn.itcast.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "direct.queue")
    public void listenSimpleQueue(String msg) {
        log.warn("消费者接收到direct.queue的消息【" + msg + "】");
        int i = 1 / 0;
    }

    @RabbitListener(queues = "simple.queue")
    public void listenDlQueue(String msg) {
        log.warn("消费者测试死信的消息【" + msg + "】");
        int i = 1 / 0;
        System.out.println("消费者测试死信的消息【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dl.ttl.queue", durable = "true"),
            exchange = @Exchange(name = "dl.ttl.exchange"),
            key = "ttl"
    ))
    public void listenTtlQueue(String msg) {
        log.info("接收到dl.ttl.queue的延迟消息:{}", msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue", durable = "true"),
            exchange = @Exchange(name = "delay.exchange", delayed = "true"),
            key = "ttl"
    ))
    public void listenDelayQueue(String msg) {
        log.info("接收到delay.queue的延迟消息:{}", msg);
    }

}
