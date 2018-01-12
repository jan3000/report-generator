package de.zalando.backlog.reportgenerator.domain;

import java.util.Date;

public class PGQEvent {

    private Integer id;
    private Date time;
    private Integer txid;
    private Integer owner;
    private Integer retry;
    private String eventType;
    private String eventData;
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(final Date time) {
        this.time = time;
    }

    public Integer getTxid() {
        return txid;
    }

    public void setTxid(final Integer txid) {
        this.txid = txid;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(final Integer owner) {
        this.owner = owner;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(final Integer retry) {
        this.retry = retry;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(final String eventData) {
        this.eventData = eventData;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(final String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(final String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(final String extra3) {
        this.extra3 = extra3;
    }

    public String getExtra4() {
        return extra4;
    }

    public void setExtra4(final String extra4) {
        this.extra4 = extra4;
    }
}
