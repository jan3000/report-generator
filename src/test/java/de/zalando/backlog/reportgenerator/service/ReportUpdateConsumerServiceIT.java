package de.zalando.backlog.reportgenerator.service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


import de.zalando.backlog.reportgenerator.domain.ShardedDataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportUpdateConsumerServiceIT {

    private static final String NEXT_BATCH = "SELECT pgq.next_batch('ar_data.q_failed_rules', " +
            "'simple-report-generator')";

    private static final String UPDATE_STATEMENT = "UPDATE ar_data.offer SET o_updated_at = now() WHERE o_id = 189676653";

    @Autowired
    private ShardedDataSource shardedDataSource;
    @Autowired
    private ReportUpdateConsumerService reportUpdateConsumerService;

//    @Test
//    public void noEventPushedToQueueOneShard() throws SQLException, InterruptedException {
//        // given
//        int shardId = 1;
//        cleanQueue(shardId);
//
//        // when
//        List<String> batch = reportUpdateConsumerService.processNextBatchIfAvailable(shardId);
//
//        // then
//        assertThat(batch).isEmpty();
//    }

    @Test
    public void singleEventPushedToQueueAndConsumedOneShard() throws SQLException, InterruptedException {
        // given
        int shardId = 1;
        cleanQueue(shardId);
        insertEventToQueue(shardId);
        Thread.sleep(1000);

//        // when
//        List<String> batch = reportUpdateConsumerService.processNextBatchIfAvailable(shardId);
//
//        // then
//        assertThat(batch).isNotEmpty();
//        System.out.println("batch " + batch);
//
//
//        // when
//        batch = reportUpdateConsumerService.processNextBatchIfAvailable(shardId);
//
//        // then
//        assertThat(batch).isEmpty();
    }

//    @Test
//    public void singleEventPushedToQueueAndConsumedMultipleShards() throws SQLException, InterruptedException {
//        // given
//        IntStream.range(1, 17).forEach(shardId -> {
//            cleanQueue(shardId);
//            insertEventToQueue(shardId);
//        });
//
//        // when
//        Thread.sleep(1000);
//        IntStream.range(1, 17).forEach(shardId -> {
//            try {
//                List<String> batch = reportUpdateConsumerService.processNextBatchIfAvailable(shardId);
//                assertThat(batch).isNotEmpty();
//            } catch (InterruptedException e) {
//                assertThat(true).isFalse();
//            }
//        });
//    }
//

    private void cleanQueue(final int shardId) {
        Integer batchId;
        try {
            while ((batchId = getNextBatchId(shardId)) != null) {
                System.out.println("Test Finish batch id = " + batchId);
                getJdbcTemplate(shardId).execute(String.format("SELECT pgq.finish_batch(%s)", batchId));
            }
        } catch (InterruptedException e) {
            assertThat(Boolean.FALSE).isTrue();
        }
    }


    private Integer getNextBatchId(int shardId) throws InterruptedException {
        return getJdbcTemplate(shardId).queryForObject(NEXT_BATCH, Integer.class);
    }

    private void insertEventToQueue(final int shardId) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate(shardId);
//        jdbcTemplate.execute(INSERT_FIRST);
//        jdbcTemplate.execute(INSERT_STATEMENT);
//        jdbcTemplate.execute(UPDATE_STATEMENT);

        String sqlFileAsString = null;
        try {
            InputStream resourceAsStream = this.getClass().getResourceAsStream("/report-store.sql");
            sqlFileAsString = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] sqlStatements = sqlFileAsString.split(";");
        Lists.newArrayList(sqlStatements).forEach(sql -> {
//            System.out.println("save " + sql);
            jdbcTemplate.execute(sql);
        });
        jdbcTemplate.execute(UPDATE_STATEMENT);

    }

    private JdbcTemplate getJdbcTemplate(final int shardId) {
        return new JdbcTemplate(shardedDataSource.getShardToDataSource().get(shardId));
    }
}
