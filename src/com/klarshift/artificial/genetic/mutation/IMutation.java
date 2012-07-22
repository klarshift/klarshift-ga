package com.klarshift.artificial.genetic.mutation;

import com.klarshift.artificial.genetic.Chromosome;



public interface IMutation {
	public void mutate(Chromosome chromosome, double stdDev);
}
