package com.bamboo.rain.mapper;

import com.bamboo.rain.entity.RocketmqTransactionLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RocketMqTransactionLogMapper {

    @Select("select id, transaction_id transactionId, log from z_transaction_log where transaction_id = #{transactionId}")
    RocketmqTransactionLog selectOne(@Param("transactionId")String transactionId);

    @Insert("insert into z_transaction_log values (null, #{transactionId}, #{log})")
    void insert(@Param("transactionId") String transactionId, @Param("log") String log);

}
