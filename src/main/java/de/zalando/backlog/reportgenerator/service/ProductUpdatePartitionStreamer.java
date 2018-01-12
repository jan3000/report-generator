package de.zalando.backlog.reportgenerator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductUpdatePartitionStreamer implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ProductUpdatePartitionStreamer.class);
    private int partitionId;
    private boolean streamingEnabled = true;
    private ReportUpdateConsumerService reportUpdateConsumerService;

    public ProductUpdatePartitionStreamer(final int partitionId, final ReportUpdateConsumerService reportUpdateConsumerService) {
        this.partitionId = partitionId;
        this.reportUpdateConsumerService = reportUpdateConsumerService;
    }

    @Override
    public void run() {
        LOG.info("Start streaming partition {}", partitionId);

        while (streamingEnabled && !Thread.currentThread().isInterrupted()) {
            reportUpdateConsumerService.processNextBatchIfAvailable(partitionId);
        }
        LOG.info("ProductUpdatePartitionStreamer interrupted");
    }
}