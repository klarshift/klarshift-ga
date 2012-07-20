package com.klarshift.ga;

import java.util.Date;

import com.klarshift.ga.gui.SolutionPanel;

/**
 * solution base class
 * 
 * @author timo
 * 
 */
public abstract class Solution {
	private Problem problem;
	private boolean solving = false;
	private long startTime;
	private long runTime = 0;

	/**
	 * get the current runtime
	 * @return
	 */
	public long getRuntime() {
		return runTime;
	}

	/**
	 * init a solution with the problem
	 * @param problem
	 * @throws Exception
	 */
	public void init(Problem problem) throws Exception {
		this.problem = problem;
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
			solving = true;
			startTime = new Date().getTime();
			solve();

		} catch (Exception e) {

		} finally {
			runTime = new Date().getTime() - startTime;
			solving = false;
		}
	}

	/**
	 * get a gui panel when available
	 * @return
	 */
	public abstract SolutionPanel getPanel();
}
