package com.sds.com;

import java.util.Random;

public class RandomTest {

	public static void main(String[] args) {
		Random random=new Random();
		int n=random.nextInt(5);
		System.out.println(n);
	}

}
