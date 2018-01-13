package de.zalando.backlog.reportgenerator.service;

import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import de.zalando.backlog.reportgenerator.domain.SimpleReportData;
import de.zalando.backlog.reportgenerator.repository.PGQService;
import de.zalando.backlog.reportgenerator.repository.ReportStoreService;

@Service
public class ReportUpdateConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportUpdateConsumerService.class);
    private PGQService pgqService;
    private ReportStoreService reportStoreService;
    private boolean streamingEnabled = true;

    @Autowired
    public ReportUpdateConsumerService(final PGQService pgqService, final ReportStoreService reportStoreService) {
        this.pgqService = pgqService;
        this.reportStoreService = reportStoreService;
    }

    public List<SimpleReportData> processNextBatchIfAvailable(int partitionId) {
        LOG.info("Process next batch on partition {}", partitionId);
        Integer batchId;
        List<SimpleReportData> batch = Lists.newArrayList();
        while ((batchId = pgqService.getNextBatchId(partitionId)) != null && (batch = pgqService.getBatch(partitionId,
                batchId)).isEmpty()) {
            pgqService.finishBatch(partitionId, batchId);
        }
        if (batchId == null) {
            LOG.info("Sleep because batch id is null");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOG.warn("Sleeping thread interrupted", e);
            }
        } else {
            LOG.error("Process batch: " + batch);
            reportStoreService.upsertSimpleReportData(batch);

            pgqService.finishBatch(partitionId, batchId);
            return batch;
        }
        return batch;
    }

}
