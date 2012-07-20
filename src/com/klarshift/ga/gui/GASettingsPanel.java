package com.klarshift.ga.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.klarshift.ga.GA;
import com.klarshift.ga.gui.controller.BooleanController;
import com.klarshift.ga.gui.controller.DoubleSliderController;
import com.klarshift.ga.gui.controller.FloatSliderController;
import com.klarshift.ga.gui.controller.IntSliderController;

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
		add(new BooleanController(ga, "elitism"));
		add(new IntSliderController(ga, "populationSize", 1, 1000));
		add(new FloatSliderController(ga, "crossoverRate"));
		add(new FloatSliderController(ga, "mutationRate"));
		add(new DoubleSliderController(ga, "stdDeviation"));
		add(new DoubleSliderController(ga, "stdDampRate", 0.9, 0.99999));
		setVisible(true);
	}

}
