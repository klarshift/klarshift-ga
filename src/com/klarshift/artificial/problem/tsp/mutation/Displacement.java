package com.klarshift.artificial.problem.tsp.mutation;

import java.util.ArrayList;
import java.util.List;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.problem.tsp.TSPChromosome;

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
		TSPChromosome c = (TSPChromosome)chromosome;
		ArrayList<Integer> newList = new ArrayList<Integer>();
				
		int pos = random.nextInt(c.size()-1);
		
		int pos2 = getRandomIndex(pos, c.size(), stdDev);
		
		
		int p = Math.min(pos,  pos2);
		int p2 = Math.max(pos,  pos2);
		
		int length = Math.abs(p2-p);
		
	
		if(length > 0){
			newList.addAll( c.getChromosomes().subList(0, p));
			newList.addAll( c.getChromosomes().subList(p+length, c.size()));
			newList.addAll( c.getChromosomes().subList(p, p+length));
			
			c.setChromosomes(newList);			
		}
	}		
}
