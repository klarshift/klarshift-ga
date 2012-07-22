package com.klarshift.artificial.genetic.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.klarshift.artificial.IGenerationListener;
import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.gui.ModulationIndicator;

/**
 * genetic algorithm panel
 * 
 * @author timo
 * 
 */
public class GAInfoPanel extends JPanel {
	private final GA ga;
	private JLabel generationLbl;
	private JLabel stdDevLbl, popLbl;

	/**
	 * create panel
	 * 
	 * @param ga
	 */
	public GAInfoPanel(GA _ga) {
		this.ga = _ga;
		setLayout(new FlowLayout());
		
		generationLbl = createLabel("generations: ");
		
		popLbl = createLabel("population: ");
		add(new ModulationIndicator(_ga));
		
		ga.addGenerationListener(new IGenerationListener() {
			
			@Override
			public void onGenerationFinished() {
				generationLbl.setText("generations: " + ga.getGeneration());
				//stdDevLbl.setText("stdDev: " + ga.getStdDeviation());
				popLbl.setText("population: " + ga.getPopulationSize());
			}
		});
		
		setPreferredSize(new Dimension(300, 50+4*20));
	}
	
	private JLabel createLabel(String text){
		JLabel lbl = new JLabel(text);
		lbl.setPreferredSize(new Dimension(300, 20));
		add(lbl);
		return lbl;
	}

}
