package com.klarshift.ga.mutation.tsp;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.genome.IndexChromosome;

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
		IndexChromosome c = (IndexChromosome) chromosome;
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
