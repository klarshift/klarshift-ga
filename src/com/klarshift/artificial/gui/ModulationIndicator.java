package com.klarshift.artificial.gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.klarshift.artificial.IGenerationListener;
import com.klarshift.artificial.genetic.GA;

public class ModulationIndicator extends JPanel {
	private final GA ga;
	private JProgressBar progressBar;

	/**
	 * Create the panel.
	 */
	public ModulationIndicator(final GA ga) {
		this.ga = ga;
		ga.addGenerationListener(new IGenerationListener() {
			
			@Override
			public void onGenerationFinished() {
				progressBar.setValue((int) (ga.getModulationRate()*100));
			}
		});
		
		setLayout(null);
		
		JLabel lblModulation = DefaultComponentFactory.getInstance().createLabel("Modulation:");
		lblModulation.setBounds(0, 0, 86, 29);
		add(lblModulation);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(104, 6, 281, 17);
		progressBar.setStringPainted(true);
		progressBar.setValue(50);
		add(progressBar);
		
		setPreferredSize(new Dimension(300, 20));
		setVisible(true);

	}

}
