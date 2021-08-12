package com.bamboo.rain.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order {

    private Integer id;
    private String orderNo;
    private String accountCode;
    private BigDecimal amount;
    private String status;
}
