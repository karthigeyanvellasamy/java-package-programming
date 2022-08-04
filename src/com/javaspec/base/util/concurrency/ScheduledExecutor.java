package com.javaspec.base.util.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

	public static void main(String[] args)
			throws InterruptedException, ExecutionException {

		ScheduledExecutorService executorService = Executors
				.newScheduledThreadPool(2);

		// Runnable
		Runnable task = () -> {
			System.out.println(
					Thread.currentThread().getName() + " Runnable Task");
		};
		ScheduledFuture<?> runnableWaitingTime = executorService.schedule(task,
				1, TimeUnit.SECONDS);

		System.out.println("Wait time for  " + runnableWaitingTime + " is "
				+ runnableWaitingTime.getDelay(TimeUnit.MILLISECONDS));

		// Callable
		Callable<String> callableTask = () -> Thread.currentThread().getName()
				+ " Callable Task";
		ScheduledFuture<String> callableScheduled = executorService
				.schedule(callableTask, 2, TimeUnit.SECONDS);
		System.out.println("Wait time from " + ProcessHandle.current().pid()
				+ " is " + callableScheduled.getDelay(TimeUnit.MILLISECONDS));
		System.out.println(callableScheduled.get());

		// ScheduledExecutor Fixed
		Runnable fixedTask = () -> {
			System.out
					.println(Thread.currentThread().getName() + " Fixed Task");
		};

		ScheduledExecutorService fixedExecutorService = Executors
				.newScheduledThreadPool(1);

		fixedExecutorService.scheduleAtFixedRate(fixedTask, 2, 4,
				TimeUnit.SECONDS);

		// ScheduledExecutor delay
		Runnable DelayedTask = () -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println(
						Thread.currentThread().getName() + " Delayed Task"
								+ " Scheduling: " + System.nanoTime());
			} catch (InterruptedException e) {
				System.err.println("task interrupted");
			}
		};

		ScheduledExecutorService delayedExecutorService = Executors
				.newScheduledThreadPool(1);

		delayedExecutorService.scheduleWithFixedDelay(DelayedTask, 0, 1,
				TimeUnit.SECONDS);

	}
}
