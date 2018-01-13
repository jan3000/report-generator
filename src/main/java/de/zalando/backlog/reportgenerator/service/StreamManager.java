package de.zalando.backlog.reportgenerator.service;

import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import de.zalando.backlog.reportgenerator.config.DataSourceProperties;

@Service
public class StreamManager {

    private static final Logger LOG = LoggerFactory.getLogger(StreamManager.class);
    private final Map<Integer, Future> partitionIdToFuture = Maps.newConcurrentMap();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ReportUpdateConsumerService reportUpdateConsumerService;
    private boolean streamingEnabled = false    ;

    @Autowired
    public StreamManager(final ReportUpdateConsumerService reportUpdateConsumerService) {
        this.reportUpdateConsumerService = reportUpdateConsumerService;
    }

    public void start() {
        LOG.info("Started postgres product update streaming");
        while (streamingEnabled && !Thread.currentThread().isInterrupted()) {
            OptionalInt availablePartition = getPartitionLock();

            if (availablePartition.isPresent()) {
                int partitionId = availablePartition.getAsInt();
                startNewStreamer(partitionId);
            } else {
                LOG.info("All partitions are being streamed. Sleep a while.");

                handleFinishedStreamers();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LOG.error("StreamManager thread has been interrupted while sleeping", e);
                }
            }
        }
        LOG.info("StreamManager thread interrupted");

    }

    private OptionalInt getPartitionLock() {
        return IntStream.range(1, DataSourceProperties.SHARDS + 1)
                        .filter(partitionId -> !partitionIdToFuture.containsKey(partitionId))
                        .findFirst();
    }

    private void startNewStreamer(final int partitionId) {
        LOG.info("Submit streaming for partition {}", partitionId);
        Future<?> submit = executorService.submit(new ProductUpdatePartitionStreamer(partitionId,
                reportUpdateConsumerService));
        partitionIdToFuture.put(partitionId, submit); // or write some lock before starting the Streamer
    }

    private void handleFinishedStreamers() {
        partitionIdToFuture.keySet().forEach(key -> {
            Future future = partitionIdToFuture.get(key);
            if (future.isDone() || future.isCancelled()) {
                LOG.warn("Thread of partition {} is done / cancelled. Remove from locking map", key);
                partitionIdToFuture.remove(key);
            }
        });
    }

    public void close() {
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Await termination was interrupted", e);
        }
        executorService.shutdown();
        executorService.shutdownNow();
    }

}
