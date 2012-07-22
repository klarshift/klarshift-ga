package test.tsp.solution;

import java.util.ArrayList;
import java.util.Collections;

import com.klarshift.artificial.IChromosomeFactory;
import com.klarshift.artificial.IStopCriteria;
import com.klarshift.artificial.Problem;
import com.klarshift.artificial.Solution;
import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.gui.SolutionPanel;
import com.klarshift.artificial.problem.tsp.TSPChromosome;
import com.klarshift.artificial.problem.tsp.TSPProblem;

/**
 * tsp solution
 * 
 * @author timo
 *
 */
public abstract class TSPSolution extends Solution implements IChromosomeFactory, IStopCriteria {
	
	private Integer maxIterations = -1;
	private Long maxRuntime = -1L;
	private TSPProblem problem;
	private Double targetDistance = -1d;
	
	@Override
	public void init(Problem problem) throws Exception {	
		super.init(problem);
		this.problem = (TSPProblem) problem;
	}
	
	public void setTargetDistance(Double distance){
		this.targetDistance = distance;
	}
	
	@Override
	public Chromosome createChromosome() {
		int cc = ((TSPProblem)getProblem()).getCityCount();
		TSPChromosome g = new TSPChromosome(cc);

		// assign random order of cities
		ArrayList<Integer> available = new ArrayList<Integer>();
		for (int i = 0; i < cc; i++) {
			available.add(i);
		}

		Collections.shuffle(available);
		for (int i = 0; i < cc; i++) {
			g.set(i, available.get(i));
		}

		return g;
	}
	
	public void setMaxRuntime(Long maxRuntime){
		this.maxRuntime = maxRuntime;
	}
	
	public Long getMaxRuntime(){
		return maxRuntime;
	}
	
	public void setMaxIterations(Integer maxIterations){
		this.maxIterations = maxIterations;
	}
	
	public Integer getMaxIterations(){
		return maxIterations;
	}
	
	public abstract int getIterationCount();

	@Override
	public boolean shouldStop() {
		// max iterations
		if(maxIterations > 0 && getIterationCount() >= maxIterations){
			log.info("Max iterations reached: " + maxIterations);
			return true;
		}
		
		// max runtime
		if(maxRuntime > 0 && getRuntime() >= maxRuntime){
			log.info("Max runtime reached: " + maxRuntime + " ms");
			return true;
		}
		
		// target distance
		
		if(targetDistance != -1 && problem.getCurrentDistance() <= targetDistance){
			log.info("Target distance reached: " + problem.getCurrentDistance());
			return true;
		}
		
		return false;
	}
	
	@Override
	protected void solve() {
		problem.setCurrentSolution(null);		
	}

}
