package test.tsp;

import test.tsp.solution.ga.TSPSolutionGA;
import test.tsp.solution.sa.TSPSolutionSimulatedAnnealing;

import com.klarshift.ga.gui.GApplication;
import com.klarshift.ga.tsp.TSPProblem;

public class TSPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GApplication app = new GApplication();
		app.addProblem(new TSPProblem());
		app.addSolution(new TSPSolutionGA());
		app.addSolution(new TSPSolutionSimulatedAnnealing());
		
		
	}

}
