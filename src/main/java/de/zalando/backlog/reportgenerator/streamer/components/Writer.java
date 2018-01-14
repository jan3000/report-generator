package de.zalando.backlog.reportgenerator.streamer.components;

import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;

public interface Writer<T extends EventBatch> {

    void writeBatch(T eventBatch);
}
