package com.bamboo.rain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Mapper
public interface AccountMapper {

    @Update("update z_user set amount = amount + #{amount} where account_code = #{userCode}")
    void increaseAmount(@Param("userCode") String userCode, @Param("amount") BigDecimal amount);
}
