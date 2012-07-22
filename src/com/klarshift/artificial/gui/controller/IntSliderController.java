package com.klarshift.artificial.gui.controller;

import com.klarshift.artificial.gui.SliderController;


public class IntSliderController extends SliderController {
	private int minValue, maxValue;

	public IntSliderController(Object object, String propertyName, int minValue, int maxValue) {
		super(object, propertyName, Integer.class);
		this.maxValue = maxValue;
		this.minValue = minValue;	
		
		init();
	}	
	
	public IntSliderController(Object object, String propertyName, int maxValue) {
		super(object, propertyName, Integer.class);
		this.maxValue = maxValue;
		this.minValue = 0;
		
		init();
	}

	@Override
	protected Double normalize(Object object) {
		Integer v = (Integer) object;
		return ((double)(v-minValue)) / ((double)(maxValue-minValue));
	}

	@Override
	public Object getValue() {
		return new Integer((int) (getNorm() * (maxValue-minValue))+minValue);
	}
}