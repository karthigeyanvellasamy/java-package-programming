package com.javaspec.base.util.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author Karthigeyan Vellasamy
 *{@link #Runnable}
 */
public class RunnableThread {

	public static void main(String[] args)
			throws InterruptedException {

		Runnable task = () -> {
			
	        try {
	        	String name =Thread.currentThread().getName();
				System.out.println("Foo " + name);
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Bar " + name);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		};
		Thread child = new Thread(task);
		child.start();

		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println(Thread.currentThread().getName());
		
	}

}
