package com.nvd.electroshop.component;

import com.nvd.electroshop.constant.RabbitMQ;
import com.nvd.electroshop.dto.request.ProductImageMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // upload ảnh
    public void uploadProductImage(ProductImageMessage productImageMessage) {

        rabbitTemplate.convertAndSend(RabbitMQ.PRODUCT_EXCHANGE, RabbitMQ.PRODUCT_UPLOAD_IMG_ROUTING_KEY, productImageMessage);
    }
}
