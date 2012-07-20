package com.klarshift.ga.mutation.tsp;

import java.util.ArrayList;
import java.util.List;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.genome.IndexChromosome;

/**
 * inversion mutation
 * 
 * @see 4.3.15. Simple inversion mutation (SIM)
 * @author timo
 *
 */
public class Displacement extends TSPMutation {

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		IndexChromosome c = (IndexChromosome)chromosome;
		ArrayList<Integer> newList = new ArrayList<Integer>();
		
		int pos = random.nextInt(c.size()-1);
		int length = Math.min(Math.abs(random.nextIndex(0, stdDev*(c.size()))), c.size()-pos-1); //random.nextInt(c.size()-pos);
						
		if(length > 1){
			newList.addAll( c.getChromosomes().subList(0, pos));
			newList.addAll( c.getChromosomes().subList(pos+length, c.getChromosomes().size()));
			newList.addAll( c.getChromosomes().subList(pos, pos+length));
			
			c.setChromosomes(newList);			
		}
	}		
}
