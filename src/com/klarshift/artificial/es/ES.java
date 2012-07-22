package com.klarshift.artificial.es;

import org.apache.log4j.Logger;

import com.klarshift.artificial.IAlgorithm;
import com.klarshift.artificial.IChromosomeFactory;
import com.klarshift.artificial.IFitnessFunction;
import com.klarshift.artificial.IStopCriteria;
import com.klarshift.artificial.Population;

public abstract class ES implements IAlgorithm {
	private IFitnessFunction fitness;
	private IChromosomeFactory factory;
	private IStopCriteria stopCriteria;
	
	private boolean running = false;
	private int generation = 0;
	
	protected final Logger log = Logger.getLogger(this.getClass());
	
	protected Population currentPopulation = new Population();
	
	public ES(){
		
	}
	
	public Integer getGeneration(){
		return generation;
	}
	
	public void setFitnessFunction(IFitnessFunction fitness){
		this.fitness = fitness;
	}
	
	public void setFitnessFunction(IChromosomeFactory factory){
		this.factory = factory;
	}
	
	public void setStopCriteria(IStopCriteria stopCriteria){
		this.stopCriteria = stopCriteria;
	}
	
	private void evolve(){
		// validate
		if(!validate()){
			log.error("Stopped due validation errors.");			
			return;
		}
		
		// init
		init();
		
		// create seed generation
		seed();	rank();			
				
		while(running && ((stopCriteria == null) || (stopCriteria.shouldStop() == false))){
			generationStep();
		}
	}
	
	
	private void generationStep(){			
		// select which survive to next
		// generation
		select();
		
		// build up new generation based
		// on survivors
		populate();
		
		// rank everything
		rank();
		
		// finish current generation
		onGenerationFinished();
	}
	
	protected abstract void select();
	protected abstract void populate();
	
	protected void init(){
		log.info("Init " + this);
		generation = 0;
		currentPopulation.clear();
	}
	
	protected void onGenerationFinished(){
		log.debug("Finished Generation " + generation);
		generation++;		
	}
	
	private void rank(){
		currentPopulation.rank(fitness);
	}
	
	protected boolean validate(){
		if(fitness == null){
			log.error("No Fitness Function given.");
			return false;
		}
		return true;
	}	
	
	protected abstract void seed();	

	@Override
	public void start() {
		if(!running){
			running = true;
			evolve();
			running = false;
		}
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}
}
