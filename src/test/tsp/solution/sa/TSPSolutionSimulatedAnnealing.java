package test.tsp.solution.sa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import test.tsp.solution.TSPSolution;

import com.klarshift.artificial.Problem;
import com.klarshift.artificial.gui.SolutionPanel;
import com.klarshift.artificial.gui.controller.DoubleSliderController;
import com.klarshift.artificial.gui.controller.IntSliderController;
import com.klarshift.artificial.problem.tsp.TSPProblem;

public class TSPSolutionSimulatedAnnealing extends TSPSolution {
	private static final double ALMOST_ZERO = 0.0001;
	private Double startTemperature = 1000.0;
	private boolean running = false;
	private TSPProblem problem;
	private ArrayList<Integer> currentOrder;
	private Double coolingRate = 0.999;
	private int iteration = 0;
	private double minDistance = 0;
	private Random random = new Random();
	private int keepFactor = 10;
	
	private SolutionPanel panel;
	
	public TSPSolutionSimulatedAnnealing() {
		
	}
	
	public Double getMinDistance(){
		return minDistance;
	}
	
	@Override
	public SolutionPanel getPanel() {
		if(panel == null){
			panel = new SolutionPanel(this);
			panel.setTitle("Solution: Simulated Annealing");
			
			panel.add(new DoubleSliderController(this, "temperature", 50, 5000));
			panel.add(new IntSliderController(this, "keepFactor", 0, 100));
			panel.add(new DoubleSliderController(this, "coolingRate", 0.9, 0.99999));
		}
		return panel;
	}
	
	public void setKeepFactor(Integer f){
		this.keepFactor = f;
	}
	
	public Integer getKeepFactor(){
		return keepFactor;
	}
	
	@Override
	public void init(Problem problem) throws Exception {	
		super.init(problem);
		this.problem = (TSPProblem) problem;
	}
	
	public void setCoolingRate(Double damp){
		this.coolingRate = damp;
	}
	
	public Double getCoolingRate(){
		return coolingRate;
	}
	
	
	public void setTemperature(Double temperature){
		this.startTemperature = temperature;
	}
	
	public Double getTemperature(){
		return startTemperature;
	}
	
	public Integer getIteration(){
		return iteration;
	}

	@Override
	protected void solve() {
		iteration = 0;
		running = true;
		minDistance = 0;
		
		currentOrder = initialOrder();
		double temp = new Double(startTemperature);
		problem.setCurrentSolution(null);
		double distance = problem.getTotalDistance(currentOrder);
		int tf = 0;
		while(temp > ALMOST_ZERO && running && !shouldStop()){
			log.debug("Iteration " + iteration + " / t=" + temp);
			ArrayList<Integer> nextOrder = nextOrder(currentOrder);
			double nD = problem.getTotalDistance(nextOrder) - distance;

			if(nD < 0 || (nD > 0 && Math.exp(-nD / temp) > Math.random())){
				currentOrder = nextOrder;
				distance += nD;
				minDistance = distance;	
				
				problem.setCurrentSolution(currentOrder);
				problem.getPanel().update();
			}
			
			if(tf++ > keepFactor){
				temp *= coolingRate;
				tf = 0;
			}
			
			iteration++;
		}
	}
	
	/**
	 * create initial order
	 * @return
	 */
	private ArrayList<Integer> initialOrder(){
		ArrayList<Integer> order = new ArrayList<Integer>();
		for(int i=0; i<problem.getCityCount(); i++){
			order.add(i);
		}
		Collections.shuffle(order);
		return order;
	}
	
	/**
	 * create next arrangement
	 * @param list
	 * @return
	 */
	private ArrayList<Integer> nextOrder(ArrayList<Integer> list){
		log.debug("Creating next arrangement ...");
		ArrayList<Integer> order = new ArrayList<Integer>(list);
		int i = random.nextInt(list.size());
		int j = random.nextInt(list.size());
		swap(order, i, j);
		return order;
	}
	
	/**
	 * swap order
	 * @param list
	 * @param i
	 * @param j
	 */
	private void swap(ArrayList<Integer> list, int i, int j){		
		int b = list.get(i);
		list.set(i, list.get(j));
		list.set(j, b);
	}

	@Override
	public void stopSolving() {		
		running = false;
	}
	
	public String toString(){
		return "Solution: TSP with Simulated Annealing";
	}

	@Override
	public int getIterationCount() {
		return iteration;
	}

}
