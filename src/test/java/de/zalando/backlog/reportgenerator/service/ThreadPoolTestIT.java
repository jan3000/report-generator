package de.zalando.backlog.reportgenerator.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;

public class ThreadPoolTestIT {


    public static Map<Integer, String> shardToLock = Maps.newConcurrentMap();
    public static int shards = 3;
    public static ExecutorService executorService = new ForkJoinPool(6);

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, Future> shardToFuture = Maps.newHashMap();
        while (true) {
            IntStream.range(1, shards + 1).forEach(shard -> {
                if (shardToLock.get(shard) == null || shardToLock.get(shard).isEmpty()) {
                    // synchronize
                    System.out.println(getThreadName() + ": Acquire lock for shard: " + shard);
                    shardToLock.put(shard, "LOCK_" + shard);
                    de.zalando.backlog.reportgenerator.service.ThreadTestIT.Streamer streamer = new de.zalando
                            .backlog.reportgenerator.service.ThreadTestIT.Streamer(shard);

                    Future<?> submit = executorService.submit(streamer);
                    shardToFuture.put(shard, submit);
                }
            });
            System.out.println(getThreadName() + ": Main loop ended, sleep");
            Thread.sleep(1000);
            if (new Random(4).nextInt(7) > 2) {
                System.out.println(getThreadName() + ": Will try to kill thread 2");
                boolean cancel = shardToFuture.get(1).cancel(true);
                System.out.println("canceled: " + cancel);
            }

        }
    }

    private static String getThreadName() {
        return Thread.currentThread().getName();
    }

    static class Streamer implements Runnable {

        private int shard;

        public Streamer(final int shard) {
            this.shard = shard;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(getThreadName() + ": Streaming shardy " + shard + "...");
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                System.out.println(getThreadName() + ": I am exception! " + e);
            }
            System.out.println(getThreadName() + ": I am interrupted!");
            System.out.println(getThreadName() + ": Releasing lock now for shard " + shard);
            shardToLock.remove(shard);
        }

    }
}

