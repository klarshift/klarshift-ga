package com.klarshift.ga.mutation.tsp;

import java.util.ArrayList;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.genome.IndexChromosome;

/**
 * inversion mutation
 * 
 * @see 4.3.15. Simple inversion mutation (SIM)
 * @author timo
 *
 */
public class SimpleInversion extends TSPMutation {

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		IndexChromosome c = (IndexChromosome)chromosome;
		int pos = random.nextInt(c.size()-1);
		int length = Math.min(Math.abs(random.nextIndex(0, stdDev*(c.size()))), c.size()); //random.nextInt(c.size()-pos);
		if(length > 0){
			invert(c, pos, length);
		}
	}
	
	private void invert(IndexChromosome c, int pos, int length){
		//System.out.println("INV " + pos + "/ l=" + length);
		
		ArrayList<Integer> data = c.getChromosomes();
		for(int i = pos; i < pos+(length/2); i++){
			
			int x = i % c.size();
			int y = (data.size() - x - 1) % c.size();
			
			
			
		    int temp = data.get(x);
		    data.set(x, data.get(y));		    
		    data.set(y, temp);
		}
	}
	

}
