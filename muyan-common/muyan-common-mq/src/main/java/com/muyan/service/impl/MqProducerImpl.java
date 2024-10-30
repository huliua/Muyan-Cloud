package com.muyan.service.impl;

import com.muyan.domain.MqMessage;
import com.muyan.service.MqProducer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * 使用rabbitmq中模式的direct模式,发送消息
 * @author huliua
 * @version 1.0
 * @date 2024-09-12 19:52
 */
@Service
@Slf4j
public class MqProducerImpl implements MqProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(MqMessage message) {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功");
            } else {
                log.error("消息发送失败:{}", cause);
                // 记录日志或者重新发送
                throw new RuntimeException("消息发送失败");
            }
        });
        rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMessage());
    }
}
