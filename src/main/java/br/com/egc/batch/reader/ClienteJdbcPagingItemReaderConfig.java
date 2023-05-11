package br.com.egc.batch.reader;

import br.com.egc.batch.mapper.ClienteRowMapper;
import br.com.egc.batch.model.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class ClienteJdbcPagingItemReaderConfig {

    @Bean
    public JdbcPagingItemReader<Cliente> clienteJdbcPagingItemReader(
            @Qualifier("appDataSource") DataSource dataSource,
            PagingQueryProvider queryProvider) {
        return new JdbcPagingItemReaderBuilder<Cliente>()
                .name("clienteJdbcPagingItemReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .pageSize(10)
                .fetchSize(10)
                .rowMapper(new ClienteRowMapper())
                .build();
    }

    @Bean
    public SqlPagingQueryProviderFactoryBean queryProvider(
            @Qualifier("appDataSource") DataSource dataSource
    ) {

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from cliente");
        queryProvider.setSortKey("id");
        return queryProvider;

    }


}
