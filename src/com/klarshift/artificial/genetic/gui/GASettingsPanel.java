package com.klarshift.artificial.genetic.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.gui.controller.BooleanController;
import com.klarshift.artificial.gui.controller.DoubleSliderController;
import com.klarshift.artificial.gui.controller.FloatSliderController;
import com.klarshift.artificial.gui.controller.IntSliderController;

/**
 * genetic algorithm panel
 * 
 * @author timo
 * 
 */
public class GASettingsPanel extends JPanel {
	private GA ga;

	/**
	 * create panel
	 * 
	 * @param ga
	 */
	public GASettingsPanel(GA ga) {
		this.ga = ga;
		setLayout(new FlowLayout());
		add(new FloatSliderController(ga, "elitismRate"));
		add(new IntSliderController(ga, "populationSize", 1, 1000));
		add(new FloatSliderController(ga, "crossoverRate"));
		add(new FloatSliderController(ga, "mutationRate"));
		//add(new DoubleSliderController(ga, "stdDeviation"));
		//add(new DoubleSliderController(ga, "stdDampRate", 0.9, 0.99999));
		
		
		
		setVisible(true);
	}

}
