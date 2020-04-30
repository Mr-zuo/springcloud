package com.ron.combat.microserviceorder.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    final static String queue_a = "queue_a";
    final static String queue_b = "queue_b";

    @Bean
    public Queue queue_a() {
        return new Queue(RabbitMqConfig.queue_a);
    }

    @Bean
    public Queue queue_b() {
        return new Queue(RabbitMqConfig.queue_b);
    }

    /**
     * 声明一个Topic类型的交换机
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("demo_exchange");
    }

    /**
     * 绑定Q到交换机,并且指定routingKey
     * @param queue_a
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queue_a, TopicExchange exchange) {
        return BindingBuilder.bind(queue_a).to(exchange).with("topic.q.a");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queue_b, TopicExchange exchange) {
        return BindingBuilder.bind(queue_b).to(exchange).with("topic.#");
    }
}
