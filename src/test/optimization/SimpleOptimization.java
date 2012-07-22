package test.optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.klarshift.artificial.IChromosomeFactory;
import com.klarshift.artificial.IFitnessFunction;
import com.klarshift.artificial.IStopCriteria;
import com.klarshift.artificial.genetic.Chromosome;
import com.klarshift.artificial.genetic.ChromosomePair;
import com.klarshift.artificial.genetic.DoubleChromosome;
import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.crossover.ICrossover;
import com.klarshift.artificial.genetic.mutation.IMutation;

public class SimpleOptimization implements IChromosomeFactory, ICrossover, IStopCriteria, IFitnessFunction, IMutation {
	GA ga = new GA();
	
	public SimpleOptimization() {
		ga.setChromosomeFactory(this);
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
		DoubleChromosome g = (DoubleChromosome) genome;
		
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
		DoubleChromosome g = ((DoubleChromosome)genome);
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
	public Chromosome createChromosome() {
		return new DoubleChromosome(1);
	}

}
