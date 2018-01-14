package de.zalando.backlog.reportgenerator.streamer.impl.simplereport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.zalando.backlog.reportgenerator.streamer.components.Writer;
import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;
import de.zalando.backlog.reportgenerator.repository.ReportStoreService;

public class ReportWriter implements Writer<SimpleReportDataBatch> {

    private static final Logger LOG = LoggerFactory.getLogger(ReportWriter.class);
    private ReportStoreService reportStoreService;

    public ReportWriter(final ReportStoreService reportStoreService) {
        this.reportStoreService = reportStoreService;
    }

    @Override
    public void writeBatch(final SimpleReportDataBatch eventBatch) {
        LOG.info("Write batch: {}", eventBatch);
        reportStoreService.upsertSimpleReportData(eventBatch.getEventBatch());
    }
}
