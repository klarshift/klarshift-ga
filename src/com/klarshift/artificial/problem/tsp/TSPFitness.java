package com.klarshift.artificial.problem.tsp;

import java.util.ArrayList;

import com.klarshift.artificial.IFitnessFunction;
import com.klarshift.artificial.genetic.Chromosome;

public class TSPFitness implements IFitnessFunction {
	private static final double NORM = 1000;
	private TSPProblem problem;
	
	public TSPFitness(TSPProblem problem){
		this.problem = problem;
	}
	
	@Override
	public double fitness(Chromosome genome) {
		TSPChromosome c = (TSPChromosome) genome;
		double f = NORM / (problem.getTotalDistance(c.getChromosomes()));
		return f;
	}	
}
