package com.klarshift.ga.genome;

import java.util.ArrayList;
import java.util.Random;

import com.klarshift.ga.Chromosome;

/**
 * genome 
 * @author timo
 *
 */
public class ValueChromosomeDouble extends Chromosome {
	private final static Random random = new Random();
	private ArrayList<Double> genes = new ArrayList<Double>();
	
	
	/**
	 * create a genome with double values
	 * and a given size
	 * @param size
	 */
	public ValueChromosomeDouble(int size) {	
		for(int g=0; g<size; g++){
			genes.add(random.nextDouble()-0.5);
		}
		init();
	}
	
	public ValueChromosomeDouble(ValueChromosomeDouble other) {	
		genes.clear();
		for(int g=0; g<other.getGenes().size(); g++){
			genes.add(new Double(other.get(g)));
		}
		init();
	}
	
	
	
	public final ArrayList<Double> getGenes(){
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

	public double get(int i) {
		return genes.get(i);
	}

	public int size() {
		return genes.size();
	}

	public void set(int c, double d) {
		genes.set(c, d);
	}
	
	@Override
	public Chromosome clone() {
		return (Chromosome) new ValueChromosomeDouble(this);
	}
}
