package br.com.egc.batch.job;

import br.com.egc.batch.listener.JobListener;
import br.com.egc.batch.model.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.amqp.AmqpItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@EnableBatchProcessing
public class ClienteJdbcPagingItemJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    JobListener jobListener;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step clienteJdbcPagingItemStep(JdbcPagingItemReader clienteJdbcPagingItemReader,
                                          AmqpItemWriter clienteAmqpItemWriter) {
        return stepBuilderFactory.get("stepClienteJdbcCursorConfig")
                .<Cliente, Cliente>chunk(10)
                .reader(clienteJdbcPagingItemReader)
                .writer(clienteAmqpItemWriter)
                .build();
    }

    @Bean
    public Job clienteJdbcPagingItemJob(Step clienteJdbcPagingItemStep) throws Exception {
        return jobBuilderFactory.get("clienteJdbcPagingItemJob")
                .incrementer(new RunIdIncrementer())
                .start(clienteJdbcPagingItemStep)
                .listener(jobListener)
                .build();
    }

}
