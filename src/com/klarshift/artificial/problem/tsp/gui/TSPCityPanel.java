package com.klarshift.artificial.problem.tsp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.klarshift.artificial.problem.tsp.TSPCity;
import com.klarshift.artificial.problem.tsp.TSPProblem;


public class TSPCityPanel extends JPanel{

	private TSPProblem problem;

	public TSPCityPanel(TSPProblem problem) {
		this.problem = problem;
		
		
		setPreferredSize(new Dimension(400, 400));
		setSize(new Dimension(400, 400));
		setVisible(true);
		updateUI();
	}
	
	public ArrayList<TSPCity> getCities(){
		return problem.getCities();
	}
	
	
	
	@Override
	public void paint(Graphics g) {			
		super.paint(g);
		
		ArrayList<Integer> solution = problem.getCurrentSolution();
		
		// clear
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// intro label
		g.setColor(Color.BLACK);
		g.drawString("Click to drop cities ...", 10, 20);
		
		
		
		ArrayList<TSPCity> cities = problem.getCities();
		if(cities.size() < 1)return;
		
		
		
		// draw connections
		if(solution != null){
			g.setColor(Color.RED);
			for(int i=0; i<solution.size()-1; i++){
				TSPCity c1 = cities.get(solution.get(i));
				TSPCity c2 = cities.get(solution.get(i+1));
				g.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY());
			}	
			g.drawLine(cities.get(solution.get(0)).getX(), cities.get(solution.get(0)).getY(), cities.get(solution.get(solution.size()-1)).getX(), cities.get(solution.get(solution.size()-1)).getY());

		}
		
		
		// draw cities
		int s = 10;
		int s2 = s/2;
		Integer id = 0;
		g.setColor(Color.BLUE);
		for(TSPCity city : cities){			
			g.drawString(id.toString(), city.getX()-s2, city.getY()-10);
			id++;
			g.fillOval(city.getX()-s2, city.getY()-s2, s, s);
		}			
	}



	public void drawTour(ArrayList<Integer> solution, Color c) {
		ArrayList<TSPCity> cities = getCities();
		if(cities.size() < 1)return;
		

		
		
	}
}
