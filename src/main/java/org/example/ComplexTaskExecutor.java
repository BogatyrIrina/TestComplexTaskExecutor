package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ComplexTaskExecutor {
    private final ExecutorService executorService;
    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numTasks) {
        this.executorService = Executors.newFixedThreadPool(numTasks);
        this.barrier = new CyclicBarrier(numTasks, () -> {
            System.out.println("All tasks completed. Combining results.");
        });
    }

    public void executeTasks(int numTasks) {
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < numTasks; i++) {
            futures.add(executorService.submit(new ComplexTask(i, barrier)));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
