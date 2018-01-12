package de.zalando.backlog.reportgenerator.service;

import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import de.zalando.backlog.reportgenerator.repository.PGQService;

@Service
public class ReportUpdateConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportUpdateConsumerService.class);
    private PGQService pgqService;
    private boolean streamingEnabled = true;

    @Autowired
    public ReportUpdateConsumerService(PGQService pgqService) {
        this.pgqService = pgqService;
    }

    public List<String> processNextBatchIfAvailable(int partitionId) {
        LOG.info("Process next batch on partition {}", partitionId);
        Integer batchId;
        List<String> batch = Lists.newArrayList();
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
            pgqService.finishBatch(partitionId, batchId);
            return batch;
        }
        return batch;
    }

}
