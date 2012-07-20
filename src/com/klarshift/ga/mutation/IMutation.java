package com.klarshift.ga.mutation;

import com.klarshift.ga.Chromosome;



public interface IMutation {
	public void mutate(Chromosome chromosome, double stdDev);
}
