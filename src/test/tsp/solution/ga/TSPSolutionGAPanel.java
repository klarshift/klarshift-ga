package test.tsp.solution.ga;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.klarshift.ga.GA;
import com.klarshift.ga.IGenerationListener;
import com.klarshift.ga.gui.GAInfoPanel;
import com.klarshift.ga.gui.GASettingsPanel;
import com.klarshift.ga.gui.SolutionPanel;
import com.klarshift.ga.tsp.TSPProblem;

/**
 * tsp solution panel 
 * 
 * @author timo
 *
 */
public class TSPSolutionGAPanel extends SolutionPanel implements IGenerationListener{
	private TSPSolutionGA solution ;
	private GA ga;
	private GASettingsPanel gaPanel;
	private TSPSolutionGAStopPanel stopPanel;
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
		
		// add ga panel
		gaPanel = new GASettingsPanel(ga);
		settings.add("Genetic Algorithm", gaPanel);
		
		// 
		stopPanel = new TSPSolutionGAStopPanel(solution);
		settings.add("Settings", stopPanel);
		
		settings.add("Mutation", new JPanel());
		settings.add("Crossover", new JPanel());
	}


	@Override
	public void onGenerationFinished() {
			
	}

}
