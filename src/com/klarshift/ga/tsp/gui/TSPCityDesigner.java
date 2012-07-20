package com.klarshift.ga.tsp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.klarshift.ga.tsp.TSPCity;
import com.klarshift.ga.tsp.TSPProblem;


/**
 * city designer
 * @author timo
 *
 */
public class TSPCityDesigner extends JPanel implements ActionListener {
	private TSPProblem problem;
	private TSPCityPanel cityPanel;
	
	private JPanel controllers;
	private JButton clearButton, randomButton, circleButton, spiralButton;
	
	public TSPCityDesigner(TSPProblem problem){
		this.problem = problem;
		
		initGui();
	}
	
	private void initGui(){
		// set layout
		setLayout(new BorderLayout());
		
		
		// add buttons
		controllers = new JPanel();
		controllers.setLayout(new FlowLayout());
		add(controllers, BorderLayout.NORTH);

		clearButton = createButton("Clear");
		randomButton = createButton("Randomize");
		circleButton = createButton("3 Circles");
		spiralButton = createButton("Spiral");
		
		// add city panel
		cityPanel = new TSPCityPanel(problem);
		add(cityPanel, BorderLayout.CENTER);
		
		cityPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				problem.addCity(new TSPCity(e.getX(), e.getY()));
				cityPanel.updateUI();
			}
		});	
		
		createRandomCities(10);
		problem.init();
		problem.getPanel().update();
	}
	
	private JButton createButton(String text){
		JButton btn = new JButton(text);
		controllers.add(btn);
		
		// add listener
		btn.addActionListener(this);
		
		return btn;
	}
	
	/**
	 * create some random cities
	 * @param num
	 */
	public void createRandomCities(int num){
		problem.clear();
		for(int c=0; c<num; c++){
			problem.addCity(new TSPCity((int)(Math.random()*(cityPanel.getWidth()-20)+10), (int)(Math.random()*(cityPanel.getHeight()-40)+20)));
		}
	}
	
	public void createSpiral() {
		problem.clear();
		double phi = 0;
		double r = 30;
		for(int c=0; c<50; c++){
			int x = (int) (cityPanel.getWidth()/2 + r*Math.cos(phi));
			int y = (int) ((cityPanel.getHeight()/2 + r*Math.sin(phi)));
			problem.addCity(new TSPCity(x, y));
			
			r += 3;
			phi += 0.2;
		}
	}
	
	public void createCircles() {
		problem.clear();
		
		int r = Math.min(cityPanel.getWidth(), cityPanel.getHeight())/2-40;
		createCircle(r, 0.2);
		createCircle(r/2, 0.3);
		createCircle(r/4, 0.4);
		
	}
	
	private void createCircle(int r, double steps){		
		for(double c=0; c<Math.PI*2; c+=steps){
			int x = (int) (cityPanel.getWidth()/2 + r*Math.cos(c));
			int y = (int) (cityPanel.getHeight()/2 + r*Math.sin(c));
			problem.addCity(new TSPCity(x, y));			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn == randomButton){
			createRandomCities((int) (Math.random()*50 + 10));
		}else if(btn ==  circleButton){
			createCircles();
		}else if(btn ==  clearButton){
			problem.clear();
		}else if(btn ==  spiralButton){
			createSpiral();
		}
		
		problem.init();
		problem.getPanel().update();
	}

	public TSPCityPanel getCityPanel() {
		return cityPanel;
	}
}
