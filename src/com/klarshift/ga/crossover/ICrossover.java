package com.klarshift.ga.crossover;

import java.util.List;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.ChromosomePair;

/**
 * crossover interface
 * @author timo
 *
 */
public interface ICrossover {
	public List<Chromosome> crossover(ChromosomePair pair);
}
