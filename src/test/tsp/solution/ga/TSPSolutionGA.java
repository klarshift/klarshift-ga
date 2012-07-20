package test.tsp.solution.ga;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

import test.tsp.solution.TSPSolution;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.GA;
import com.klarshift.ga.IFitnessFunction;
import com.klarshift.ga.IGenerationListener;
import com.klarshift.ga.IGenomeFactory;
import com.klarshift.ga.IStopCriterion;
import com.klarshift.ga.Problem;
import com.klarshift.ga.crossover.CrossoverList;
import com.klarshift.ga.crossover.tsp.OrderBasedCrossover;
import com.klarshift.ga.genome.IndexChromosome;
import com.klarshift.ga.gui.SolutionPanel;
import com.klarshift.ga.mutation.MutationList;
import com.klarshift.ga.mutation.tsp.Displacement;
import com.klarshift.ga.mutation.tsp.Insertion;
import com.klarshift.ga.mutation.tsp.Inversion;
import com.klarshift.ga.mutation.tsp.ReciprocalExchange;
import com.klarshift.ga.selection.TournamentSelection;
import com.klarshift.ga.tsp.TSPProblem;
import com.klarshift.ga.tsp.gui.TSPProblemPanel;

/**
 * Genetic Algorithm TSP Solution
 * 
 * solves the TSPProblem with GA Algorithm approach
 * 
 * @author timo
 * 
 */
public class TSPSolutionGA extends TSPSolution implements IGenomeFactory, IStopCriterion,
		IFitnessFunction, IGenerationListener {
		
	/* crossover and mutation lists */
	private CrossoverList xList = new CrossoverList();
	private MutationList mList = new MutationList();

	/* distance */
	private double minD;
	private Double minStopDistance = new Double(5);

	private GA ga;
	
	Integer maxGenerations = 100;

	
	// the problem
	private TSPProblem problem = null;
	
	@Override
	public SolutionPanel getPanel() {
		return new TSPSolutionGAPanel(this);
	}
	
	/**
	 * get minimum distance
	 * @return
	 */
	public double getMinDistance(){
		return minD;
	}
	
	public void setMinStopDistance(Double distance){
		this.minStopDistance = distance;
	}
	
	public Double getMinStopDistance(){
		return minStopDistance;
	}
	
	public void setMaxGenerations(Integer maxGenerations){
		this.maxGenerations = maxGenerations;
	}
	
	public Integer getMaxGenerations(){
		return maxGenerations;
	}

	@Override
	public void init(Problem problem) throws Exception {
		super.init(problem);
		
		if (problem instanceof TSPProblem == false) {
			throw new Exception("Cannot solve this problem.");
		}

		// store problem
		this.problem = (TSPProblem) problem;
		minD = Double.MAX_VALUE;
		
		
	}
	
	public String toString(){
		return "Solution: Genetic Algorithm";
	}
	
	

	public TSPSolutionGA() {
		createGA();
		
	}
	
	private void createGA(){
		ga = new GA();
		ga.setGenomeFactory(this);
		ga.setFitnessFunction(this);
		ga.setMutator(mList);
		ga.setStopCriterion(this);
		ga.setMutationRate(0.05f);
		ga.setCrossoverRate(0.85f);
		ga.addGenerationListener(this);
		ga.setSelection(new TournamentSelection(0.2));
		ga.setCrossover(xList);
		ga.setElitism(true);

		// crossover
		// xList.add(new PositionBasedCrossover());
		xList.add(new OrderBasedCrossover());

		// mutations
		//mList.add(new SimpleInversion());
		mList.add(new ReciprocalExchange());
		mList.add(new Insertion());
		mList.add(new Displacement());
		mList.add(new Inversion());
	}

	

	@Override
	public double fitness(Chromosome genome) {
		double maxD = getDistance((IndexChromosome) genome);

		// make distance a fitness function
		return 1 / maxD;
	}
	
	private double getDistance(IndexChromosome c){
		return problem.getTotalDistance(c.getChromosomes());
	}

	@Override
	public boolean shouldStop() {
		return ga.getGeneration() >= maxGenerations || minD <= minStopDistance
				|| ga.getStdDeviation() < 0.1;
	}

	@Override
	public Chromosome createGenome() {
		int cc = problem.getCityCount();
		IndexChromosome g = new IndexChromosome(cc);

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

	@Override
	public void onGenerationFinished() {
		double d = getDistance((IndexChromosome) ga.getBestGenome());
		if (d < minD) {
			minD = d;
		}
		
		TSPProblemPanel panel = (TSPProblemPanel) problem.getPanel();				
		
		// set current solution to problem
		IndexChromosome c = ((IndexChromosome)ga.getBestGenome());			
		problem.setCurrentSolution(c.getChromosomes());
		panel.update();
	}

	@Override
	public void solve() {
		ga.start();
	}
	
	@Override
	public void stopSolving() {
		ga.stop();
	}

	public GA getGA() {
		return ga;
	}
}
