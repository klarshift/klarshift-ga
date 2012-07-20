package com.klarshift.ga.tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;


import com.klarshift.ga.Problem;
import com.klarshift.ga.gui.ProblemPanel;
import com.klarshift.ga.tsp.gui.TSPProblemPanel;

/**
 * traveling salesman problem
 * 
 * @author timo
 *
 */
public class TSPProblem extends Problem {
	private double dMatrix[][] = null;
	private int cityCount = -1;
	private File file = null;
	private ArrayList<TSPCity> cities = new ArrayList<TSPCity>();
	
	TSPProblemPanel panel;
	
	private ArrayList<Integer> currentSolution = null;
	
	public void setCurrentSolution(ArrayList<Integer> solution){
		this.currentSolution = solution;			
	}
	
	public double getCurrentDistance(){
		if(currentSolution != null){
			return getTotalDistance(currentSolution);
		}
		
		return -1;
	}
	
	public ArrayList<Integer> getCurrentSolution(){
		return currentSolution;
	}
	
	/**
	 * create a new tsp problem
	 */
	public TSPProblem(){
		
	}
	
	
	
	public void clear(){
		dMatrix = null;
		cities.clear();
		file = null;
		cityCount = -1;
		currentSolution = null;
	}
	
	public TSPProblem(File file){
		setFile(file);		
	}
	
	private void calculateMatrixFromCities(){
		cityCount = cities.size();
		Collections.shuffle(cities);
		dMatrix = new double[cityCount][cityCount];
		for(int i=0; i<cityCount; i++){
			for(int j=0; j<cityCount; j++){				
				dMatrix[i][j] = cities.get(i).getDistance(cities.get(j));
			}
		}
	}
	
	public String toString(){
		return "Problem: Traveling Salesman";
	}
	
	@Override
	public ProblemPanel getPanel() {
		if(panel == null){
			panel = new TSPProblemPanel(this);
		}
		return panel;
	}
	
	
	public int getCityCount(){
		return cityCount;
	}
	
	public void chooseFile(){
		File cwd = new File(System.getProperty("user.dir"));
		JFileChooser chooser = new JFileChooser(cwd);
		chooser.showOpenDialog(null);
		File selected = chooser.getSelectedFile();
		setFile(selected);		
	}
	
	public double getTotalDistance(ArrayList<Integer> order) {
		int size = order.size();
		
		// calculate total distance
		double maxD = 0;
		for (int j = 0; j < size - 1; j++) {
			int a = order.get(j);
			int b = order.get(j + 1);
			maxD += getDistance(a, b);
		}

		// cycle
		maxD += getDistance(order.get(size - 1), order.get(0));

		return maxD;
	}
	
	public void setFile(File file){
		if(file != null){
			log("Set file " + file);
			clear();
			this.file = file;	
			init();
		}
	}
	
	private void loadDistanceMatrix(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		int lo = 0;
		while((line = reader.readLine()) != null){
			ArrayList<String> c = new ArrayList<String>();
			StringTokenizer t = new StringTokenizer(line,  " " );
			while(t.hasMoreTokens()){
				c.add(t.nextToken());
			}
			
			if(dMatrix == null){
				int s = c.size();
				dMatrix = new double[s][s];
				cityCount = s;
			}
			
			for(int d=0; d<c.size(); d++){
				dMatrix[lo][d] = Double.parseDouble(c.get(d));
			}
			
			// line offset
			lo++;
		}
		reader.close();
		log("Read Matrix");
	}

	@Override
	public void init() {
		// distance matrix from file
		if(file != null){
			try {
				loadDistanceMatrix(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(cities.size() > 0){
			calculateMatrixFromCities();
		}
	}

	public double getDistance(int a, int b) {
		return dMatrix[a][b];
	}

	public void addCity(TSPCity tspCity) {
		cities.add(tspCity);
		System.out.println("ADDED CITY " + tspCity);
	}

	public ArrayList<TSPCity> getCities() {
		return cities;
	}

	public File getFile() {
		return file;
	}

	
}
