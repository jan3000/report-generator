package de.zalando.backlog.reportgenerator.domain;

import com.google.gson.annotations.SerializedName;

public class ReasonCode {

    @SerializedName("block_reason_code")
    private String blockReasonCode;

    @SerializedName("block_reason_description")
    private String blockReasonDescription;

    public String getBlockReasonCode() {
        return blockReasonCode;
    }

    public void setBlockReasonCode(final String blockReasonCode) {
        this.blockReasonCode = blockReasonCode;
    }

    public String getBlockReasonDescription() {
        return blockReasonDescription;
    }

    public void setBlockReasonDescription(final String blockReasonDescription) {
        this.blockReasonDescription = blockReasonDescription;
    }

    @Override
    public String toString() {
        return "ReasonCode{" +
                "blockReasonCode='" + blockReasonCode + '\'' +
                ", blockReasonDescription='" + blockReasonDescription + '\'' +
                '}';
    }
}
