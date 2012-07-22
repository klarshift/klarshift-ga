package com.klarshift.artificial.problem.tsp.mutation;

import com.klarshift.artificial.genetic.mutation.IMutation;
import com.klarshift.artificial.util.RandomGaussian;

/**
 * tsp mutation base class
 * @author timo
 *
 */
public abstract class TSPMutation implements IMutation {
	/* random generator */
	protected RandomGaussian random = new RandomGaussian();
	
	protected int getRandomIndex(int mean, int max, double stdDev){
		int i2 = random.nextIndex(mean,  stdDev*max);
		if(i2 > max-1){
			i2 %= max;
		}

		if(i2 < 0){		
			i2 = (i2 % max)*-1;
		}
		
		return i2;
	}
}
