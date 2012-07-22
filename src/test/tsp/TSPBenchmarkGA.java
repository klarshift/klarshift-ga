package test.tsp;

import java.io.File;

import org.apache.log4j.Level;

import test.tsp.solution.ga.TSPSolutionGA;

import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.crossover.CrossoverList;
import com.klarshift.artificial.genetic.mutation.MutationList;
import com.klarshift.artificial.modulation.AnnealingModulation;
import com.klarshift.artificial.problem.tsp.TSPProblem;
import com.klarshift.artificial.util.Logging;

public class TSPBenchmarkGA extends TSPBenchmark {
	public TSPBenchmarkGA(TSPProblem problem) {
		super(problem);
	}
	
	public void benchmarkMutation(){
		log.info("Benchmarking Mutation");
		for(float m=0.35f; m<1; m+=0.025){
			TSPSolutionGA ga = createSolution(80, m, 0.85f, 0.15f, 50, 0);
			add(ga.toString(), ga);
		}
		
		run();
	}
	
	public void benchmarkCrossover(){
		log.info("Benchmarking Crossover");
		for(float m=0.1f; m<1; m+=0.05){
			TSPSolutionGA ga = createSolution(80, 0.8f, m, 0.15f, 50, 0);
			add(ga.toString(), ga);
		}
		
		run();
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
		
		TSPBenchmarkGA b = new TSPBenchmarkGA(problem);
		if(type == "mutation"){
			b.benchmarkMutation();
		}else if(type == "crossover"){
			b.benchmarkCrossover();
		}
			
		//b.run(100000, 5000, 17, 30);							
	}
	
	public static TSPBenchmark createGABenchmark(TSPProblem problem){
		TSPBenchmark b = new TSPBenchmark(problem);
		
		// add ga`s
		TSPSolutionGA ga;
		
		// mutation
		/*
		for(float m=0.35f; m<1; m+=0.025){
			ga = createSolution(80, m, 0.85f, 0.15f, 100);
			b.add(ga.toString(), ga);
		}*/
		
		// elitism
		/*for(float m=0.0f; m<0.5; m+=0.025){
			ga = createSolution(80, 0.85f, 0.85f, m, 100);
			b.add(ga.toString(), ga);
		}*/
		
		// modulation
		/*for(int m=10; m<1000; m+=50){
			ga = createSolution(80, 0.85f, 0.85f, 0.15f, 50, m);
			b.add(ga.toString(), ga);
		}*/
		
		// test
		for(int m=0; m<100; m++){
			ga = createSolution(80, 0.85f, 0.85f, 0.15f, 50, m);
			b.add(ga.toString(), ga);
		}
		
		// population
		/*for(int m=10; m<2000; m*=2){
			ga = createSolution(m, 0.8f, 0.85f, 0.15f, 100);
			b.add(ga.toString(), ga);
		}*/
		
		
		return b;
	}
	
	public static TSPSolutionGA createSolution(final int populationSize, final float mut, final float cross,  final float elitism, final int modRate, final int id){
		
		return new TSPSolutionGA(){
			@Override
			protected void configureGA(GA ga, MutationList mList,
					CrossoverList cList) {				
				super.configureGA(ga, mList, cList);
				
				ga.setPopulationSize(populationSize);
				ga.setElitismRate(elitism);
				ga.setCrossoverRate(cross);
				ga.setMutationRate(mut);
				ga.setModulation(new AnnealingModulation(1, 0.001, modRate));
			}
			
			@Override
			public String toString() {
				return "GA("+id+") P-" + populationSize + "/e" + elitism + "/m" + mut + "/c" + cross + "/mod" + modRate;
			}					
		};
	}
}
