package com.klarshift.artificial.gui;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.klarshift.artificial.genetic.mutation.MutationList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MutationPanelItem extends JPanel {
	JLabel lblValue;
	JSlider slider;
	
	private String key;
	private MutationList mList;
	int oldValue;

	/**
	 * Create the panel.
	 */
	public MutationPanelItem(String key, MutationList mList) {
		this.key = key;
		this.mList = mList;
		
		double prob = mList.getMap().getProbability(key);
		
		
		setLayout(new BorderLayout(0, 0));
		
		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		
		lblValue = DefaultComponentFactory.getInstance().createLabel("10 %");
		panel.add(lblValue, BorderLayout.EAST);
		lblValue.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				update();				
			}
		});
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setValue((int) prob);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		add(slider, BorderLayout.CENTER);
		
		final JCheckBox checkBox = new JCheckBox(key, prob > 0 ? true : false);
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox.isSelected()){
					slider.setValue(oldValue);
					slider.setEnabled(true);
				}else{
					oldValue = slider.getValue();
					slider.setValue(0);
					slider.setEnabled(false);
				}
				update();
			}
		});
		panel.add(checkBox, BorderLayout.WEST);
	}	

	private void update(){
		mList.getMap().setProbability(key, slider.getValue());
		lblValue.setText(slider.getValue() + " %");
	}
}
