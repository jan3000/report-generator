package de.zalando.backlog.reportgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import de.zalando.backlog.reportgenerator.service.ReportUpdateConsumerService;
import de.zalando.backlog.reportgenerator.service.StreamManager;

@Configuration
public class StreamConfig {

    @Bean(initMethod = "start")
    public StreamManager streamManager(ReportUpdateConsumerService reportUpdateConsumerService) {
        return new StreamManager(reportUpdateConsumerService);
    }

}
