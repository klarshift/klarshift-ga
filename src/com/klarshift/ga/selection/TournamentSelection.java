package com.klarshift.ga.selection;


import java.util.ArrayList;
import java.util.Random;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.ChromosomePair;
import com.klarshift.ga.Population;

/**
 * tournament selection
 * @author timo
 *
 */
public class TournamentSelection implements ISelection{
	Random random = new Random();
	
	Double selectionPressure = 0.7;
	
	public TournamentSelection(){
		
	}
	
	public TournamentSelection(double d) {
		selectionPressure = d;
	}
	
	public void setSelectionPressure(Double pressure){
		this.selectionPressure = pressure;
	}
	
	public Double getSelectionPressure(){
		return selectionPressure;
	}

	private Chromosome tournament(Population population){		
		Chromosome best = null;
		int s = (int) Math.round(population.size() * selectionPressure) ;
	
		
		// get population for tournament
		ArrayList<Chromosome> list = (ArrayList<Chromosome>) population.getChromosomes().clone();
		for(int t=0; t<s; t++){
			int o = random.nextInt(list.size());
			Chromosome c = list.get(o);
			if(best == null || c.getFitness() > best.getFitness()){
				best = c;
			}		
			list.remove(c);
		}
		
		return best.clone();
	}
	
	public String toString(){
		return "Tournament " + selectionPressure;
	}

	@Override
	public ChromosomePair select(Population population) {
		Chromosome first = tournament(population);
		Chromosome second = null;
		while(second == null || second.equals(first)){
			second = tournament(population);
		}
		return new ChromosomePair(first, second);					
	}

}
