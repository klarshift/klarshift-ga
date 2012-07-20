package test.tsp.solution.ga;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.klarshift.ga.gui.controller.DoubleSliderController;
import com.klarshift.ga.gui.controller.IntSliderController;
import com.klarshift.ga.selection.TournamentSelection;

public class TSPSolutionGAStopPanel extends JPanel {
	TSPSolutionGA solution;
	
	public TSPSolutionGAStopPanel(TSPSolutionGA solution){
		this.solution = solution;
		
		setLayout(new FlowLayout());
		
		add(new IntSliderController(solution, "maxGenerations", 2000));
		
		DoubleSliderController s = new DoubleSliderController(solution, "minStopDistance", 3000);
		s.setScale(10000);
		add(s);
		
		TournamentSelection selection = (TournamentSelection) solution.getGA().getSelection();
		add(new DoubleSliderController(selection, "selectionPressure"));
		
		setVisible(true);
	}
}
