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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.amqp.AmqpItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@EnableBatchProcessing
public class ClienteJdbcCursorItemJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Autowired
    JobListener jobListener;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step clienteJdbcCursorItemStep(ItemReader clienteJdbcCursorItemReader,
                                          AmqpItemWriter clienteJdbcCursorItemWriter) {
        return stepBuilderFactory.get("clienteJdbcCursorItemStep")
                .<Cliente, Cliente>chunk(10)
                .reader(clienteJdbcCursorItemReader)
                .writer(clienteJdbcCursorItemWriter)
                .build();
    }


    @Bean
    public Job clienteJdbcCursorJob(Step clienteJdbcCursorItemStep) throws Exception {
        return jobBuilderFactory.get("clienteJdbcCursorJob")
                .incrementer(new RunIdIncrementer())
                .start(clienteJdbcCursorItemStep)
                .listener(jobListener)
                .build();
    }

}
