package de.zalando.backlog.reportgenerator.streamer;

import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartitionElector {

    private static final Logger LOG = LoggerFactory.getLogger(PartitionElector.class);
    private final Map<Integer, Future> partitionIdToFuture = Maps.newConcurrentMap();

    public synchronized OptionalInt getPartitionLock(final int numberOfPartitions) {
        LOG.info("Trying to get a lock");
        OptionalInt optionalInt = IntStream.range(1, numberOfPartitions + 1)
                .filter(partitionId -> !partitionIdToFuture.containsKey(partitionId))
                .findFirst();

        if (optionalInt.isPresent()) {
            int key = optionalInt.getAsInt();
            LOG.info("Got lock: {}", key);
//            partitionIdToFuture.put(key, ); // or write some lock before starting the Streamer
        }
        return optionalInt;
    }

    public synchronized void setFuture(int key, Future future) {
        LOG.info("Set future for key: {}", key);
        partitionIdToFuture.put(key, future);
    }


    public synchronized void handleFinishedStreamers() {
        partitionIdToFuture.keySet().forEach(key -> {
            Future future = partitionIdToFuture.get(key);
            if (future.isDone() || future.isCancelled()) {
                LOG.warn("Thread of partition {} is done / cancelled. Remove from locking map", key);
                partitionIdToFuture.remove(key);
            }
        });
    }
}
