package com.bamboo.rain.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "delay-topic",
        consumerGroup = "delay-group"
)
public class DelayConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message time is {}", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(LocalDateTime.now()));
        log.info("received message is {}", message);
    }
}