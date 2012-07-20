package com.klarshift.ga.gui.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BooleanController extends JPanel {
	
	private BeanController bc;	
	private String propertyName;

	private JLabel valueLabel;
	private JCheckBox checkBox;
	
	private boolean value;
	

	public BooleanController(Object object, String propertyName) {
		// get bean controller
		bc = new BeanController(object, propertyName, Boolean.class);
		this.propertyName = propertyName;	
		init();
	}
	
	

	protected void init() {
		// get value from bean
		Object beanValue = bc.getValue();


		setLayout(new BorderLayout());

		// add checkbox
		checkBox = new JCheckBox(propertyName, null, (boolean) beanValue);
		checkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateValue();
				onChange();
			}
		});
		add(checkBox, BorderLayout.CENTER);	

		updateValue();
		setSize(new Dimension(200, 20));
		setPreferredSize(new Dimension(200, 40));
		setVisible(true);
	}

	private void updateValue() {
		value = checkBox.isSelected();
	}

	private void onChange() {		
		bc.setValue(value);
	}

}
