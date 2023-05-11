package br.com.egc.batch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "springDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource springDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "appDataSource")
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }


}