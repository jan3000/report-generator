package de.zalando.backlog.reportgenerator.streamer.impl.simplereport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.zalando.backlog.reportgenerator.streamer.components.Committer;
import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;
import de.zalando.backlog.reportgenerator.repository.PGQService;

public class ReportCommitter implements Committer<SimpleReportDataBatch> {

    private static final Logger LOG = LoggerFactory.getLogger(ReportCommitter.class);
    private PGQService pgqService;

    public ReportCommitter(final PGQService pgqService) {
        this.pgqService = pgqService;
    }

    @Override
    public void commitBatch(final SimpleReportDataBatch eventBatch) {
        LOG.info("Committing batch: {}", eventBatch);
        pgqService.finishBatch(eventBatch.getPartitionId(), eventBatch.getBatchId());
    }
}
