package com.klarshift.artificial.genetic.mutation;

import java.util.Random;
import java.util.UUID;

import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.util.RandomMap;

/**
 * mutation list
 * 
 * mutations can be added and they will be selected randomly
 * 
 * @author timo
 * 
 */
public class MutationList implements IMutation {
	//private ArrayList<IMutation> list = new ArrayList<IMutation>();
	private Random random = new Random();
	
	private RandomMap rMap = new RandomMap();

	public void add(IMutation x) {
		rMap.put(UUID.randomUUID().toString(), x, 1);
	}
	
	public void add(IMutation x, double p) {
		rMap.put(UUID.randomUUID().toString(), x, p);
	}
	
	public void add(String key, IMutation x, double p) {
		rMap.put(key, x, p);
	}
	
	public RandomMap getMap(){
		return rMap;
	}

	@Override
	public void mutate(Chromosome chromosome, double stdDev) {
		IMutation xSelected = (IMutation) rMap.next();
		if(xSelected != null){
			xSelected.mutate(chromosome, stdDev);
		}
	}
}
