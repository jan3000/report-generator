package de.zalando.backlog.reportgenerator.streamer;

import java.util.OptionalInt;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;
import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;
import de.zalando.backlog.reportgenerator.streamer.domain.StreamerType;

public class StreamManager<T extends EventBatch> {

    private static final Logger LOG = LoggerFactory.getLogger(StreamManager.class);
    private boolean streamingEnabled = true; // TODO handle interruption per stream type
    private int numberOfPartitions;
    private PartitionElector partitionElector;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private StreamerFactory streamerFactory;

    private StreamerType streamerType;

    public StreamManager(final int numberOfPartitions, final PartitionElector partitionElector, final StreamerType
            streamerType, StreamerFactory streamerFactory) {
        this.numberOfPartitions = numberOfPartitions;
        this.partitionElector = partitionElector;
        this.streamerType = streamerType;
        this.streamerFactory = streamerFactory;
    }

    public void stream() {
        while (streamingEnabled && !Thread.currentThread().isInterrupted()) {
            synchronized (this) { // TODO most probably this is anyway just one thread running here and can be removed
                OptionalInt partitionLock = partitionElector.getPartitionLock(numberOfPartitions);
                if (partitionLock.isPresent()) {
                    int partitionId = partitionLock.getAsInt();
                    LOG.info("Lock acquired for partition: {}", partitionId);
                    Streamer<SimpleReportDataBatch> streamer = streamerFactory.getStreamer(streamerType);
                    streamer.setPartitionId(partitionId);
                    Future<?> submit = executorService.submit(streamer);
                    partitionElector.setFuture(partitionId, submit);
                } else {
                    LOG.info("All partitions are being streamed.");
                    partitionElector.handleFinishedStreamers();
                    try {
                        LOG.info("Main thread sleeps a while");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        LOG.error("Main thread interrupted while sleeping", e);
                    }
                }
            }
        }
        LOG.error("Main stream was interrupted!");
    }

    public void close() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error("Interrupted while awaiting thread termination", e);
        }
    }
}
