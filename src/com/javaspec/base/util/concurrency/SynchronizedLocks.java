package com.javaspec.base.util.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class SynchronizedLocks {

	ReentrantLock lock = new ReentrantLock();
	static int count = 0;
	// Synchronized
	void increment() {
		synchronized (this) {
			count++;
			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " " + count);
		}

	}
	
	//ReenterLock
	void incrementReenterLock() {
		lock.lock();
		try {
			
			System.out.println(lock.isLocked());
			System.out.println(lock.isHeldByCurrentThread());
			System.out.println(lock.getQueueLength());
			count++;
		} finally {
			System.out.println("Thread : " + Thread.currentThread().getName()
					+ " " + count);
			lock.unlock();
		}

	}

	public static void main(String[] args) {

		SynchronizedLocks main = new SynchronizedLocks();
		main.concurrentAction();

	}

	public void concurrentAction() {
		ExecutorService executor = Executors.newFixedThreadPool(10);

		IntStream.range(0, 10000)
		.forEach(count -> executor.submit(this::increment));
		
		IntStream.range(0, 10000)
				.forEach(count -> executor.submit(this::incrementReenterLock));
		shutDown(executor);

	}

	public static void shutDown(ExecutorService executor) {
		try {
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.err.println("termination interrupted");
		} finally {
			if (!executor.isTerminated()) {
				System.err.println("killing non-finished tasks");
			}
			executor.shutdownNow();
		}
	}
}
