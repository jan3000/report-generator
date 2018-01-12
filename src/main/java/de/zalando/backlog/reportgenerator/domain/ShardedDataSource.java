package de.zalando.backlog.reportgenerator.domain;

import java.util.Map;
import javax.sql.DataSource;


public class ShardedDataSource {

    private Map<Integer, DataSource> shardToDataSource;

    public ShardedDataSource(final Map<Integer, DataSource> shardToDataSource) {
        this.shardToDataSource = shardToDataSource;
    }

    public Map<Integer, DataSource> getShardToDataSource() {
        return shardToDataSource;
    }

}
