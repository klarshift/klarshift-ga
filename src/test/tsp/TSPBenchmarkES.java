package test.tsp;

import java.io.File;

import test.tsp.solution.es.TSPSolutionES;
import test.tsp.solution.sa.TSPSolutionSimulatedAnnealing;

import com.klarshift.artificial.problem.tsp.TSPProblem;

public class TSPBenchmarkES extends TSPBenchmark {
	public TSPBenchmarkES(TSPProblem problem) {
		super(problem);
	}
	

	public static void main(String args[]){
		String filename = "data/tsp-cities.txt";
		if(args.length > 0){
			filename = args[0];
		}
				
		
		// get problem
		TSPProblem problem = new TSPProblem();
		File tspFile = new File(filename);
		problem.setFile(tspFile);
		
		TSPBenchmarkES b = new TSPBenchmarkES(problem);
		
		// add stuff to benchmark
		b.add("GA Best", createBestGASolution());
		
		
		b.add("ES-" + 0.9, createESSolution(0.9));
		b.add("ES-" + 0.95, createESSolution(0.95));
		b.add("ES-" + 0.99, createESSolution(0.99));
		b.add("ES-" + 0.995, createESSolution(0.995));
		b.add("ES-" + 0.999, createESSolution(0.999));
		b.add("ES-" + 0.9995, createESSolution(0.9995));
		
		
		
		b.run(-1, 12000, 17, 10);
			
		//b.run(100000, 5000, 17, 30);							
	}	
	
	public static TSPSolutionES createESSolution(final double coolingRate){
		TSPSolutionES s = new TSPSolutionES(){
		
							
			@Override
			public String toString() {
				return "ES-" + coolingRate;
			}
		};
		
		s.setTargetDistance((double) 17);
		s.setDeviationReduction(coolingRate);	
		
		return s;
	}
	
	
}
