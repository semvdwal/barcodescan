package nl.sem.barcodescan.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories(basePackages = "nl.sem.barcodescan.repositories")
@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private final Environment environment;

    public MongoConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    @Bean
    @Primary
    public MongoClient reactiveMongoClient() {
        int port = environment.getProperty("mongodb.port", Integer.class);
        String host = environment.getProperty("mongodb.host", String.class);
        return MongoClients.create(String.format("mongodb://%s:%d", host, port));
    }

    @Override
    protected String getDatabaseName() {
        return environment.getProperty("mongodb.database", String.class);
    }

    @Override
    public CustomConversions customConversions() {
        return super.customConversions();
    }

    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        return super.mappingMongoConverter();
    }

}
