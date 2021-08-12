package com.bamboo.rain.rocket;

import com.bamboo.rain.entity.UserAddMoneyDTO;
import com.bamboo.rain.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "add-amount",consumerGroup = "cloud-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class AddUserAmountListener implements RocketMQListener<UserAddMoneyDTO> {
    private final AccountMapper accountMapper;
    /**
     * 收到消息的业务逻辑
     */
    @Override
    public void onMessage(UserAddMoneyDTO userAddMoneyDTO) {
        log.info("received message: {}",userAddMoneyDTO);
        accountMapper.increaseAmount(userAddMoneyDTO.getUserCode(), userAddMoneyDTO.getAmount());
        log.info("add money success");
    }
}
