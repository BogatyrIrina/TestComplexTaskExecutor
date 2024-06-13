package org.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class ComplexTask implements Runnable {
    private final int taskId;
    private final CyclicBarrier barrier;

    public ComplexTask(int taskId, CyclicBarrier barrier) {
        this.taskId = taskId;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            // Выполнение сложной задачи
            System.out.println("Task " + taskId + " started on thread " + Thread.currentThread().getName());
            Thread.sleep(1000); // Имитация выполнения задачи
            System.out.println("Task " + taskId + " completed on thread " + Thread.currentThread().getName());

            // Ожидание, пока все задачи будут выполнены
            barrier.await();

            // Объединение результатов
            System.out.println("All tasks completed. Combining results...");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

