package com.bamboo.rain.rocket;

import com.bamboo.rain.constant.CloudConstant;
import com.bamboo.rain.entity.RocketmqTransactionLog;
import com.bamboo.rain.mapper.OrderMapper;
import com.bamboo.rain.mapper.RocketMqTransactionLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RocketMQTransactionListener
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddUserAmountListener implements RocketMQLocalTransactionListener {
    private final RocketMqTransactionLogMapper rocketMqTransactionLogMapper;
    private final OrderService orderService;

    /**
     * 执行本地事务
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        log.info("执行本地事务");
        MessageHeaders headers = message.getHeaders();
        //获取事务ID
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        Integer orderId = Integer.valueOf((String) headers.get("order_id"));
        log.info("transactionId is {}, orderId is {}", transactionId, orderId);

        try {
            //执行本地事务，并记录日志
            orderService.changeStatuswithRocketMqLog(orderId, CloudConstant.INVALID_STATUS, transactionId);
            //执行成功，可以提交事务
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("executeLocalTransaction error", e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 本地事务的检查，检查本地事务是否成功
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        MessageHeaders headers = message.getHeaders();
        //获取事务ID
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("检查本地事务,事务ID:{}", transactionId);
        //根据事务id从日志表检索
        RocketmqTransactionLog rocketmqTransactionLog = rocketMqTransactionLogMapper.selectOne(transactionId);
        if (null != rocketmqTransactionLog) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }

}
