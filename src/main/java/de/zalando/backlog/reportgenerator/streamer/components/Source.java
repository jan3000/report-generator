package de.zalando.backlog.reportgenerator.streamer.components;

import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;

public interface Source<T extends EventBatch> {

    T getBatch(int partitionId);
}
