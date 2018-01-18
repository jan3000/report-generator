package de.zalando.backlog.reportgenerator.streamer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.zalando.backlog.reportgenerator.streamer.components.Committer;
import de.zalando.backlog.reportgenerator.streamer.components.Processor;
import de.zalando.backlog.reportgenerator.streamer.components.Source;
import de.zalando.backlog.reportgenerator.streamer.components.Writer;
import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;

public class Streamer<T extends EventBatch> implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Streamer.class);
    private Source<T> source;
    private Processor<T> processor;
    private Writer<T> writer;
    private Committer<T> committer;
    private int partitionId;

    Streamer(final Source<T> source, final Processor<T> processor, final Writer<T> writer, final
    Committer<T> committer) {
        this.source = source;
        this.processor = processor;
        this.writer = writer;
        this.committer = committer;
    }

    void setPartitionId(final int partitionId) {
        LOG.info("Set partition: {}", partitionId);
        this.partitionId = partitionId;
    }

    @Override
    public void run() {
        LOG.info("Start streaming partition: {}", partitionId);
        while (!Thread.currentThread().isInterrupted()) {
            T batch = source.getBatch(partitionId);
            if (!batch.getEventBatch().isEmpty()) {
                LOG.info("Processing next batch : {}", batch);
                processor.processBatch(batch);
                writer.writeBatch(batch);
                committer.commitBatch(batch);
            }
        }

        LOG.warn("Streaming interrupted for partition: {}", partitionId);
    }
}
