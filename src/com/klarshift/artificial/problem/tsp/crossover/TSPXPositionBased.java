package com.klarshift.artificial.problem.tsp.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.genetic.ChromosomePair;
import com.klarshift.artificial.genetic.crossover.ICrossover;
import com.klarshift.artificial.problem.tsp.TSPChromosome;

public class TSPXPositionBased implements ICrossover {
	Random random = new Random();

	@Override
	public List<Chromosome> crossover(ChromosomePair pair) {
		ArrayList<Chromosome> list = new ArrayList<Chromosome>();

		// position based crossover
		ChromosomePair clonedPair = pair.clone();
		int size = ((TSPChromosome) pair.getFirst()).size();
		
		// swap
		swap(random.nextInt(size), clonedPair);
		

		list.add(clonedPair.getFirst());
		list.add(clonedPair.getSecond());
		
		return list;
	}
	
	private void swap(int p, ChromosomePair pair){
		TSPChromosome c1 = (TSPChromosome) pair.getFirst();
		TSPChromosome c2 = (TSPChromosome) pair.getSecond();
		Integer city1 = c1.get(p);
		Integer city2 = c2.get(p);
		
		for(int i=0; i<c2.size(); i++){
			if(c2.get(i).equals(city1)){
				c2.set(i, city2);
			}
		}		
		c2.set(p, city1);
		
		for(int i=0; i<c1.size(); i++){
			if(c1.get(i).equals(city2)){
				c1.set(i, city1);
			}
		}
		
		c1.set(p, city2);
		
		
		
	}

}
