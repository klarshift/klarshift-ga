package com.klarshift.ga.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.klarshift.ga.gui.controller.BeanController;

public abstract class SliderController extends JPanel {
	private JSlider slider;
	private BeanController bc;
	private Double normVal = new Double(0);
	private int scale = 500;
	private String propertyName;

	private JLabel valueLabel;

	

	public SliderController(Object object, String propertyName, Class type) {
		// get bean controller
		bc = new BeanController(object, propertyName, type);
		this.propertyName = propertyName;	
	}
	
	private int sliderScale(double s) {
		return (int) (s * scale);
	}

	public void setScale(int scale) {
		this.scale = scale;
		slider.setMaximum(scale);
	}

	protected void init() {
		// get value from bean
		Object beanValue = bc.getValue();
		Object realValue = sliderScale(normalize(beanValue));

		setLayout(new BorderLayout());

		slider = new JSlider(0, scale, (int) realValue);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// update norm
				updateValue();
			}
		});
		add(slider, BorderLayout.CENTER);

		JLabel lbl = new JLabel(propertyName);
		add(lbl, BorderLayout.NORTH);

		valueLabel = new JLabel(realValue.toString());
		valueLabel.setPreferredSize(new Dimension(60, 20));
		add(valueLabel, BorderLayout.EAST);

		updateValue();
		setSize(new Dimension(200, 20));
		setPreferredSize(new Dimension(200, 40));
		setVisible(true);
	}

	protected Double getNorm() {
		return normVal;
	}

	private void updateValue() {
		normVal = (double) (slider.getValue() / (double) scale);
		System.out.println(normVal);
		onChange();
	}

	private void onChange() {
		Object value = getValue();
		valueLabel.setText(value.toString());
		bc.setValue(value);
	}

	protected abstract Double normalize(Object object);
	protected abstract Object getValue();
}
