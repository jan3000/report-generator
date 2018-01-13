package de.zalando.backlog.reportgenerator.domain;

import com.google.gson.annotations.SerializedName;

public class RuleMetadata {

    @SerializedName("rule_name")
    private String ruleName;

    @SerializedName("rule_type")
    private String ruleType;

    @SerializedName("reason_code")
    private ReasonCode reasonCode;

    @SerializedName("rule_description")
    private String ruleDescription;

    @SerializedName("rule_context_type")
    private String ruleContextType;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(final String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(final String ruleType) {
        this.ruleType = ruleType;
    }

    public ReasonCode getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(final ReasonCode reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(final String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getRuleContextType() {
        return ruleContextType;
    }

    public void setRuleContextType(final String ruleContextType) {
        this.ruleContextType = ruleContextType;
    }

    @Override
    public String toString() {
        return "RuleMetadata{" +
                "ruleName='" + ruleName + '\'' +
                ", ruleType='" + ruleType + '\'' +
                ", reasonCode=" + reasonCode +
                ", ruleDescription='" + ruleDescription + '\'' +
                ", ruleContextType='" + ruleContextType + '\'' +
                '}';
    }
}
