package com.bamboo.rain.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserAddMoneyDTO {

    private String userCode;
    private BigDecimal amount;
}
