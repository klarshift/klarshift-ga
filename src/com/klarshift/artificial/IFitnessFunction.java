package com.klarshift.artificial;

import com.klarshift.artificial.genetic.Chromosome;


public interface IFitnessFunction {
	public double fitness(Chromosome genome);
}
