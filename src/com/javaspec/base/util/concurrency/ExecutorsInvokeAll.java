package com.javaspec.base.util.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsInvokeAll {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> tasks = Arrays.asList(() -> {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Thread : " + Thread.currentThread().getName());
			return "Task1 completed";
		}, () -> {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Thread : " + Thread.currentThread().getName());
			return "Task2 completed";
		}, () -> {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Thread : " + Thread.currentThread().getName());
			return "Task3 completed";
		}, () -> {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Thread : " + Thread.currentThread().getName());
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
