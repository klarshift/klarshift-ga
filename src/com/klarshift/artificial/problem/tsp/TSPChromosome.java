package com.klarshift.artificial.problem.tsp;

import java.util.ArrayList;
import java.util.Random;

import com.klarshift.artificial.genetic.Chromosome;

/**
 * genome 
 * @author timo
 *
 */
public class TSPChromosome extends Chromosome {
	private ArrayList<Integer> genes = new ArrayList<Integer>();
	
	
	/**
	 * create a genome with double values
	 * and a given size
	 * @param size
	 */
	public TSPChromosome(int size) {	
		for(int g=0; g<size; g++){
			genes.add(new Integer(-1));
		}
		init();
	}
	
	public TSPChromosome(TSPChromosome other) {	
		genes.clear();
		for(int g=0; g<other.getChromosomes().size(); g++){
			genes.add(new Integer(other.get(g)));
		}
		init();		
	}
	
	
	
	public final ArrayList<Integer> getChromosomes(){
		return genes;
	}
	
	/**
	 * string representation
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(getId() + " [ ");
		sb.append("f=" + getFitness());
		sb.append(" ] // ");
		for(int g=0; g<genes.size(); g++){
			sb.append(" " + genes.get(g) + ",");
		}
		return sb.toString();
	}

	public Integer get(int i) {
		return genes.get(i);
	}

	public int size() {
		return genes.size();
	}

	public void set(int c, int d) {
		genes.set(c, d);
	}
	
	@Override
	public Chromosome clone() {
		return (Chromosome) new TSPChromosome(this);
	}

	public void setChromosomes(ArrayList<Integer> newList) {
		genes = newList;
	}
}
