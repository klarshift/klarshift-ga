package com.klarshift.artificial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.genetic.ChromosomePair;

/**
 * GA Population
 * @author timo
 *
 */
public class Population implements Iterable<Chromosome>{
	private final Logger log = Logger.getLogger(this.getClass());
	
	private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	private TreeSet<Chromosome> elites = new TreeSet<Chromosome>();
		
	private double totalFitness;
	private double avgFitness;
	private Chromosome bestChromosome, worstChromosome;

	/**
	 * get the best chromosome
	 * @return
	 */
	public Chromosome getBest(){
		return bestChromosome;
	}
	
	/**
	 * get the worst chromosome
	 * @return
	 */
	public Chromosome getWorst(){
		return worstChromosome;
	}
	
	/**
	 * add chromosome
	 * @param genome
	 */
	public void add(Chromosome genome){
		chromosomes.add(genome);
		elites.add(genome);
		update();
	}
	
	/**
	 * get the size of the population
	 * @return
	 */
	public int size(){
		return chromosomes.size();
	}
	
	private void update(){
		// track best and worst chromosome
		bestChromosome = elites.first();
		worstChromosome = elites.last();
	}
	
	/**
	 * rank the population with a given 
	 * fitness function
	 * 
	 * @param fitnessFunction
	 */
	public void rank(IFitnessFunction fitnessFunction){
		if(chromosomes.size() < 1){
			return;
		}
		
		log.debug("Ranking population of " + size());
		
		// reset
		totalFitness = 0;		
		elites.clear();	
		
		// set all current fitness
		double fit;
		for (int g = 0; g < chromosomes.size(); g++) {
			// get chromosome
			Chromosome gen = chromosomes.get(g);
					
			// set fitness
			fit = fitnessFunction.fitness(gen);
			gen.setFitness(fit);
			
			// add in sorted manner
			elites.add(gen);
			
			// track total fitness
			totalFitness += fit;
		}
		
		avgFitness = totalFitness / size();
		
		update();
		
		log.debug("Ranking done.");
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
		return bestChromosome.getFitness();
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
	 * get the elite chromosomes
	 * 
	 * @param elitismRate
	 * @return
	 */
	public ArrayList<Chromosome> getElites(float elitismRate){
		int eliteCount = (int) Math.max(0, elitismRate * size());
		return getElites(eliteCount);
	}
	
	/**
	 * get the elite chromosomes 
	 * 
	 * @param eliteCount
	 * @return
	 */
	public ArrayList<Chromosome> getElites(int eliteCount){
		ArrayList<Chromosome> best = new ArrayList<Chromosome>();
		eliteCount = Math.min(eliteCount, size());
		
		if(eliteCount == 0){
			return best;
		}
		
		// collect elites
		Iterator<Chromosome> ei = elites.iterator();		
		while(ei.hasNext() && best.size() < eliteCount){
			Chromosome c = ei.next();			
			best.add(c);
		}	
		return best;			
	}

	/**
	 * get current minimum fitness
	 * @return
	 */
	public double getMinFitness() {
		return worstChromosome.getFitness();
	}

	/**
	 * get chromosome list
	 * @return
	 */
	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	/**
	 * add a chromosome pair
	 * @param pair
	 */
	public void add(ChromosomePair pair) {
		add(pair.getFirst());
		add(pair.getSecond());
	}

	/**
	 * clear population
	 */
	public void clear() {
		chromosomes.clear();
	}

	/**
	 * add chromosomes
	 * @param chromosomes
	 */
	public void add(ArrayList<Chromosome> chromosomes) {
		for(Chromosome c : chromosomes){
			add(c);
		}
		
	}
}
