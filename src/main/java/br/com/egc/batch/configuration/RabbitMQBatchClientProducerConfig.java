package br.com.egc.batch.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RabbitMQBatchClientProducerConfig {

    @Value("${config.exchange.teste.cliente}")
    private String exchangeTesteCliente;

    @Value("${config.queue.teste.cliente}")
    private String queueTesteCliente;

    @Value("${config.routing.teste.cliente}")
    private String routingTesteCliente;

    @Value("${batch.rabbitmq.host}")
    private String host;
    @Value("${batch.rabbitmq.username}")
    private String userName;
    @Value("${batch.rabbitmq.password}")
    private String password;
    @Value("${batch.rabbitmq.port}")
    private int port;
    @Value("${batch.rabbitmq.virtualhost}")
    private String vhost;

    @Bean
    public ConnectionFactory connectionFactory() throws NoSuchAlgorithmException, KeyManagementException {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.getRabbitConnectionFactory().useSslProtocol();
        connectionFactory.setPort(port);
        return connectionFactory;
    }

    @Bean
    Queue queueCliente() {
        return new Queue(queueTesteCliente, true);
    }

    @Bean
    DirectExchange exchangeCliente() {
        return new DirectExchange(exchangeTesteCliente);
    }

    @Bean
    Binding bindingCliente() {
        return BindingBuilder.bind(queueCliente()).to(exchangeCliente()).with(routingTesteCliente);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() throws NoSuchAlgorithmException, KeyManagementException {
        var rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.declareQueue(queueCliente());
        rabbitAdmin.declareExchange(exchangeCliente());
        rabbitAdmin.declareBinding(bindingCliente());
        return rabbitAdmin;
    }


}