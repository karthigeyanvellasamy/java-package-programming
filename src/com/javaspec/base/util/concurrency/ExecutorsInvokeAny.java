package com.javaspec.base.util.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Karthigeyan Vellasamy
 * {@link ExecutorService invokeAny}
 */
public class ExecutorsInvokeAny {
	
	static Callable<String> callable(String task, long time) {
		return () -> {
			TimeUnit.SECONDS.sleep(time);
			return task;
		};
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newWorkStealingPool();
		
		try {
			List<Callable<String>> tasks  = Arrays.asList(
				callable("task1", 1), callable("task2", 1), callable("task3", 3), callable("task4", 4)
			);
			String result = executor.invokeAny(tasks);
			System.out.println(result);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
