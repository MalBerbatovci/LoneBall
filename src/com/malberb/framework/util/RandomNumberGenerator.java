//public and static means they can be accessed be other classes within
//framework without instantiating a new instance of the object
//i.e System.out.println(RandomNumberGenerator.get.....)

package com.malberb.framework.util;

//class that creates a single static Random object that will be shared across the
//entire application
import java.util.Random;

public class RandomNumberGenerator {

	private static Random rand = new Random();

	public static int getRandIntBetween(int lowerBound, int upperBound) {
		return rand.nextInt(upperBound - lowerBound) + lowerBound;
	}

	public static int getRandInt(int upperBound) {
		return rand.nextInt(upperBound);
	}

}
