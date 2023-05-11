package br.com.egc.batch.writer;

import br.com.egc.batch.model.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.batch.item.amqp.AmqpItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ClienteAmqpItemWriterConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${config.exchange.teste.cliente}")
    private String exchangeTesteCliente;

    @Value("${config.routing.teste.cliente}")
    private String routingTesteCliente;

    @Bean
    public AmqpItemWriter<Cliente> clienteAmqpItemWriter() {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setExchange(exchangeTesteCliente);
        rabbitTemplate.setRoutingKey(routingTesteCliente);
        AmqpItemWriter<Cliente> amqpItemWriter = new AmqpItemWriter<>(rabbitTemplate);
        return amqpItemWriter;
    }


}


