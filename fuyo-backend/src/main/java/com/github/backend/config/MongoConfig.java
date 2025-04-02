package com.github.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.github.backend.repository.mongodb")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database:fuyo_db}") // 添加默认值
    private String databaseName;

    @NotNull
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @NotNull
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @NotNull
    @Bean
    @Override
    public MongoTemplate mongoTemplate(@NotNull MongoDatabaseFactory databaseFactory,
                                       @NotNull MappingMongoConverter converter) {
        return new MongoTemplate(databaseFactory, converter);
    }

    @Override
    protected boolean autoIndexCreation() {
        return true; // 启用自动索引创建
    }
}