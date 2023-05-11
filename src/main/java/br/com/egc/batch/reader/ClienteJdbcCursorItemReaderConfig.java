package br.com.egc.batch.reader;

import br.com.egc.batch.mapper.ClienteRowMapper;
import br.com.egc.batch.model.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class ClienteJdbcCursorItemReaderConfig {

    @Bean
    public JdbcCursorItemReader<Cliente> clienteJdbcCursorItemReader(
            @Qualifier("appDataSource") DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Cliente>()
                .name("clienteJdbcCursorItemReader")
                .dataSource(dataSource)
                .sql("select * from cliente")
                .rowMapper(new ClienteRowMapper())
                .build();
    }

}
