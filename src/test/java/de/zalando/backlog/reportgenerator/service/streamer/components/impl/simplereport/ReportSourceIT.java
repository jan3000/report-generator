package de.zalando.backlog.reportgenerator.service.streamer.components.impl.simplereport;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;


import de.zalando.backlog.reportgenerator.domain.ShardedDataSource;
import de.zalando.backlog.reportgenerator.domain.SimpleReportDataBatch;
import de.zalando.backlog.reportgenerator.repository.PGQService;
import de.zalando.backlog.reportgenerator.streamer.impl.simplereport.ReportSource;

@SpringBootTest
@Ignore("WIP")
public class ReportSourceIT {

    @Autowired
    private PGQService pgqService;
    @Autowired
    private ShardedDataSource shardedDataSource;

    private static final String GET_NEXT_BATCH = "SELECT pgq.next_batch('ar_data.q_failed_rules', " +
            "'simple-report-generator')";

    public void setUp() {
        int shardId = 1;
        cleanQueue(shardId);
        cleanTables();
    }

    public void test() {
        // given
        int shardId = 1;
        insertEventToQueue(shardId);

        // when
        ReportSource reportSource = new ReportSource(pgqService);
        SimpleReportDataBatch batch = reportSource.getBatch(1);

        // then
        assertThat(batch.getEventBatch()).isNotEmpty();
    }

    private void cleanQueue(final int shardId) {
        Integer batchId;
        try {
            while ((batchId = getNextBatchId(shardId)) != null) {
                System.out.println("TESTY CleanQueue: Finish batch id = " + batchId);
                getJdbcTemplate(shardId).execute(String.format("SELECT pgq.finish_batch(%s)", batchId));
            }
        } catch (InterruptedException e) {
            assertThat(Boolean.FALSE).isTrue();
        }
    }

    public void cleanTables() {
        executeBatchCommandsFromFile(1, "/clean_table.sql");
    }


    private Integer getNextBatchId(int shardId) throws InterruptedException {
        return getJdbcTemplate(shardId).queryForObject(GET_NEXT_BATCH, Integer.class);
    }

    private void insertEventToQueue(final int shardId) {
        executeBatchCommandsFromFile(shardId, "/report_store.sql");
//        JdbcTemplate jdbcTemplate = getJdbcTemplate(shardId);
//        jdbcTemplate.execute(String.format(UPDATE_STATEMENT, "189676148"));
//        jdbcTemplate.execute(String.format(UPDATE_STATEMENT, "189676653"));
//        jdbcTemplate.execute(String.format(UPDATE_STATEMENT, "184190330"));
//        jdbcTemplate.execute(String.format(UPDATE_STATEMENT, "184190839"));
    }

    private void executeBatchCommandsFromFile(final int shardId, final String fileName) {
        String sqlFileAsString = null;
        try {
            InputStream resourceAsStream = this.getClass().getResourceAsStream(fileName);
            sqlFileAsString = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] sqlStatements = sqlFileAsString.split(";");
        Lists.newArrayList(sqlStatements).forEach(getJdbcTemplate(shardId)::execute);
    }


    private JdbcTemplate getJdbcTemplate(final int shardId) {
        return new JdbcTemplate(shardedDataSource.getShardToDataSource().get(shardId));
    }
}
