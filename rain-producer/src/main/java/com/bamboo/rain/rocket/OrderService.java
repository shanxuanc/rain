package com.bamboo.rain.rocket;

import com.bamboo.rain.constant.CloudConstant;
import com.bamboo.rain.entity.Order;
import com.bamboo.rain.entity.UserAddMoneyDTO;
import com.bamboo.rain.mapper.OrderMapper;
import com.bamboo.rain.mapper.RocketMqTransactionLogMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RocketMqTransactionLogMapper rocketMqTransactionLogMapper;

    public void delete(String orderNo) {
        Order order = orderMapper.selectByNo(orderNo);
        //如果订单存在且状态为有效，进行业务处理
        if (order != null && CloudConstant.VALID_STATUS.equals(order.getStatus())) {
            String transactionId = UUID.randomUUID().toString();
            //如果可以删除订单则发送消息给rocketmq，让用户中心消费消息
            rocketMQTemplate.sendMessageInTransaction("add-amount",
                    MessageBuilder.withPayload(
                            UserAddMoneyDTO.builder()
                                    .userCode(order.getAccountCode())
                                    .amount(order.getAmount())
                                    .build()
                    )
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("order_id", order.getId())
                            .build()
                    , order
            );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeStatuswithRocketMqLog(Integer id, String status, String transactionId) {
        //将订单状态置位无效
        orderMapper.changeStatus(id, status);
        //插入事务表
        rocketMqTransactionLogMapper.insert(transactionId, "执行删除订单操作");
    }

}
