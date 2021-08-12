package com.bamboo.rain.mapper;

import com.bamboo.rain.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OrderMapper {

    @Select("select id, order_no orderNo, account_code accountCode, amount, status from z_order where order_no = #{orderNo}")
    Order selectByNo(@Param("orderNo") String orderNo);

    @Update("update z_order set status = #{status} where id = #{id}")
    void changeStatus(@Param("id") Integer id, @Param("status")String status);
}
