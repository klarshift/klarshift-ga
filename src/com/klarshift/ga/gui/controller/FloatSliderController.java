package com.klarshift.ga.gui.controller;

import com.klarshift.ga.gui.SliderController;


public class FloatSliderController extends SliderController {
	private float minValue = 0f, maxValue = 1f;

	public FloatSliderController(Object object, String propertyName, float minValue, float maxValue) {
		super(object, propertyName, Float.class);
		this.maxValue = maxValue;
		this.minValue = minValue;	
		
		init();
	}	
	
	public FloatSliderController(Object object, String propertyName, float maxValue) {
		super(object, propertyName, Float.class);
		this.maxValue = maxValue;
		this.minValue = 0;
		init();
	}
	
	public FloatSliderController(Object object, String propertyName) {
		super(object, propertyName, Float.class);	
		init();
	}

	@Override
	protected Double normalize(Object object) {
		Float v = (Float) object;
		return ((double)(v-minValue)) / ((double)(maxValue-minValue));
	}

	@Override
	public Object getValue() {
		return new Float( (getNorm() * (maxValue-minValue))+minValue);
	}
}