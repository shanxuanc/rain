package com.bamboo.rain.rocket;

import com.bamboo.rain.entity.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class Demo01Producer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public SendResult syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 同步发送消息
        return rocketMQTemplate.syncSend(Demo01Message.TOPIC, message);
    }

    public void asyncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // 异步发送消息
        rocketMQTemplate.asyncSend(Demo01Message.TOPIC, MessageBuilder.withPayload(message).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("asyncSend sendResult:{}", sendResult);
            }
            @Override
            public void onException(Throwable e) {
                log.error("asyncSend error", e);
            }
        }, 300000, 2);
        log.info("asyncSend success!!!");
    }

    public void onewaySend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        // oneway 发送消息
        rocketMQTemplate.sendOneWay(Demo01Message.TOPIC, message);

    }

    public void sendDelayMessage(String topic, String message, int delayLevel) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), 2000, delayLevel);
        log.info("sendtime is {}", DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss").format(LocalDateTime.now()));
        log.info("sendResult is{}", sendResult);
    }


    public SendResult syncSendOrder(Integer id) {
        // 创建 Demo01Message 消息
        Demo01Message message = new Demo01Message();
        message.setId(id);
        log.info("syncSendOrder message:{}", message);
        // 同步发送消息
        return rocketMQTemplate.syncSendOrderly(Demo01Message.TOPIC, message, "syncSendOrderly");
    }

}