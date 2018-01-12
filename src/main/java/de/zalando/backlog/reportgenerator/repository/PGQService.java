package de.zalando.backlog.reportgenerator.repository;

import java.sql.Date;
import java.util.List;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import de.zalando.backlog.reportgenerator.domain.ShardedDataSource;

@Service
public class PGQService {

    private static final Logger LOG = LoggerFactory.getLogger(PGQService.class);
    private static final String GET_NEXT_BATCH = "SELECT pgq.next_batch('ar_data.q_failed_rules', " +
            "'simple-report-generator')";
    private static final String GET_BATCH = "SELECT pgq.get_batch_events(%s)";
    private static final String FINISH_BATCH = "SELECT pgq.finish_batch(%s)";

    private ShardedDataSource shardedDataSource;

    @Autowired
    public PGQService(final ShardedDataSource shardedDataSource) {
        this.shardedDataSource = shardedDataSource;
    }

    public Integer getNextBatchId(final int shardId) {
        LOG.info("Get next batch id");
        Integer batchId = getJdbcTemplate(shardId).queryForObject(GET_NEXT_BATCH, Integer.class);
        LOG.info("Next batch id: {}", batchId);
        return batchId;
    }

    public List<String> getBatch(final int shardId, final int batchId) {
        LOG.info("Get batch for id: {}", batchId);
        return getJdbcTemplate(shardId).queryForList(String.format(GET_BATCH, batchId), String.class);

    }

    public void finishBatch(final int shardId, final int batchId) {
        LOG.info("Finish batch id: {}", batchId);
        getJdbcTemplate(shardId).execute(String.format(FINISH_BATCH, batchId));

    }

    private JdbcTemplate getJdbcTemplate(int shardId) {
        DataSource dataSource = shardedDataSource.getShardToDataSource().get(shardId);
        return new JdbcTemplate(dataSource);
    }

    public class Batch {

        private int idx;
        private Date date;
        private int id;
        private String json;

        public int getIdx() {
            return idx;
        }

        public void setIdx(final int idx) {
            this.idx = idx;
        }

        public String getJson() {
            return json;
        }

        public void setJson(final String json) {
            this.json = json;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(final Date date) {
            this.date = date;
        }
    }
}
