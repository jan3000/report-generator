package de.zalando.backlog.reportgenerator.streamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import de.zalando.backlog.reportgenerator.streamer.domain.StreamerType;
import de.zalando.backlog.reportgenerator.streamer.impl.simplereport.ReportCommitter;
import de.zalando.backlog.reportgenerator.streamer.impl.simplereport.ReportProcessor;
import de.zalando.backlog.reportgenerator.streamer.impl.simplereport.ReportSource;
import de.zalando.backlog.reportgenerator.streamer.impl.simplereport.ReportWriter;
import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;
import de.zalando.backlog.reportgenerator.repository.PGQService;
import de.zalando.backlog.reportgenerator.repository.ReportStoreService;

@Service
public class StreamerFactory {

    private PGQService pgqService;
    private ReportStoreService reportStoreService;

    @Autowired
    public StreamerFactory(final PGQService pgqService, final ReportStoreService reportStoreService) {
        this.pgqService = pgqService;
        this.reportStoreService = reportStoreService;
    }

    public synchronized Streamer<SimpleReportDataBatch> getStreamer(StreamerType streamerType) {
        if (streamerType.equals(StreamerType.SIMPLE_REPORT)) {
            return new Streamer<>(new ReportSource(pgqService), new ReportProcessor(), new ReportWriter
                    (reportStoreService), new ReportCommitter(pgqService));
        }
        return null;
    }
}
