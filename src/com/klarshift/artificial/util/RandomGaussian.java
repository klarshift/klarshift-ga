package com.klarshift.artificial.util;

import java.util.Random;

/**
 * random gaussian helper class
 * 
 * @author timo
 *
 */
public class RandomGaussian extends Random {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7526279159224608034L;

	public RandomGaussian(){
		
	}
	
	public int nextIndex(int mean, double standardDeviation){
		
		int i = (int) Math.floor(nextGaussian(mean, standardDeviation));
		return i;
	}
	
	public Double nextGaussian(double mean, double standardDeviation){
		return (0.5*(nextGaussian()+1))  * standardDeviation + mean;
	}
	
	public static void main(String[] args){
		RandomGaussian rg = new RandomGaussian();
		for(int c=0; c<100; c++){
			System.out.println(rg.nextIndex(5, 5 * (1-c/100.0)));
		}
	}
}
