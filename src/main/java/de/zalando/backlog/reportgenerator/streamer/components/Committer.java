package de.zalando.backlog.reportgenerator.streamer.components;

import de.zalando.backlog.reportgenerator.streamer.domain.EventBatch;

public interface Committer<T extends EventBatch> {

    void commitBatch(T eventBatch);

}
