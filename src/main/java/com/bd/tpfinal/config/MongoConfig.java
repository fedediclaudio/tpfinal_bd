package com.bd.tpfinal.config;

import com.bd.tpfinal.event.CascadePersistMongoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.bd.tpfinal")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Bean
    public CascadePersistMongoEventListener cascadingMongoEventListener() {
        return new CascadePersistMongoEventListener();
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }
}