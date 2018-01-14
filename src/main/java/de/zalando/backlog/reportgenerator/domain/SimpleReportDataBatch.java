package de.zalando.backlog.reportgenerator.domain;

import java.util.List;


import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;

public class SimpleReportDataBatch extends EventBatch<SimpleReportData> {

    private Integer batchId;
    private int partitionId;

    public SimpleReportDataBatch(final List<SimpleReportData> eventBatch, final Integer batchId, final int
            partitionId) {
        super(eventBatch);
        this.batchId = batchId;
        this.partitionId = partitionId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public int getPartitionId() {
        return partitionId;
    }
}
