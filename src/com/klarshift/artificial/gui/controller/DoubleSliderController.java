package com.klarshift.artificial.gui.controller;

import com.klarshift.artificial.gui.SliderController;


public class DoubleSliderController extends SliderController {
	private double minValue = 0, maxValue = 1;

	public DoubleSliderController(Object object, String propertyName, double minValue, double maxValue) {
		super(object, propertyName, Double.class);
		this.maxValue = maxValue;
		this.minValue = minValue;	
		
		init();
	}	
	
	public DoubleSliderController(Object object, String propertyName, double maxValue) {
		super(object, propertyName, Double.class);
		this.maxValue = maxValue;
		this.minValue = 0;
		init();
	}
	
	public DoubleSliderController(Object object, String propertyName) {
		super(object, propertyName, Double.class);	
		init();
	}

	@Override
	protected Double normalize(Object object) {
		Double v = (Double) object;
		return ((double)(v-minValue)) / ((double)(maxValue-minValue));
	}

	@Override
	public Object getValue() {
		return new Double( (getNorm() * (maxValue-minValue))+minValue);
	}
}