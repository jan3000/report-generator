package de.zalando.backlog.reportgenerator.service;


import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;

public class ThreadTestIT {

    public static Map<Integer, String> shardToLock = Maps.newConcurrentMap();
    public static int shards = 3;

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, Thread> shardToThread = Maps.newHashMap();
        while (true) {
            IntStream.range(1, shards + 1).forEach(shard -> {
                if (shardToLock.get(shard) == null || shardToLock.get(shard).isEmpty()) {
                    // synchronize
                    System.out.println(getThreadName() + ": Acquire lock for shard: " + shard);
                    shardToLock.put(shard, "LOCK_" + shard);
                    Streamer streamer = new Streamer(shard);
                    Thread thread = new Thread(streamer);
                    thread.start();
                    shardToThread.put(shard, thread);

                }
            });
            System.out.println(getThreadName() + ": Main loop ended, sleep");
            Thread.sleep(1000);
            if (new Random(4).nextInt(7) > 2) {
                System.out.println(getThreadName() + ": Will try to kill thread 2");
                shardToThread.get(3).interrupt();
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
                    System.out.println(getThreadName() + ": Streaming shard " + shard + "...");
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
