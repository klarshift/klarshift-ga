package com.klarshift.artificial.util;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * weighted random map
 * 
 * @author timo
 *
 */
public class RandomMap {
	private HashMap<String,RandomItem> items = new HashMap<String,RandomItem>();
	private boolean update = false;
	private double total = -1;
	private Random random = new Random();
	
	public RandomMap(){
		
	}
	
	public Set<String> getKeys(){
		return items.keySet();
	}
	
	public void setProbability(String key, double p){
		items.get(key).p = p;
	}
	
	public double getProbability(String key){
		return items.get(key).p;
	}
	
	public Object getObject(String key){
		return items.get(key);
	}
	
	public void remove(String key){
		items.remove(key);
		update = true;
	}
	
	public int size(){
		return items.size();
	}
	
	public void put(String key, Object o, double p){
		items.put(key, new RandomItem(p, o));
		update = true;
	}
	
	public Object next(){
		if(update){
			update();
		}
			
		double u = random.nextDouble()*total;
		double t = 0;
		for(RandomItem ri : items.values()){
			t += ri.p;
			if(t >= u){
				return ri.o;
			}
		}
				
		return null;
	}
	
	private void update(){
		// calculate total sum
		total = 0;
		for(RandomItem ri : items.values()){
			total += ri.p;
		}
		
		// 
		update = false;
	}
	
	private class RandomItem{
		double p;
		Object o;
		public RandomItem(double p, Object o){
			this.p = p;
			this.o = o;
		}
	}
	
	public static void main(String[] args){
		RandomMap rm = new RandomMap();
		
		rm.put("X", "X", 0.2);
		rm.put("Y", "Y", 0.7);
		rm.put("Z", "Z", 0.1);
		
		
		System.out.println("**");
		for(int j=0; j<100; j++){
			System.out.println(rm.next());
			
		}
	}
}
