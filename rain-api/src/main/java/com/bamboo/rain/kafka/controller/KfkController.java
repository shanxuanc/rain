package com.bamboo.rain.kafka.controller;

import com.bamboo.rain.kafka.service.KfkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lpg
 * @description: 测试kfk生产与消费
 * @date 2020-12-2318:22
 */
@RestController
public class KfkController {
    @Autowired
    private KfkService kfkService;

    @GetMapping("/send")
    public String send() {
        kfkService.sendMsg("topic1", "this first msg");
        return "success-topic1";
    }
}