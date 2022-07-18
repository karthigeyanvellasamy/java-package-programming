package com.javaspec.base.lang.thread;

public class MultiThread extends Thread{
	
	Integer threadNo;

	public MultiThread(Integer threadNo) {
		this.threadNo = threadNo;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Fill place ="+ i + " Thread No : " + this.getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
		
		
	}
	
	
}
