package com.javaspec.base.util.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableFuture {

	public static void main(String[] args) throws TimeoutException {
		Callable<String> task = () -> {
			TimeUnit.SECONDS.sleep(2);
			System.out.println("Thread : "+ Thread.currentThread().getName());
			return "Task completed";
		};

		try {
			ExecutorService exFixedThread = Executors.newFixedThreadPool(1);
			Future<String> callableResponse = exFixedThread.submit(task);
			String result;
			// exFixedThread.shutdownNow();
			// callableResponse.get(1, TimeUnit.SECONDS);
			result = callableResponse.get();
			System.out.println("Result ? " + callableResponse.isDone());
			System.out.println("Output :" + result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
