package com.javaspec.base.util.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * @author Karthigeyan Vellasamy
 * {@link #ReentrantLock -> #ReadWriteLock -> #StampedLock}
 */
public class SynchronizedLocks {

	//ReentrantLock
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

/**
 * @author Karthigeyan Vellasamy
 * #ReadWriteLock
 */
class ReadWriteLocks {

	public static void main(String[] args) throws InterruptedException {
		ReadWriteLocks instance = new ReadWriteLocks();
		instance.executeLock();
	}
	
	
	public void executeLock() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		
		//ReadWriteLock
		ReadWriteLock lock = new ReentrantReadWriteLock();

		executor.submit(() -> {
		    lock.writeLock().lock();
		    try {
		    	ReadWriteLocks.sleep(1);
		        map.put("key", "value");
		    } finally {
		        lock.writeLock().unlock();
		    }
		});
		
		Runnable readTask = () -> {
		    lock.readLock().lock();
		    try {
		        System.out.println(map.get("key"));
		        sleep(1);
		    } finally {
		        lock.readLock().unlock();
		    }
		};

		//Reads and then wait 3 seconds to read again while write lock goes for sleep mode
		executor.submit(readTask);
		executor.submit(readTask);
		TimeUnit.SECONDS.sleep(3);
		executor.submit(readTask);

		SynchronizedLocks.shutDown(executor);
	}
	
	public static void sleep(int sleepTime){
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

/**
 * @author Karthigeyan Vellasamy
 * #StampedLock
 */
class StampedLocks {

	public static void main(String[] args) throws InterruptedException {
		ReadWriteLocks instance = new ReadWriteLocks();
		instance.executeLock();
	}
	
	
	public void executeLock() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		
		//ReadWriteLock
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
		    long stamped = lock.writeLock();
		    try {
		    	ReadWriteLocks.sleep(1);
		        map.put("Key", "value");
		    } finally {
		        lock.unlockWrite(stamped);
		    }
		});
		
		Runnable readTask = () -> {
			long readStamped = lock.readLock();
		    try {
		        System.out.println(map.get("key"));
		        sleep(1);
		    } finally {
		        lock.unlockRead(readStamped);
		    }
		};

		//Reads and then wait 3 seconds to read again while write lock goes for sleep mode
		executor.submit(readTask);
		executor.submit(readTask);
		TimeUnit.SECONDS.sleep(3);
		executor.submit(readTask);

		SynchronizedLocks.shutDown(executor);
	}
	
	public static void sleep(int sleepTime){
		try {
			TimeUnit.SECONDS.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
