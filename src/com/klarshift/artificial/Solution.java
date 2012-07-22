package com.klarshift.artificial;

import java.util.Date;

import org.apache.log4j.Logger;

import com.klarshift.artificial.gui.SolutionPanel;

/**
 * solution base class
 * 
 * @author timo
 * 
 */
public abstract class Solution {
	protected Logger log = Logger.getLogger(this.getClass());
	private Problem problem;
	private boolean solving = false;
	private long startTime;
	private long runTime = 0;

	/**
	 * get the current runtime
	 * @return
	 */
	public long getRuntime() {
		return new Date().getTime() - startTime;
	}

	/**
	 * init a solution with the problem
	 * @param problem
	 * @throws Exception
	 */
	public void init(Problem problem) throws Exception {
		this.problem = problem;
		log.debug("Init " + this + " with problem " + problem);
	}

	/**
	 * get the problem
	 * @return
	 */
	public Problem getProblem() {
		return problem;
	}

	/**
	 * solve the prolem
	 */
	protected abstract void solve();

	/**
	 * stop solving the problem
	 */
	public abstract void stopSolving();

	/**
	 * get current solving state
	 * @return
	 */
	public boolean isSolving() {
		return solving;
	}

	/**
	 * solve it
	 */
	public void solveProblem() {
		try {
			log.info("Start solving ...");
			solving = true;
			runTime = 0;			
			startTime = new Date().getTime();
			solve();
			

		} catch (Exception e) {

		} finally {
			runTime = getRuntime();
			solving = false;
			log.info("Solving Done. Took " + runTime + " ms");
		}
	}

	/**
	 * get a gui panel when available
	 * @return
	 */
	public abstract SolutionPanel getPanel();
}
