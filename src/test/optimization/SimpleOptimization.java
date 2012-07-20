package test.optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.klarshift.ga.Chromosome;
import com.klarshift.ga.ChromosomePair;
import com.klarshift.ga.GA;
import com.klarshift.ga.IFitnessFunction;
import com.klarshift.ga.IGenomeFactory;
import com.klarshift.ga.IStopCriterion;
import com.klarshift.ga.crossover.ICrossover;
import com.klarshift.ga.genome.ValueChromosomeDouble;
import com.klarshift.ga.mutation.IMutation;

public class SimpleOptimization implements IGenomeFactory, ICrossover, IStopCriterion, IFitnessFunction, IMutation {
	GA ga = new GA();
	
	public SimpleOptimization() {
		ga.setGenomeFactory(this);
		ga.setFitnessFunction(this);
		ga.setMutator(this);
		ga.setStopCriterion(this);	
		ga.setCrossover(this);
		
		
		ga.setMutationRate(0.05f);
		
		ga.start();
		
		System.out.println(ga.getBestGenome());
		System.out.println(ga.getGeneration());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SimpleOptimization();
	}

	@Override
	public void mutate(Chromosome genome, double stdDev) {
		Random r = new Random();
		ValueChromosomeDouble g = (ValueChromosomeDouble) genome;
		
		int i = r.nextInt(1);
		double v = g.get(i);
		g.set(i, v + (r.nextDouble()-0.5)*0.1);
		
	}

	@Override
	public List<Chromosome> crossover(ChromosomePair pair) {
		ArrayList<Chromosome> list = new ArrayList<Chromosome>();
		list.add(pair.getFirst()); list.add(pair.getSecond());
		return list;
	}

	@Override
	public double fitness(Chromosome genome) {
		ValueChromosomeDouble g = ((ValueChromosomeDouble)genome);
		double x1 = g.get(0);

		
		double e = -Math.pow(x1, 2)+1;
		
		return e;
		
		//return Math.random();
	}

	@Override
	public boolean shouldStop() {
		return ga.getGeneration() >= 1000;
	}

	@Override
	public Chromosome createGenome() {
		return new ValueChromosomeDouble(1);
	}

}
