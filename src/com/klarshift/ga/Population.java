package com.klarshift.ga;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Population implements Iterable<Chromosome>{
	private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	
	private double maxFitness, minFitness, totalFitness;
	Chromosome bestGenome, worstGenome;

	public Chromosome getBest(){
		return bestGenome;
	}
	
	public Chromosome getWorst(){
		return worstGenome;
	}
	
	public void add(Chromosome genome){
		chromosomes.add(genome);
	}
	
	public int size(){
		return chromosomes.size();
	}
	
	public void rank(IFitnessFunction fitnessFunction){
		maxFitness = -Double.MAX_VALUE;
		minFitness = Double.MAX_VALUE;
		totalFitness = 0;

		// set all current fitness
		double fit;
		for (int g = 0; g < chromosomes.size(); g++) {
			// get genome
			Chromosome gen = chromosomes.get(g);
					
			// set fitness
			fit = fitnessFunction.fitness(gen);
			gen.setFitness(fit);

			// track best
			if (fit > maxFitness) {
				maxFitness = fit;
				bestGenome = gen;
			// track worst
			}else if (fit < minFitness) {
				minFitness = fit;
				worstGenome = gen;
			}

			// track total fitness
			totalFitness += fit;
		}
	}
	
	public Chromosome get(int i){
		return chromosomes.get(i);
	}

	@Override
	public Iterator<Chromosome> iterator() {
		return chromosomes.iterator();
	}
	
	/**
	 * get the current maximum fitness
	 * @return
	 */
	public double getMaxFitness() {
		return maxFitness;
	}

	/**
	 * get the current total fitness
	 * over population
	 * @return
	 */
	public double getTotalFitness() {
		return totalFitness;
	}

	/**
	 * get current minimum fitness
	 * @return
	 */
	public double getMinFitness() {
		return minFitness;
	}

	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void add(ChromosomePair pair) {
		add(pair.getFirst());
		add(pair.getSecond());
	}

	public void clear() {
		chromosomes.clear();
	}
}
