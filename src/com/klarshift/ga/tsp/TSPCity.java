package com.klarshift.ga.tsp;

public class TSPCity {
	private int x, y;
	
	public TSPCity(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public double getDistance(TSPCity otherCity){
		double d1 = x - otherCity.getX();
		double d2 = y - otherCity.getY();
		return Math.sqrt(d1*d1+d2*d2);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return "" + x + "/" + y;
	}
}
