package com.nvd.electroshop.config;

import com.nvd.electroshop.constant.RabbitMQ;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue productQueue() {
        return new Queue(RabbitMQ.PRODUCT_QUEUE);
    }

    @Bean
    public Exchange productExchange() {
        return new DirectExchange(RabbitMQ.PRODUCT_EXCHANGE);
    }

    @Bean
    public Binding productBinding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQ.PRODUCT_UPLOAD_IMG_ROUTING_KEY).noargs();
    }

    // 👇 Converter để RabbitTemplate hiểu object
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}