package de.zalando.backlog.reportgenerator.streamer.components;

import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;

public interface Processor<T extends EventBatch> {

    void processBatch(T eventBatch);
}
