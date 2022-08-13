package com.javaspec.base.util.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * @author Karthigeyan Vellasamy
 * {@link #CountDownLatch}
 */
public class CountDownLatchDemo {

	public static void main(String[] args)  {

		CountDownLatch latch = new CountDownLatch(3);

		Worker first = new Worker(1000, latch, "WORKER-1");
		Worker second = new Worker(2000, latch, "WORKER-2");
		Worker third = new Worker(3000, latch, "WORKER-3");

		Thread threadOne = new Thread(first);
		Thread threadtwo = new Thread(second);
		Thread threadthree = new Thread(third);

		threadOne.start();
		threadtwo.start();
		threadthree.start();

		try {
			System.out.println("Started threads");
			latch.await();
			System.out.println("End Task");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class Worker implements Runnable {

	private int delay;
	private CountDownLatch latch;
	private String name;

	public Worker(int delay, CountDownLatch latch, String name) {
		super();
		this.delay = delay;
		this.latch = latch;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(delay);
			latch.countDown();
			System.out.println("Thread : "+ name );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}