package de.zalando.backlog.reportgenerator.streamer.impl.simplereport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.zalando.backlog.reportgenerator.streamer.components.Processor;
import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;

public class ReportProcessor implements Processor<SimpleReportDataBatch> {

    private static final Logger LOG = LoggerFactory.getLogger(ReportProcessor.class);


    @Override
    public void processBatch(final SimpleReportDataBatch eventBatch) {
        LOG.info("Processing nothing");
    }
}
