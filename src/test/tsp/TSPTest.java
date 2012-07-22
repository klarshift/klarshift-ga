package test.tsp;

import test.tsp.solution.es.TSPSolutionES;
import test.tsp.solution.ga.TSPSolutionGA;
import test.tsp.solution.sa.TSPSolutionSimulatedAnnealing;

import com.klarshift.artificial.gui.GApplication;
import com.klarshift.artificial.problem.tsp.TSPProblem;
import com.klarshift.artificial.util.Logging;

public class TSPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logging.setup();

		GApplication app = new GApplication();
		app.addProblem(new TSPProblem());
		app.addSolution(new TSPSolutionGA());
		app.addSolution(new TSPSolutionSimulatedAnnealing());
		app.addSolution(new TSPSolutionES());
		
		
	}

}
