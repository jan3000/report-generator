package de.zalando.backlog.reportgenerator.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SimpleReportData {


    @SerializedName("rp_product_id")
    private String productId;

    @SerializedName("rp_sales_channel_id")
    private String salesChannelId;

    @SerializedName("m_merchant")
    private String merchantName;

    @SerializedName("tag")
    private String tag;

    @SerializedName("season")
    private String season;

    @SerializedName("status")
    private boolean status;

    @SerializedName("rpm_eid")
    private String rpmEid;

    @SerializedName("o_status")
    private String offerStatus;

    @SerializedName("config_id")
    private String configId;

    @SerializedName("brand_code")
    private String brandCode;

    @SerializedName("commodity_group")
    private String commodityGroup;

    @SerializedName("target_group_set")
    private String targetGroupSet;

    @SerializedName("stock_array_quantity")
    private List<Stock> stocks;

    @SerializedName("o_failed_rules")
    private List<FailureRule> failureRules;

    public String getTag() {
        return tag;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(final String season) {
        this.season = season;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public String getRpmEid() {
        return rpmEid;
    }

    public void setRpmEid(final String rpmEid) {
        this.rpmEid = rpmEid;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(final String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(final String configId) {
        this.configId = configId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(final String brandCode) {
        this.brandCode = brandCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(final String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public List<FailureRule> getFailureRules() {
        return failureRules;
    }

    public void setFailureRules(final List<FailureRule> failureRules) {
        this.failureRules = failureRules;
    }

    public String getCommodityGroup() {
        return commodityGroup;
    }

    public void setCommodityGroup(final String commodityGroup) {
        this.commodityGroup = commodityGroup;
    }

    public String getTargetGroupSet() {
        return targetGroupSet;
    }

    public void setTargetGroupSet(final String targetGroupSet) {
        this.targetGroupSet = targetGroupSet;
    }

    public String getSalesChannelId() {
        return salesChannelId;
    }

    public void setSalesChannelId(final String salesChannelId) {
        this.salesChannelId = salesChannelId;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(final List<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "SimpleReportData{" +
                "tag='" + tag + '\'' +
                ", season='" + season + '\'' +
                ", status=" + status +
                ", rpmEid='" + rpmEid + '\'' +
                ", offerStatus='" + offerStatus + '\'' +
                ", configId='" + configId + '\'' +
                ", brandCode='" + brandCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", productId='" + productId + '\'' +
                ", failureRules=" + failureRules +
                ", commodityGroup='" + commodityGroup + '\'' +
                ", targetGroupSet='" + targetGroupSet + '\'' +
                ", salesChannelId='" + salesChannelId + '\'' +
                ", stocks=" + stocks +
                '}';
    }
}
