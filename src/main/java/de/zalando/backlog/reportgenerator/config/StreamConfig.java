package de.zalando.backlog.reportgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import de.zalando.backlog.reportgenerator.streamer.PartitionElector;
import de.zalando.backlog.reportgenerator.streamer.StreamManager;
import de.zalando.backlog.reportgenerator.streamer.StreamerFactory;
import de.zalando.backlog.reportgenerator.streamer.domain.StreamerType;

@Configuration
public class StreamConfig {


    @Bean(initMethod = "stream", destroyMethod = "close")
//    @Bean
    public StreamManager simpleReportStreamManager(StreamerFactory streamerFactory) {
        int numberOfPartitions = 2;
        PartitionElector partitionElector = new PartitionElector();
        return new StreamManager<>(numberOfPartitions, partitionElector, StreamerType.SIMPLE_REPORT, streamerFactory);
    }

}
