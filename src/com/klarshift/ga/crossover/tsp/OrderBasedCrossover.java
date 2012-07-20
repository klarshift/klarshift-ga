package com.klarshift.ga.crossover.tsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.ChromosomePair;
import com.klarshift.ga.crossover.ICrossover;
import com.klarshift.ga.genome.IndexChromosome;

public class OrderBasedCrossover implements ICrossover {
	Random random = new Random();
	
	private ArrayList<Integer> createList(IndexChromosome c, int pos, int length){
		ArrayList<Integer> list = new ArrayList<Integer>();
		List<Integer> subList = c.getChromosomes().subList(pos, pos+length);
	
		for(int i=0; i<c.size(); i++){
			if(i >= pos && i < pos+length){
				list.add(subList.get(i-pos));				
			}else{
				list.add(-1);
			}
		}
		return list;
	}

	@Override
	public List<Chromosome> crossover(ChromosomePair pair) {
		ArrayList<Chromosome> list = new ArrayList<Chromosome>();

		// position based crossover
		ChromosomePair clonedPair = pair.clone();
		
		//System.out.println(clonedPair);
		
		IndexChromosome first = (IndexChromosome) clonedPair.getFirst();
		IndexChromosome second = (IndexChromosome) clonedPair.getSecond();
		
		int size = first.size();
		int length = Math.min(size, 3);
		int pos = random.nextInt(size-1-length);
		
		
		ArrayList<Integer> off1 = createList(first, pos, length);
		ArrayList<Integer> off2 = createList(second, pos, length);
		
		// fill
		for(int i=pos+length; i<size; i++){
			
			// fill first offspring
			int j = i;
			while(true){				
				int y = j%size;				
				Integer v = second.getChromosomes().get(y);								
				if(!off1.contains(v)){				
					off1.set(i, v);
					break;
				}
				j++;
			}
			
			// fill first offspring
			j = i;
			while(true){				
				int y = j%size;				
				Integer v = first.getChromosomes().get(y);								
				if(!off2.contains(v)){				
					off2.set(i, v);
					break;
				}
				j++;
			}
		}
		
		// fill
		for(int i=0; i<pos; i++){
			
			// fill first offspring
			int j = i;
			while(true){				
				int y = j%size;				
				Integer v = second.getChromosomes().get(y);								
				if(!off1.contains(v)){				
					off1.set(i, v);
					break;
				}
				j++;
			}
			
			// fill second offspring
			j = i;
			while(true){				
				int y = j%size;				
				Integer v = first.getChromosomes().get(y);								
				if(!off2.contains(v)){				
					off2.set(i, v);
					break;
				}
				j++;
			}
		}
		
	
		
		
		
		
		
		first.setChromosomes(off1);
		second.setChromosomes(off2);
		
		

		list.add(first);
		list.add(second);
		
		return list;
	}

	
	

}
