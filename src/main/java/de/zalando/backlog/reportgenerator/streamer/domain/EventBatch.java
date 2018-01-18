package de.zalando.backlog.reportgenerator.streamer.domain;

import java.util.List;

import com.google.common.collect.Lists;

public class EventBatch<T extends Event> {

    private List<T> eventBatch;

    public EventBatch(final List<T> eventBatch) {
        this.eventBatch = eventBatch;
    }

    public List<T> getEventBatch() {
        return Lists.newArrayList(eventBatch);
    }

    @Override
    public String toString() {
        return "EventBatch{" +
                "eventBatch=" + eventBatch +
                '}';
    }
}
