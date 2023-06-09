package br.com.egc.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
        SpringApplication.run(BatchApplication.class, args);
        System.exit(SpringApplication.exit(applicationContext));
    }

}