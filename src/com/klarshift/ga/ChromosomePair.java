package com.klarshift.ga;

/**
 * chromosome pair
 * @author timo
 *
 */
public class ChromosomePair {
	Chromosome first, second;
	
	public ChromosomePair(Chromosome first, Chromosome second){
		this.first = first;
		this.second = second;
	}	
	
	public ChromosomePair(ChromosomePair chromosomePair) {
		this.first = chromosomePair.getFirst().clone();
		this.second = chromosomePair.getSecond().clone();
	}

	public Chromosome getFirst(){
		return first;
	}
	
	public Chromosome getSecond(){
		return second;
	}
	
	public ChromosomePair clone(){
		return new ChromosomePair(this);
	}
	
	public String toString(){
		return "Pair\r\n\t* " + first + "\r\n\t* " + second;
	}
}
