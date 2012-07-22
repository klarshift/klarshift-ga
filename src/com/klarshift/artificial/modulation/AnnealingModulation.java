package com.klarshift.artificial.modulation;

import com.klarshift.artificial.genetic.GA;

public class AnnealingModulation implements IMutationModulation{
	private int maxGenerations;
	private double start = 1.0, end = 0.0;
	
	public AnnealingModulation(double start, double end, int maxGenerations){
		this.start = start;
		this.end = end;
		this.maxGenerations = maxGenerations;
	}
	
	public AnnealingModulation(int maxGenerations){		
		this.maxGenerations = maxGenerations;
	}

	@Override
	public double modulateMutation(int generation) {
		double k = 0.001;
		return end + (start-end)*Math.exp(-k*(generation));		
	}

}
