package com.bamboo.rain.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class RocketmqTransactionLog {

    private Integer id;
    private String transactionId;
    private String log;

}
