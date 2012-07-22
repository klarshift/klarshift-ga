package com.klarshift.artificial.genetic;

import java.util.UUID;

/**
 * abstract genome
 * @author timo
 *
 */
public abstract class Chromosome implements Comparable<Chromosome> {	
	private Double fitness = new Double(0);	
	private String id = null;
	
	/**
	 * set the fitness
	 * @param f
	 */
	public void setFitness(double f){
		fitness = f;
	}	
	
	protected void init(){
		id = UUID.randomUUID().toString();
	}
	
	/**
	 * clone a genome
	 */
	public abstract Chromosome clone();
	
	/**
	 * get the fitness
	 * @return
	 */
	public Double getFitness(){
		return fitness;
	}
	
	@Override
	public int compareTo(Chromosome o) {
		return o.getFitness().compareTo(fitness);
	}

	public String getId() {
		return id;
	}
}
