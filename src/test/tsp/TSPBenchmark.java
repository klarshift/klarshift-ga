package test.tsp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import test.tsp.solution.TSPSolution;
import test.tsp.solution.es.TSPSolutionES;
import test.tsp.solution.ga.TSPSolutionGA;
import test.tsp.solution.sa.TSPSolutionSimulatedAnnealing;

import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.crossover.CrossoverList;
import com.klarshift.artificial.genetic.mutation.MutationList;
import com.klarshift.artificial.modulation.AnnealingModulation;
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
	private LinkedHashMap<String,TSPSolution> solutions = new LinkedHashMap<String,TSPSolution>();
	private LinkedHashMap<String,Result> avgResult = new LinkedHashMap<String,Result>();
	private TSPProblem problem;
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	static{
		Logging.setup();
		Logging.setLevel(Level.INFO);
	}
		
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
	
	/**
	 * benchmark a solution
	 * @param solution
	 * @param s
	 * @return
	 */
	private ArrayList<Result> benchmark(TSPSolution solution, int s){
		
		Result totalResult = new Result(solution, 0, 0, 0);
		ArrayList<Result> results = new ArrayList<Result>();
		
		for(int k=0; k<s; k++){
			log.info("** Bench // " + solution + " // #" + k);
			// solve
			solution.solveProblem();
			
			Result r = new Result(solution, solution.getIterationCount(), solution.getRuntime(), problem.getCurrentDistance());
			totalResult.distance += r.distance;
			totalResult.iterations += r.iterations;
			totalResult.runtime += r.runtime;
			
			log.info("\tdistance  : " + r.distance);
			log.info("\titerations: " + r.iterations);
			log.info("\trunTime   : " + r.runtime + " ms");
			
			results.add(r);
		}
		
		totalResult.distance /= s;
		totalResult.iterations /= s;
		totalResult.runtime /= s;
		
		log.info("** Avg Results");
		log.info("\tavg. distance  : " + totalResult.distance);
		log.info("\tavg. iterations: " + totalResult.iterations);
		log.info("\tavg. runTime   : " + totalResult.runtime + " ms");
		
		avgResult.put(solution.toString(), totalResult);
		
		return results;
	}
	
	/**
	 * init boundaries
	 * @param maxIterations
	 * @param maxRuntime
	 * @param targetDistance
	 */
	private void init(int maxIterations, long maxRuntime, double targetDistance){
		for(Entry<String, TSPSolution> s : solutions.entrySet()){
			TSPSolution solution = s.getValue();
								
			// set stop criteria
			solution.setTargetDistance(targetDistance);
			solution.setMaxIterations(maxIterations);
			solution.setMaxRuntime(maxRuntime);			
		}
	}
	
	public void run(){
		run(-1, 5000, 17, 20);
	}
	
	public void run(int maxIterations, long maxRuntime, double targetDistance, int c){
		// init
		init(maxIterations, maxRuntime, targetDistance);
		
		// run all
		for(Entry<String, TSPSolution> s : solutions.entrySet()){
			TSPSolution solution = s.getValue();
			log.info("*** Benchmarking " + solution);	
			benchmark(solution, c);
		}
		
		System.out.println("***\r\nName;Iterations;Runtime;Distance");
		for(Entry<String, Result> k : avgResult.entrySet()){
			System.out.println(k.getValue().toCSV());
		}
	}	
	
	
	
	public static TSPBenchmark createAllBenchmark(TSPProblem problem){
		TSPBenchmark b = new TSPBenchmark(problem);
		
		// add ga`s
		TSPSolutionGA ga = new TSPSolutionGA();
		b.add("GA", ga);
		
		b.add("SA", new TSPSolutionSimulatedAnnealing());
		b.add("ES", new TSPSolutionES());
		
		
		return b;
	}
	
	public static TSPSolutionGA createBestGASolution(){
		
		return new TSPSolutionGA(){
			@Override
			protected void configureGA(GA ga, MutationList mList,
					CrossoverList cList) {				
				super.configureGA(ga, mList, cList);
				
				ga.setPopulationSize(80);
				ga.setElitismRate(0.1f);
				ga.setCrossoverRate(0.4f);
				ga.setMutationRate(0.85f);
				ga.setModulation(new AnnealingModulation(1, 0.001, 100));
			}
			
			@Override
			public String toString() {
				return "GA Best";
			}					
		};
	}
	
	
	
	
	
	public class Result {
		int iterations;
		double distance;
		long runtime;
		TSPSolution solution;
		public Result(TSPSolution s, int iterations, long runtime, double distance){
			this.solution = s;
			this.iterations = iterations;
			this.runtime = runtime;
			this.distance = distance;
		}
		
		public String toCSV(){
			return solution.toString() + ";" + iterations + ";" + runtime + ";" + new String(""+distance).replace(".", ",");
		}
	}
	
	
}
