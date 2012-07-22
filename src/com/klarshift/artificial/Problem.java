package com.klarshift.artificial;

import org.apache.log4j.Logger;

import com.klarshift.artificial.gui.ProblemPanel;

/**
 * abstract problem
 * @author timo
 *
 */
public abstract class Problem {	
	protected Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * init the problem
	 */
	public abstract void init();
	
	/**
	 * create gui panel for the problem
	 * @param application
	 * @return
	 */
	public abstract ProblemPanel getPanel();			
}
