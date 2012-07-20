package com.klarshift.ga;

import java.util.ArrayList;

import com.klarshift.ga.crossover.ICrossover;
import com.klarshift.ga.mutation.IMutation;
import com.klarshift.ga.random.RandomGaussian;
import com.klarshift.ga.selection.ISelection;
import com.klarshift.ga.selection.TournamentSelection;

/**
 * genetic algorithm
 * 
 * @author timo
 * 
 */
public class GA implements IAlgorithm {
	/* settings */
	private float mutationRate = 0.15f;
	private float crossOverRate = 0.95f;
	private Integer populationSize = 200;
	private boolean elitism = true;

	private boolean running = false;

	/* state */
	private int currentGeneration = 0;

	private Population currentPopulation = new Population();

	private IGenomeFactory factory;
	private IFitnessFunction fitnessFunction;
	private ISelection selection = new TournamentSelection();
	private IMutation mutator;
	private ICrossover crossover;
	private IStopCriterion stopCriterion;

	private ArrayList<IGenerationListener> genListener = new ArrayList<IGenerationListener>();

	private double startDev = 1.0;
	private double stdDev = 1.0;
	private double stdDampRate = 0.999;

	/**
	 * get deviation damping
	 * 
	 * @return
	 */
	public Double getStdDampRate() {
		return stdDampRate;
	}

	/**
	 * set standard deviation damping
	 * 
	 * @param r
	 */
	public void setStdDampRate(Double r) {
		this.stdDampRate = r;
	}

	/**
	 * add generation listener
	 * 
	 * @param listener
	 */
	public void addGenerationListener(IGenerationListener listener) {
		genListener.add(listener);
	}

	/**
	 * init reset all runtime variables
	 */
	private void init() {
		stdDev = startDev;
		currentGeneration = 0;
		currentPopulation = new Population();
	}

	private RandomGaussian random = new RandomGaussian();

	/**
	 * create genetic algorithm
	 */
	public GA() {

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
	}

	/**
	 * set crossover
	 * 
	 * @param crossover
	 */
	public void setCrossover(ICrossover crossover) {
		this.crossover = crossover;
	}

	/**
	 * set the mutation method
	 * 
	 * @param mutator
	 */
	public void setMutator(IMutation mutator) {
		this.mutator = mutator;
	}

	/**
	 * set a genome factory
	 * 
	 * @param factory
	 */
	public void setGenomeFactory(IGenomeFactory factory) {
		this.factory = factory;
	}

	/**
	 * set the stop criterion
	 * 
	 * @param stopCriteria
	 */
	public void setStopCriterion(IStopCriterion stopCriteria) {
		this.stopCriterion = stopCriteria;
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
		if (mutator == null) {
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
		init();
		createGenes();

		// rank first time
		currentPopulation.rank(fitnessFunction);
		onGenerationFinished();

		// evolve
		currentGeneration = 0;
		while (stopCriterion.shouldStop() == false && running) {
			// generate new population
			populateNextGeneration();

			// rank
			currentPopulation.rank(fitnessFunction);

			// inform observers
			onGenerationFinished();
		}
	}

	/**
	 * generation finished inform listeners
	 */
	private void onGenerationFinished() {
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
		// next population
		Population nextPopulation = new Population();

		// get best from current population
		// to store it for elistism
		Chromosome best = currentPopulation.getBest();

		while (nextPopulation.size() < populationSize) {
			if (populationSize > 1) {
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
			} else {
				nextPopulation.add(best);
			}
		}

		// perform mutation on next generation
		for (int g = 0; g < nextPopulation.size(); g++) {
			Chromosome gen = nextPopulation.get(g);
			if (random.nextDouble() < mutationRate)
				mutator.mutate(gen, stdDev);
		}

		if (elitism && populationSize > 1) {
			nextPopulation.add(best);
		}

		// switch population
		currentPopulation = nextPopulation;
		currentGeneration++;
		stdDev *= stdDampRate;
	}

	/**
	 * get current deviation
	 * 
	 * @return
	 */
	public Double getStdDeviation() {
		return stdDev;
	}

	/**
	 * set standard deviation
	 * 
	 * @param d
	 */
	public void setStdDeviation(Double d) {
		this.startDev = d;
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
	private void createGenes() {
		for (int p = 0; p < populationSize; p++) {
			currentPopulation.add(factory.createGenome());
		}
	}

	/**
	 * set the fitness function
	 * 
	 * @param fitness
	 */
	public void setFitnessFunction(IFitnessFunction fitness) {
		this.fitnessFunction = fitness;
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
	}

	/**
	 * set the crossover rate
	 * 
	 * @param f
	 */
	public void setCrossoverRate(Float f) {
		this.crossOverRate = f;
	}

	/**
	 * set elite state
	 * 
	 * @param b
	 */
	public void setElitism(Boolean b) {
		this.elitism = b;
	}

	/**
	 * get elite state
	 * 
	 * @return
	 */
	public Boolean getElitism() {
		return elitism;
	}

	/**
	 * setulation size
	 * 
	 * @param v
	 */
	public void setPopulationSize(Integer v) {
		this.populationSize = v;
	}

	/**
	 * get the mutation rate
	 * 
	 * @return
	 */
	public Float getMutationRate() {
		return mutationRate;
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
		if (!running) {
			running = true;
			try {
				populate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			running = false;
		}
	}

	@Override
	public void stop() {
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
