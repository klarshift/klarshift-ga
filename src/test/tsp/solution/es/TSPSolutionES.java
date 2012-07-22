package test.tsp.solution.es;

import test.tsp.solution.TSPSolution;

import com.klarshift.artificial.Population;
import com.klarshift.artificial.Problem;
import com.klarshift.artificial.es.ES;
import com.klarshift.artificial.genetic.mutation.MutationList;
import com.klarshift.artificial.gui.SolutionPanel;
import com.klarshift.artificial.problem.tsp.TSPChromosome;
import com.klarshift.artificial.problem.tsp.TSPFitness;
import com.klarshift.artificial.problem.tsp.TSPProblem;
import com.klarshift.artificial.problem.tsp.mutation.Displacement;
import com.klarshift.artificial.problem.tsp.mutation.Insertion;
import com.klarshift.artificial.problem.tsp.mutation.Inversion;
import com.klarshift.artificial.problem.tsp.mutation.ReciprocalExchange;
import com.klarshift.artificial.problem.tsp.mutation.SimpleInversion;

/**
 * evolutionary solution
 * 
 * @author timo
 * 
 */
public class TSPSolutionES extends TSPSolution {
	private TSPES es;
	private TSPProblem problem;
	private MutationList mList = new MutationList();
	private double minD = Double.MAX_VALUE;
	
	double std = 1;

	public TSPSolutionES() {
		es = new TSPES();

		// mutations
		mList.add("Simple Inversion", new SimpleInversion(), 10);
		mList.add("Reciprocal Exchange", new ReciprocalExchange(), 10);
		mList.add("Insertion", new Insertion(), 10);
		mList.add("Displacement", new Displacement(), 10);
		mList.add("Inversion", new Inversion(), 10);
	}

	@Override
	public void init(Problem problem) throws Exception {
		super.init(problem);
		this.problem = (TSPProblem) problem;
		
		

		// set fitness function
		es.setFitnessFunction(new TSPFitness((TSPProblem) problem));
		es.setStopCriteria(this);
	}

	@Override
	protected void solve() {
		super.solve();
		minD = Double.MAX_VALUE;
		std = 1;
		
		
		es.start();
	}

	@Override
	public void stopSolving() {
		es.stop();
	}

	@Override
	public SolutionPanel getPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return "Solution: TSP Evolutionary Strategy";
	}

	/**
	 * the evolutionary strategy
	 * 
	 * @author timo
	 * 
	 */
	private class TSPES extends ES {

		@Override
		protected void seed() {
			currentPopulation.clear();
			TSPChromosome first = (TSPChromosome) createChromosome();
			currentPopulation.add(first);
			log.info("ES seeding: " + first);
		}

		@Override
		protected void select() {
			// select the best one and shift over
			// to next generation
			Population next = new Population();
			next.add(currentPopulation.getBest());
			currentPopulation = next;
					
		}

		@Override
		protected void populate() {
			log.debug("Populating ES ...");
			
			TSPChromosome c = (TSPChromosome) currentPopulation.getBest();
			
			for(int n=0; n<10; n++){
				TSPChromosome mutant = (TSPChromosome) c.clone();
				mList.mutate(mutant, std);
											
				currentPopulation.add(mutant);
			}
		}

		@Override
		protected void onGenerationFinished() {
			super.onGenerationFinished();

			// set solution and update
			// problem panel gui
			TSPChromosome c = (TSPChromosome) currentPopulation.getBest();
			problem.setCurrentSolution(c.getChromosomes());
			double d = problem.getCurrentDistance();
			if(d < minD){
				minD = d;
				problem.getPanel().update();	
			}
			
			std *= 0.999;
			
		}

		
	}

	@Override
	public int getIterationCount() {
		return es.getGeneration();
	}

}
