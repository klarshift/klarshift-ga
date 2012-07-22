package com.klarshift.artificial.problem.tsp.mutation;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.problem.tsp.TSPChromosome;

/**
 * tsp reciprocal exchange mutation
 * 
 * swaps two positions randomly
 * 
 * @see 4.3.13. Exchange mutation (EM)
 * 
 * @author timo
 * 
 */
public class ReciprocalExchange extends TSPMutation {
	
	

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		TSPChromosome c = (TSPChromosome) chromosome;
		int i1 = random.nextInt(c.size());
		int i2 = getRandomIndex(i1, c.size(), stdDev);						
		if(i1 != i2){
			// swap
			int b = c.get(i1);
			c.set(i1, c.get(i2));
			c.set(i2, b);
		}
	}
}
