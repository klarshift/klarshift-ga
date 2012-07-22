package test.tsp;

import java.io.File;

import org.apache.log4j.Level;

import test.tsp.solution.ga.TSPSolutionGA;
import test.tsp.solution.sa.TSPSolutionSimulatedAnnealing;

import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.crossover.CrossoverList;
import com.klarshift.artificial.genetic.mutation.MutationList;
import com.klarshift.artificial.modulation.AnnealingModulation;
import com.klarshift.artificial.problem.tsp.TSPProblem;
import com.klarshift.artificial.util.Logging;

public class TSPBenchmarkSA extends TSPBenchmark {
	public TSPBenchmarkSA(TSPProblem problem) {
		super(problem);
	}
	

	public static void main(String args[]){
		String filename = "data/tsp-cities.txt";
		if(args.length > 0){
			filename = args[0];
		}
		
		String type = "crossover";
		if(args.length > 1){
			// TODO
		}			
		
		// get problem
		TSPProblem problem = new TSPProblem();
		File tspFile = new File(filename);
		problem.setFile(tspFile);
		
		TSPBenchmarkSA b = new TSPBenchmarkSA(problem);
		
		// add stuff to benchmark
		b.add("GA Best", createBestGASolution());
		
		for(int i=1; i<10; i++){
			double temp = 100*i;
			
			b.add("SA-" + temp + "-" + 0.9, createSASolution(temp, 0.9));
			b.add("SA-" + temp + "-" + 0.95, createSASolution(temp, 0.95));
			b.add("SA-" + temp + "-" + 0.99, createSASolution(temp, 0.99));
			b.add("SA-" + temp + "-" + 0.995, createSASolution(temp, 0.995));
		}
		
		
		b.run(-1, 12000, 17, 10);
			
		//b.run(100000, 5000, 17, 30);							
	}	
	
	public static TSPSolutionSimulatedAnnealing createSASolution(final double temp, final double coolingRate){
		TSPSolutionSimulatedAnnealing s = new TSPSolutionSimulatedAnnealing(){
		
							
			@Override
			public String toString() {
				return "SA-" + temp + "-" + coolingRate;
			}
		};
		
		s.setTargetDistance((double) 17);
		s.setTemperature((double) temp);
		s.setCoolingRate(coolingRate);		
		
		return s;
	}
	
	
}
