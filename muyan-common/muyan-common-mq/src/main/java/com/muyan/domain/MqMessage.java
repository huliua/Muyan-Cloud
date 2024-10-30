package com.muyan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息对象
 * @author huliua
 * @version 1.0
 * @date 2024-09-12 19:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqMessage implements Serializable {

    private String exchange;

    private String routingKey;

    private String message;

}
