package de.zalando.backlog.reportgenerator.config;

import java.util.Map;
import java.util.stream.IntStream;
import javax.sql.DataSource;

import com.google.common.collect.Maps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import de.zalando.backlog.reportgenerator.domain.ShardedDataSource;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DatabaseConfig {


    @Bean
    public ShardedDataSource shardedDataSource(DataSourceProperties dataSourceProperties) {
        Map<Integer, DataSource> shardToDataSource = Maps.newHashMap();

        IntStream.range(1, dataSourceProperties.getShards() + 1).forEach(shard -> {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(String.format(dataSourceProperties.getJdbcUrlTemplate(), shard));
            config.setDriverClassName(dataSourceProperties.getDriver());
            config.setUsername(dataSourceProperties.getUsername());
            config.setPassword(dataSourceProperties.getPassword());

            config.setMinimumIdle(2);
            config.setMaximumPoolSize(10);

            shardToDataSource.put(shard, new HikariDataSource(config));
        });

        return new ShardedDataSource(shardToDataSource);
    }


}
