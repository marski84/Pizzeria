package org.localhost.pizzeria.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.supply-check}")
    private String queueName;

    @Value("${rabbitmq.exchange.supply-check}")
    private String exchangeName;

    @Value("${rabbitmq.exchange.supply-check-dlq-exchange}")
    private String dlqExchangeName;

    @Value("${rabbitmq.routing-key.supply-check}")
    private String routingKey;

    @Value("${rabbitmq.queue.supply-check-dlq}")
    private String queueDlq;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(dlqExchangeName);
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(queueDlq)
                .build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlqExchangeName)
                .build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
//            ConnectionFactory connectionFactory,
//            MessageConverter messageConverter) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(messageConverter);
//        factory.setDefaultRequeueRejected(false);
//        return factory;
//    }
}