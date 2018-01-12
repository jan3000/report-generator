package de.zalando.backlog.reportgenerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "reporting-store")
public class DataSourceProperties {

    public static final int SHARDS = 16;
    private int shards = SHARDS;
    private String username = "postgres";
    private String password = "zalando";
    private String driver = "org.postgresql.Driver";
    private String jdbcUrlTemplate = "jdbc:postgresql://localhost:5432/reporting_store_%s";

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(final String driver) {
        this.driver = driver;
    }

    public int getShards() {
        return shards;
    }

    public void setShards(final int shards) {
        this.shards = shards;
    }

    public String getJdbcUrlTemplate() {
        return jdbcUrlTemplate;
    }

    public void setJdbcUrlTemplate(final String jdbcUrlTemplate) {
        this.jdbcUrlTemplate = jdbcUrlTemplate;
    }
}
