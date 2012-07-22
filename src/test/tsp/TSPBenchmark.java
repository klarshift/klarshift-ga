package test.tsp;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import test.tsp.solution.TSPSolution;
import test.tsp.solution.es.TSPSolutionES;
import test.tsp.solution.ga.TSPSolutionGA;
import test.tsp.solution.sa.TSPSolutionSimulatedAnnealing;

import com.klarshift.artificial.problem.tsp.TSPProblem;
import com.klarshift.artificial.util.Logging;

/**
 * benchmarking class
 * 
 * comparing GA, ES and SA
 * 
 * @author timo
 *
 */
public class TSPBenchmark {
	HashMap<String,TSPSolution> solutions = new HashMap<String,TSPSolution>();
	TSPProblem problem;
	Logger log = Logger.getLogger(this.getClass());
	
	public TSPBenchmark(TSPProblem problem){
		this.problem = problem;
	}
	
	public void add(String name, TSPSolution solution){
		solutions.put(name, solution);
		
		try {
			// set problem
			solution.init(problem);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void benchmark(TSPSolution solution, int s){
		
		Result totalResult = new Result(0, 0, 0);
		
		for(int k=0; k<s; k++){
			log.info("** Bench // " + solution + " // #" + k);
			// solve
			solution.solveProblem();
			
			Result r = new Result(solution.getIterationCount(), solution.getRuntime(), problem.getCurrentDistance());
			totalResult.distance += r.distance;
			totalResult.iterations += r.iterations;
			totalResult.runtime += r.runtime;
			
			log.info("\tdistance  : " + r.distance);
			log.info("\titerations: " + r.iterations);
			log.info("\trunTime   : " + r.runtime + " ms");
		}
		
		totalResult.distance /= s;
		totalResult.iterations /= s;
		totalResult.runtime /= s;
		
		log.info("\tavg. distance  : " + totalResult.distance);
		log.info("\tavg. iterations: " + totalResult.iterations);
		log.info("\tavg. runTime   : " + totalResult.runtime + " ms");
	}
	private void init(int maxIterations, long maxRuntime, double targetDistance){
		for(Entry<String, TSPSolution> s : solutions.entrySet()){
			TSPSolution solution = s.getValue();
								
			// set stop criteria
			solution.setTargetDistance(targetDistance);
			solution.setMaxIterations(maxIterations);
			solution.setMaxRuntime(maxRuntime);			
		}
	}
	
	public void run(int maxIterations, long maxRuntime, double targetDistance){
		// init
		init(maxIterations, maxRuntime, targetDistance);
		
		// run all
		for(Entry<String, TSPSolution> s : solutions.entrySet()){
			TSPSolution solution = s.getValue();
			log.info("*** Benchmarking " + solution);
			
			benchmark(solution, 10);
			
		}
	}
	
	public static void main(String args[]){
		Logging.setup();
		Logging.setLevel(Level.INFO);
		
		TSPProblem problem = new TSPProblem();
		File tspFile = new File("data/tsp-cities.txt");
		problem.setFile(tspFile);
		
		TSPBenchmark b = new TSPBenchmark(problem);
		b.add("GA", new TSPSolutionGA());
		b.add("SA", new TSPSolutionSimulatedAnnealing());
		b.add("ES", new TSPSolutionES());
		
		b.run(100000, 5000, 17);							
	}
	
	class Result{
		int iterations;
		double distance;
		long runtime;
		public Result(int iterations, long runtime, double distance){
			this.iterations = iterations;
			this.runtime = runtime;
			this.distance = distance;
		}
	}
	
	
}
