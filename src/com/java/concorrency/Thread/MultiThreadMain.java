package com.java.concorrency.thread;

public class MultiThreadMain {
	
	public static void main(String[] args) throws InterruptedException {
		
		for (int i = 0; i < 2; i++) {
			System.out.println("=======Start =======");
			MultiThread instance = new  MultiThread(i);
			instance.start();
			//instance.join();
		}
		
	}

}
