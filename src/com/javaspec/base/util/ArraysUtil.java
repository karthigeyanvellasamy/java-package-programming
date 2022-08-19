package com.javaspec.base.util;

import java.util.Arrays;

public class ArraysUtil {
	
	public static void main(String[] args) {
		
		int[] arr = {65, 87, 98, 4, 56, 34, 18};
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr) );
	}
	
	public static int[] getIntArray() {
		int[] arr = {65, 87, 98, 4, 56, 34, 18};
		return arr;
	}

}
