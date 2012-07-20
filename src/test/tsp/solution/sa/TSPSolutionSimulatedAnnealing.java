package test.tsp.solution.sa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import test.tsp.solution.TSPSolution;

import com.klarshift.ga.Problem;
import com.klarshift.ga.gui.SolutionPanel;
import com.klarshift.ga.gui.controller.DoubleSliderController;
import com.klarshift.ga.tsp.TSPProblem;

public class TSPSolutionSimulatedAnnealing extends TSPSolution {
	private static final double ALMOST_ZERO = 0.0001;
	private Double startTemperature = 1000.0;
	private boolean running = false;
	private TSPProblem problem;
	private ArrayList<Integer> currentOrder;
	private Double dampFactor = 0.999;
	private int iteration = 0;
	private double minDistance = 0;
	private Random random = new Random();
	
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
			panel.add(new DoubleSliderController(this, "dampFactor", 0.9, 0.99999));
		}
		return panel;
	}
	
	@Override
	public void init(Problem problem) throws Exception {	
		super.init(problem);
		this.problem = (TSPProblem) problem;
	}
	
	public void setDampFactor(Double damp){
		this.dampFactor = damp;
	}
	
	public Double getDampFactor(){
		return dampFactor;
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
		double distance = problem.getTotalDistance(currentOrder);
		while(temp > ALMOST_ZERO && running){
			ArrayList<Integer> nextOrder = nextOrder(currentOrder);
			double nD = problem.getTotalDistance(nextOrder) - distance;

			if(nD < 0 || (nD > 0 && Math.exp(-nD / temp) > Math.random())){
				currentOrder = nextOrder;
				distance += nD;
				minDistance = distance;	
				
				problem.setCurrentSolution(currentOrder);
				problem.getPanel().update();
			}
			
			temp *= dampFactor;
			iteration++;
		}
	}
	
	private ArrayList<Integer> initialOrder(){
		ArrayList<Integer> order = new ArrayList<Integer>();
		for(int i=0; i<problem.getCityCount(); i++){
			order.add(i);
		}
		Collections.shuffle(order);
		return order;
	}
	
	private ArrayList<Integer> nextOrder(ArrayList<Integer> list){
		ArrayList<Integer> order = new ArrayList<Integer>(list);
		int i = random.nextInt(list.size());
		int j = random.nextInt(list.size());
		swap(order, i, j);
		return order;
	}
	
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

}
