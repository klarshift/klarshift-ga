package com.klarshift.ga.selection;

import com.klarshift.ga.ChromosomePair;
import com.klarshift.ga.Population;


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
