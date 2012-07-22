package com.klarshift.artificial.genetic;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.klarshift.artificial.IAlgorithm;
import com.klarshift.artificial.IChromosomeFactory;
import com.klarshift.artificial.IFitnessFunction;
import com.klarshift.artificial.IGenerationListener;
import com.klarshift.artificial.IStopCriteria;
import com.klarshift.artificial.Population;
import com.klarshift.artificial.genetic.crossover.ICrossover;
import com.klarshift.artificial.genetic.mutation.IMutation;
import com.klarshift.artificial.genetic.selection.ISelection;
import com.klarshift.artificial.genetic.selection.TournamentSelection;
import com.klarshift.artificial.modulation.AnnealingModulation;
import com.klarshift.artificial.modulation.IMutationModulation;
import com.klarshift.artificial.modulation.LinearModulation;
import com.klarshift.artificial.util.RandomGaussian;

/**
 * genetic algorithm
 * 
 * @author timo
 * 
 */
public class GA implements IAlgorithm {
	private final Logger log = Logger.getLogger(this.getClass());
	
	/* settings */
	private float mutationRate = 0.15f;
	private float crossOverRate = 0.85f;
	private Integer populationSize = 200;
	private float elitismRate = 0.1f;

	

	/* state */
	private int currentGeneration = 0;
	private boolean running = false;

	private Population currentPopulation = new Population();

	private IChromosomeFactory factory;
	private IFitnessFunction fitnessFunction;
	private ISelection selection = new TournamentSelection();
	private IMutation mutation;
	private ICrossover crossover;
	private IStopCriteria stopCriterion;
	private IMutationModulation modulation = new AnnealingModulation(1, 0.001, 100);

	private ArrayList<IGenerationListener> genListener = new ArrayList<IGenerationListener>();
	
	private RandomGaussian random = new RandomGaussian();

	private Double modulationRate = new Double(0);

	/**
	 * create genetic algorithm
	 */
	public GA() {

	}
	
	public void setModulation(IMutationModulation modulation){
		this.modulation = modulation;
		log.info("Set modulation: " + modulation);
	}

	
	
	/**
	 * get the elitism rate
	 * @return
	 */
	public Float getElitismRate(){
		return elitismRate;
	}
	
	/**
	 * set the elitism rate
	 * @param er
	 */
	public void setElitismRate(Float er){
		this.elitismRate = er;
	}

	

	/**
	 * add generation listener
	 * 
	 * @param listener
	 */
	public void addGenerationListener(IGenerationListener listener) {
		log.info("Added Generation Listener: " + listener);
		genListener.add(listener);
	}

	/**
	 * init reset all runtime variables
	 */
	private void init() {
		currentGeneration = 0;
		currentPopulation = new Population();
		log.info("Initialized");
	}

	

	/**
	 * get the current population
	 * 
	 * @return
	 */
	public Population getPopulation() {
		return currentPopulation;
	}

	/**
	 * set the selection method
	 * 
	 * @param selection
	 */
	public void setSelection(ISelection selection) {
		this.selection = selection;
		log.info("Set selection: " + selection);
	}

	/**
	 * set crossover
	 * 
	 * @param crossover
	 */
	public void setCrossover(ICrossover crossover) {
		this.crossover = crossover;
		log.info("Set crossover: " + crossover);
	}

	/**
	 * set the mutation method
	 * 
	 * @param mutation
	 */
	public void setMutator(IMutation mutation) {
		this.mutation = mutation;
		log.info("Set mutation: " + mutation);
	}

	/**
	 * set a genome factory
	 * 
	 * @param factory
	 */
	public void setChromosomeFactory(IChromosomeFactory factory) {
		this.factory = factory;
		log.info("Set chromosome factory: " + factory);
	}

	/**
	 * set the stop criterion
	 * 
	 * @param stopCriterion
	 */
	public void setStopCriterion(IStopCriteria stopCriterion) {
		this.stopCriterion = stopCriterion;
		log.info("Set stop criterion: " + stopCriterion);
	}

	/**
	 * get the population size
	 * 
	 * @return
	 */
	public Integer getPopulationSize() {
		return populationSize;
	}

	/**
	 * populate
	 * 
	 * @throws Exception
	 */
	private void populate() throws Exception {
		log.debug("Populating ...");
		
		// validate genome factory
		if (factory == null) {
			throw new Exception("No Genome factory set.");
		}

		// validate fitness function
		if (fitnessFunction == null) {
			throw new Exception("No Fitness function set.");
		}

		// validate selection
		if (selection == null) {
			throw new Exception("No Selection function set.");
		}

		// validate mutator
		if (mutation == null) {
			throw new Exception("No Mutator set.");
		}

		// validate stop criterion
		if (stopCriterion == null) {
			throw new Exception("No Stop Criterion set.");
		}

		// validate crossover
		if (crossover == null) {
			throw new Exception("No Crossover set.");
		}

		// create initial genes
		// generation=0
		init();
		createChromosomes();
			
		// evolve		
		while (stopCriterion.shouldStop() == false && running) {
								
			// generate new population
			populateNextGeneration();
			
			// rank first time
			currentPopulation.rank(fitnessFunction);

			// inform observers
			onGenerationFinished();
		}
	}

	/**
	 * generation finished inform listeners
	 */
	private void onGenerationFinished() {
		log.debug("Generation finished. GEN=" + currentGeneration );
		
		for (IGenerationListener gl : genListener) {
			gl.onGenerationFinished();
		}
	}
	

	/**
	 * populate the next generation
	 * 
	 * @throws Exception
	 */
	private void populateNextGeneration() throws Exception {
		log.debug("Generating population " + (currentGeneration+1));
		
		// create next population
		Population nextPopulation = new Population();
		
		// handle elitism
		ArrayList<Chromosome> elites = currentPopulation.getElites(elitismRate);
		log.debug("Elitism Rate: " + elitismRate + " / keeping " + elites.size() + " elites.");
				
		
		

		// fill up the population
		int fillLimit = populationSize-elites.size();
		log.debug("Filling next population with " + fillLimit + " chromosomes.");
		while (nextPopulation.size() < fillLimit) {			
			ChromosomePair pair = selection.select(currentPopulation);

			// crossover
			if (random.nextDouble() <= crossOverRate) {
				// add all children
				for (Chromosome child : crossover.crossover(pair)) {
					nextPopulation.add(child);
				}
			} else {
				// keep selection
				nextPopulation.add(pair);
			}			
		}

		// perform mutation on next generation
		log.debug("Performing mutation ...");
		modulationRate = modulation.modulateMutation(currentGeneration);
		for (Chromosome gen : nextPopulation) {			
			if (random.nextDouble() < mutationRate){
				mutation.mutate(gen, modulationRate);
			}
				
		}

		// handle elitism
		if (elites.size() > 0) {
			nextPopulation.add(elites);
		}

		// switch population
		currentPopulation = nextPopulation;
		currentGeneration++;
	}
	
	





	/**
	 * debug print genes
	 */
	public void printGenomes() {
		for (Chromosome g : currentPopulation) {
			System.out.println("\t" + g);
		}
	}

	/**
	 * initially create all genes
	 */
	private void createChromosomes() {
		log.info("Creating initial chromosomes");
		for (int p = 0; p < populationSize; p++) {
			currentPopulation.add(factory.createChromosome());
		}
		
		// rank first time
		currentPopulation.rank(fitnessFunction);
		
		// generation 0
		onGenerationFinished();
	}

	/**
	 * set the fitness function
	 * 
	 * @param fitness
	 */
	public void setFitnessFunction(IFitnessFunction fitness) {
		this.fitnessFunction = fitness;
		log.info("Set fitness function: " + fitness);
	}

	/**
	 * get the best genome
	 * 
	 * @return
	 */
	public Chromosome getBestGenome() {
		return currentPopulation.getBest();
	}

	/**
	 * get the worst genome
	 * 
	 * @return
	 */
	public Chromosome getWorstGenome() {
		return currentPopulation.getWorst();
	}

	/**
	 * get the current generation
	 * 
	 * @return
	 */
	public int getGeneration() {
		return currentGeneration;
	}

	/**
	 * set the mutation rate
	 * 
	 * @param d
	 */
	public void setMutationRate(Float d) {
		mutationRate = d;
		log.info("Set mutation rate: " + d);
	}

	/**
	 * set the crossover rate
	 * 
	 * @param f
	 */
	public void setCrossoverRate(Float f) {
		this.crossOverRate = f;
		log.info("Set crossover rate: " + f);
	}

	

	/**
	 * setulation size
	 * 
	 * @param v
	 */
	public void setPopulationSize(Integer v) {
		this.populationSize = v;
		log.info("Set population size: " + v);
	}

	/**
	 * get the mutation rate
	 * 
	 * @return
	 */
	public Float getMutationRate() {
		return mutationRate;
	}
	
	public Double getModulationRate(){
		return modulationRate;
	}

	/**
	 * get the crossover rate
	 * 
	 * @return
	 */
	public Float getCrossoverRate() {
		return crossOverRate;
	}

	@Override
	public void start() {
		log.info("Starting algorithm");
		if (!running) {
			running = true;
			try {
				populate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				running = false;	
			}
			
		}
	}

	@Override
	public void stop() {
		log.info("Stopping algorithm");
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	/**
	 * get the current selection
	 * 
	 * @return
	 */
	public ISelection getSelection() {
		return selection;
	}
}
