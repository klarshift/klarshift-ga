package test.tsp.solution.ga;

import com.klarshift.artificial.genetic.GA;
import com.klarshift.artificial.genetic.gui.GASettingsPanel;
import com.klarshift.artificial.genetic.selection.TournamentSelection;
import com.klarshift.artificial.gui.controller.DoubleSliderController;
import com.klarshift.artificial.gui.controller.IntSliderController;

public class TSPSolutionGASettingsPanel extends GASettingsPanel {
	TSPSolutionGA solution;
	
	public TSPSolutionGASettingsPanel(GA ga, TSPSolutionGA solution){
		super(ga);
		
		this.solution = solution;
		
				
		add(new IntSliderController(solution, "maxGenerations", -1, 5000));
		
			
		TournamentSelection selection = (TournamentSelection) solution.getGA().getSelection();
		add(new DoubleSliderController(selection, "selectionPressure"));
		
	}
}
