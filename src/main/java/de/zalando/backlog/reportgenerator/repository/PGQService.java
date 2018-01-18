package de.zalando.backlog.reportgenerator.repository;

import java.util.List;
import javax.sql.DataSource;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import de.zalando.backlog.reportgenerator.domain.ShardedDataSource;
import de.zalando.backlog.reportgenerator.domain.SimpleReportData;

@Repository
public class PGQService {

    private static final Logger LOG = LoggerFactory.getLogger(PGQService.class);

    private static final String EV_DATA = "ev_data";
    private static final String GET_NEXT_BATCH = "SELECT pgq.next_batch('ar_data.q_failed_rules', " +
            "'simple-report-generator')";
    private static final String GET_BATCH = "SELECT ev_id, %s, ev_type FROM pgq.get_batch_events(%s)";
    private static final String FINISH_BATCH = "SELECT pgq.finish_batch(%s)";

    private static final RowMapper<SimpleReportData> STRING_ROW_MAPPER = (rs, rowNum) -> {
        String json = rs.getString(EV_DATA);
        Gson gson = new Gson();
        SimpleReportData simpleReportData = gson.fromJson(json, SimpleReportData.class);
        LOG.info("simpleReportData: {}", simpleReportData);
        return simpleReportData;
    };

    private ShardedDataSource shardedDataSource;

    @Autowired
    public PGQService(final ShardedDataSource shardedDataSource) {
        this.shardedDataSource = shardedDataSource;
    }

    public Integer getNextBatchId(final int shardId) {
        return getJdbcTemplate(shardId).queryForObject(GET_NEXT_BATCH, Integer.class);
    }

    public List<SimpleReportData> getBatch(final int shardId, final int batchId) {
        LOG.info("Get batch with id: {}", batchId);
        return getJdbcTemplate(shardId).query(String.format(GET_BATCH, EV_DATA, batchId), STRING_ROW_MAPPER);
    }

    public void finishBatch(final int shardId, final int batchId) {
        LOG.info("Finish batch with id: {}", batchId);
        getJdbcTemplate(shardId).execute(String.format(FINISH_BATCH, batchId));

    }

    private JdbcTemplate getJdbcTemplate(int shardId) {
        DataSource dataSource = shardedDataSource.getShardToDataSource().get(shardId);
        return new JdbcTemplate(dataSource);
    }
}
