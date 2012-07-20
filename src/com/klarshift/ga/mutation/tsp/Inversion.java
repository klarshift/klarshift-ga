package com.klarshift.ga.mutation.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.genome.IndexChromosome;

/**
 * inversion mutation
 * 
 * @see 4.3.16. Inversion mutation (IVM)
 * @author timo
 *
 */
public class Inversion extends TSPMutation {

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		IndexChromosome c = (IndexChromosome)chromosome;
		ArrayList<Integer> newList = new ArrayList<Integer>();
		
		int pos = random.nextInt(c.size()-1);
		int length = Math.min(Math.abs(random.nextIndex(0, stdDev*(c.size()))), c.size()-pos-1); //random.nextInt(c.size()-pos);
						
		if(length > 1){
			newList.addAll( c.getChromosomes().subList(0, pos));
			
			List<Integer> subTour = c.getChromosomes().subList(pos+length, c.getChromosomes().size());
			Collections.reverse(subTour);
			newList.addAll( subTour );
			
			newList.addAll( c.getChromosomes().subList(pos, pos+length));
			
			c.setChromosomes(newList);			
		}
	}		
}
