package com.klarshift.artificial.modulation;

import com.klarshift.artificial.genetic.GA;

public class LinearModulation implements IMutationModulation{
	private int maxGenerations;
	private double start = 1.0, end = 0.0;
	
	public LinearModulation(double start, double end, int maxGenerations){
		this.start = start;
		this.end = end;
		this.maxGenerations = maxGenerations;
	}
	
	public LinearModulation(int maxGenerations){		
		this.maxGenerations = maxGenerations;
	}

	@Override
	public double modulateMutation(int generation) {
		double m = (end-start)/(double)(maxGenerations);
		double c = start;
		return m*(generation % maxGenerations)+c;
	}

}
