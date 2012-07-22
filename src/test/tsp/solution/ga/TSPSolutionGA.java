package test.tsp.solution.ga;

import test.tsp.solution.TSPSolution;

import com.klarshift.artificial.IGenerationListener;
import com.klarshift.artificial.Problem;
import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.crossover.CrossoverList;
import com.klarshift.artificial.genetic.mutation.MutationList;
import com.klarshift.artificial.genetic.selection.TournamentSelection;
import com.klarshift.artificial.gui.SolutionPanel;
import com.klarshift.artificial.problem.tsp.TSPChromosome;
import com.klarshift.artificial.problem.tsp.TSPFitness;
import com.klarshift.artificial.problem.tsp.TSPProblem;
import com.klarshift.artificial.problem.tsp.crossover.TSPXOrderBased;
import com.klarshift.artificial.problem.tsp.gui.TSPProblemPanel;
import com.klarshift.artificial.problem.tsp.mutation.Displacement;
import com.klarshift.artificial.problem.tsp.mutation.Insertion;
import com.klarshift.artificial.problem.tsp.mutation.Inversion;
import com.klarshift.artificial.problem.tsp.mutation.ReciprocalExchange;
import com.klarshift.artificial.problem.tsp.mutation.SimpleInversion;

/**
 * Genetic Algorithm TSP Solution
 * 
 * solves the TSPProblem with GA Algorithm approach
 * 
 * @author timo
 * 
 */
public class TSPSolutionGA extends TSPSolution implements IGenerationListener {
		
	/* crossover and mutation lists */
	private CrossoverList xList = new CrossoverList();
	private MutationList mList = new MutationList();

	/* distance */
	private double minD;

	private GA ga;

	
	// the problem
	private TSPProblem problem = null;
	private int deltaC = 0;
	
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
	
	
	
	public void setMaxGenerations(Integer maxGenerations){
		setMaxIterations(maxGenerations);
	}
	
	public Integer getMaxGenerations(){
		return getMaxIterations();
	}

	@Override
	public void init(Problem problem) throws Exception {
		super.init(problem);
		
		if (problem instanceof TSPProblem == false) {
			throw new Exception("Cannot solve this problem.");
		}

		// store problem
		this.problem = (TSPProblem) problem;
		
		ga.setFitnessFunction(new TSPFitness(this.problem));
		
	}
	
	public String toString(){
		return "Solution: Genetic Algorithm";
	}
	
	

	public TSPSolutionGA() {
		createGA();		
	}
	
	protected void configureGA(GA ga, MutationList mList, CrossoverList cList){
		// configure
	}
	
	private void createGA(){
		ga = new GA();
		ga.setChromosomeFactory(this);		
		ga.setMutator(mList);
		ga.setCrossover(xList);
		ga.setStopCriterion(this);
		ga.addGenerationListener(this);
		ga.setSelection(new TournamentSelection(0.1));
		
		ga.setPopulationSize(200);
		ga.setMutationRate(0.85f);
		ga.setCrossoverRate(0.45f);				
		ga.setElitismRate(0.05f);

		// crossover
		// xList.add(new PositionBasedCrossover());
		xList.add(new TSPXOrderBased());

		// mutations
		mList.add("Simple Inversion", new SimpleInversion(), 10);
		mList.add("Reciprocal Exchange", new ReciprocalExchange(), 10);
		mList.add("Insertion", new Insertion(), 10);
		mList.add("Displacement", new Displacement(), 10);
		mList.add("Inversion", new Inversion(), 10);
		
		configureGA(ga, mList, xList);
	}
	
	private double getDistance(TSPChromosome c){
		return problem.getTotalDistance(c.getChromosomes());
	}
	

	@Override
	public void onGenerationFinished() {
		double d = getDistance((TSPChromosome) ga.getBestGenome());
		if (d < minD) {
			minD = d;
			deltaC  = 0;
			
			// set current solution to problem
			TSPProblemPanel panel = (TSPProblemPanel) problem.getPanel();				
			TSPChromosome c = ((TSPChromosome)ga.getBestGenome());			
			problem.setCurrentSolution(c.getChromosomes());
			panel.update();
		}	
	}

	@Override
	public void solve() {
		super.solve();
		
		// init
		minD = Double.MAX_VALUE;
		
		// run
		ga.start();
	}
	
	@Override
	public void stopSolving() {
		ga.stop();
	}

	public GA getGA() {
		return ga;
	}

	public MutationList getMutationList() {
		return mList;
	}

	@Override
	public int getIterationCount() {
		return ga.getGeneration() ;
	}
}
