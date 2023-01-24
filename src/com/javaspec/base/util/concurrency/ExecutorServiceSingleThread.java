package com.javaspec.base.util.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Karthigeyan Vellasamy
 *         #Executors
 */
public class ExecutorServiceSingleThread {

	public static void main(String[] args) {

		// Executor Service Single Thread
		ExecutorService singleThread = Executors.newSingleThreadExecutor();
		singleThread.submit(() -> {
			String taskName = Thread.currentThread().getName();
			System.out.println("Single thread executor task Name: " + taskName);
		});

		try {
			System.out.println("Shutdowning Executor Service...");
			singleThread.shutdown();
			singleThread.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("task interrupted");
		} finally {
			if (!singleThread.isTerminated()) {
				System.out.println("cancel all tasks");
			}
			singleThread.shutdownNow();
			System.out.println("Shutdown the executor thread");
		}

	}

}
