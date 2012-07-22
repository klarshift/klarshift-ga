package com.klarshift.artificial.problem.tsp.mutation;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.problem.tsp.TSPChromosome;

/**
 * tsp insertion mutation
 * 
 * removes a random position and adds it at another random position
 * 
 * @see 4.3.14. Insertion mutation (ISM)
 * 
 * @author timo
 * 
 */
public class Insertion extends TSPMutation {

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		TSPChromosome c = (TSPChromosome) chromosome;
		int pos1 = random.nextInt(c.size());
		int pos2 = getRandomIndex(pos1, c.size(), stdDev);

		Integer r = c.getChromosomes().remove(pos1);
		c.getChromosomes().add(pos2, r);
	}

}
