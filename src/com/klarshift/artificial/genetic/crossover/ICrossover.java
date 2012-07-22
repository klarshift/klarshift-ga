package com.klarshift.artificial.genetic.crossover;

import java.util.List;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.genetic.ChromosomePair;

/**
 * crossover interface
 * @author timo
 *
 */
public interface ICrossover {
	public List<Chromosome> crossover(ChromosomePair pair);
}
