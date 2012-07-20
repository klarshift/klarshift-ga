package com.klarshift.ga.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.ChromosomePair;

public class CrossoverList implements ICrossover {
	private ArrayList<ICrossover> list = new ArrayList<ICrossover>();
	private Random random = new Random();
	
	public void add(ICrossover x){
		list.add(x);
	}

	@Override
	public List<Chromosome> crossover(ChromosomePair pair) {
		ICrossover xSelected = list.get(random.nextInt(list.size()));
		return xSelected.crossover(pair);
	}
}
