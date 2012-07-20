package com.klarshift.ga.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.ChromosomePair;

/**
 * mutation list
 * 
 * mutations can be added and they will be selected randomly
 * 
 * @author timo
 * 
 */
public class MutationList implements IMutation {
	private ArrayList<IMutation> list = new ArrayList<IMutation>();
	private Random random = new Random();

	public void add(IMutation x) {
		list.add(x);
	}

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		IMutation xSelected = list.get(random.nextInt(list.size()));
		xSelected.mutate(chromosome, stdDev);
	}
}
