package com.klarshift.artificial.genetic.selection;

import com.klarshift.artificial.Population;
import com.klarshift.artificial.genetic.ChromosomePair;


/**
 * selection
 * 
 * @author timo
 *
 */
public interface ISelection {
	/**
	 * get chromosome pair
	 * @param population
	 * @return
	 */
	public ChromosomePair select(Population population);
}
