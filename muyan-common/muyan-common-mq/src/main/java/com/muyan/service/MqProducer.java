package com.muyan.service;

import com.muyan.domain.MqMessage;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-09-12 19:47
 */
public interface MqProducer {

    public void sendMessage(MqMessage message);
}
