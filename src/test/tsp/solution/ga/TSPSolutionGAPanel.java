package test.tsp.solution.ga;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.klarshift.artificial.IGenerationListener;
import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.gui.GAInfoPanel;
import com.klarshift.artificial.genetic.gui.GASettingsPanel;
import com.klarshift.artificial.gui.MutationPanel;
import com.klarshift.artificial.gui.SolutionPanel;
import com.klarshift.artificial.problem.tsp.TSPProblem;

/**
 * tsp solution panel 
 * 
 * @author timo
 *
 */
public class TSPSolutionGAPanel extends SolutionPanel implements IGenerationListener{
	private TSPSolutionGA solution ;
	private GA ga;
	private TSPSolutionGASettingsPanel gaPanel;
	private GAInfoPanel infoPanel;
	

	public TSPSolutionGAPanel(TSPSolutionGA solution) {
		super(solution);		
		this.solution = solution;
					
		// get ga
		ga = solution.getGA();		
		ga.addGenerationListener(this);
		
		setLayout(new BorderLayout());
		
		setPreferredSize(new Dimension(400, 400));
		
		
		setTitle("TSP GA Solution");
		
		// info panel
		infoPanel = new GAInfoPanel(ga);
		infoPanel.setBorder(new TitledBorder("GA Info"));
		add(infoPanel, BorderLayout.NORTH);
		
		JTabbedPane settings = new JTabbedPane();
		add(settings, BorderLayout.CENTER);
		
		
		// 
		gaPanel = new TSPSolutionGASettingsPanel(ga, solution);
		settings.add("Genetic Algorithm", gaPanel);
		
		
		settings.add("Mutation", new MutationPanel(solution.getMutationList()));
		//settings.add("Crossover", new JPanel());
	}


	@Override
	public void onGenerationFinished() {
			
	}

}
