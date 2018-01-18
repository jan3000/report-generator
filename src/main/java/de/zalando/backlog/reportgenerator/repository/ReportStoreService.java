package de.zalando.backlog.reportgenerator.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import de.zalando.backlog.reportgenerator.domain.ShardedDataSource;
import de.zalando.backlog.reportgenerator.domain.SimpleReportData;

@Repository
public class ReportStoreService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportStoreService.class);

    private static final Gson GSON = new Gson();
    private static final String UPSERT_SIMPLE_REPORT_DATA = "SELECT ar_report.upsert_simple_report_data('%s')";

    private ShardedDataSource shardedDataSource;

    @Autowired
    public ReportStoreService(final ShardedDataSource shardedDataSource) {
        this.shardedDataSource = shardedDataSource;
    }

    public void upsertSimpleReportData(List<SimpleReportData> simpleReportDataJson) {
        LOG.info("Saving {} simpleReportData event(s)", simpleReportDataJson.size());
        try {
            String json = GSON.toJson(simpleReportDataJson);
            LOG.info("Saving simpleReportData: {}", json);
            getConnection(1).createStatement().executeQuery(String.format(UPSERT_SIMPLE_REPORT_DATA, json));
        } catch (SQLException e) {
            LOG.error("Failed to upsert simpleReportData", e);
        }
    }

    private Connection getConnection(final int shardId) throws SQLException {
        return shardedDataSource.getShardToDataSource().get(shardId).getConnection();
    }
}
