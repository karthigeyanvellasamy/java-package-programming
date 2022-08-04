package com.javaspec.base.util.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Karthigeyan Vellasamy
 * {@link ExecutorService invokeAll}
 */
public class ExecutorsInvokeAll {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> tasks = Arrays.asList(() -> {
			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " Process :" + ProcessHandle.current().pid());
			TimeUnit.SECONDS.sleep(2);
			return "Task1 completed";
		}, () -> {
			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " Process :" + ProcessHandle.current().pid());
			TimeUnit.SECONDS.sleep(1);
			return "Task2 completed";
		}, () -> {
			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " Process :" + ProcessHandle.current().pid());
			TimeUnit.SECONDS.sleep(3);
			return "Task3 completed";
		}, () -> {
			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " Process :" + ProcessHandle.current().pid());
			TimeUnit.SECONDS.sleep(1);
			return "Task4 completed";
		});
		
	    //List<Callable<String>> tasks = Arrays.asList(() -> "task1 Done",
		//	() -> "task2 Done", () -> "task3 Done", () -> "task4 Done");

		executor.invokeAll(tasks).stream().map(task -> {
			try {
				return task.get();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}).forEach(System.out::println);

	}
}
