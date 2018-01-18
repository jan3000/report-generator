package de.zalando.backlog.reportgenerator.streamer.impl.simplereport;

import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.zalando.backlog.reportgenerator.streamer.components.Source;
import de.zalando.backlog.reportgenerator.domain.SimpleReportData;
import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;
import de.zalando.backlog.reportgenerator.repository.PGQService;

public class ReportSource implements Source<SimpleReportDataBatch> {

    private static final Logger LOG = LoggerFactory.getLogger(ReportSource.class);
    private PGQService pgqService;

    public ReportSource(PGQService pgqService) {
        this.pgqService = pgqService;
    }

    @Override
    public SimpleReportDataBatch getBatch(int partitionId) {
        LOG.info("Process next batch on partition {}", partitionId);
        Integer batchId;
        List<SimpleReportData> batch = Lists.newArrayList();
        while ((batchId = pgqService.getNextBatchId(partitionId)) != null && (batch = pgqService.getBatch(partitionId,
                batchId)).isEmpty()) {
            pgqService.finishBatch(partitionId, batchId);
        }
        // TODO should we wait here?
        if (batchId == null) {
            LOG.info("Sleep because batch id is null");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.warn("Sleeping thread interrupted", e);
            }
        }
        return new SimpleReportDataBatch(batch, batchId, partitionId);
    }
}
