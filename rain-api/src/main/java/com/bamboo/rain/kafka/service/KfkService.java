package com.bamboo.rain.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lpg
 * @description: kfk生产者与消费者
 * @date 2020-12-2317:57
 */
@Service
public class KfkService {

    private static final Logger log = LoggerFactory.getLogger(KfkService.class);

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;


    //消费者：监听topic1，groupId1
    /*@KafkaListener(groupId = "groupId2", topicPartitions =
            {@TopicPartition(topic = "topic2", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))})
    public void consumer1(ConsumerRecord<Integer, String> record) {
        *//*log.info("consumer1 kfk consume message start...");
        log.info("consumer1 kfk consume message topic:{},msg:{},offset:{}", record.topic(), record.value(), record.offset());
        log.info("consumer1 kfk consume message record:{}", record.toString());
        log.info("consumer1 kfk consume message end...");*//*
    }*/

    //生产者
    public void sendMsg(String topic, String msg) {
        /*log.info("开始发送kfk消息,topic:{},msg:{}", topic, msg);

        ListenableFuture<SendResult<Integer, String>> sendMsg = kafkaTemplate.send(topic, msg);

        //消息确认
        sendMsg.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("send error,ex:{},topic:{},msg:{}", throwable, topic, msg);
            }
            @Override
            public void onSuccess(SendResult<Integer, String> stringStringSendResult) {
                log.info("send success,topic:{},msg:{}", topic, msg);
            }
        });
        log.info("kfk send end!");*/
    }

}