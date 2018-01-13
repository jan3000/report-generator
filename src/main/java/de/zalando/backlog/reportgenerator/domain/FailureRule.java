package de.zalando.backlog.reportgenerator.domain;

import com.google.gson.annotations.SerializedName;

public class FailureRule {

    @SerializedName("details")
    private String details;

    @SerializedName("rule_id")
    private String ruleId;

    @SerializedName("rule_metadata")
    private RuleMetadata ruleMetadata;

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(final String ruleId) {
        this.ruleId = ruleId;
    }

    public RuleMetadata getRuleMetadata() {
        return ruleMetadata;
    }

    public void setRuleMetadata(final RuleMetadata ruleMetadata) {
        this.ruleMetadata = ruleMetadata;
    }

    @Override
    public String toString() {
        return "FailureRule{" +
                "details='" + details + '\'' +
                ", ruleId='" + ruleId + '\'' +
                ", ruleMetadata=" + ruleMetadata +
                '}';
    }
}
