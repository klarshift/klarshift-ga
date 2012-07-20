package com.klarshift.ga.gui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.klarshift.ga.Solution;

/**
 * solution panel
 * @author timo
 *
 */
public class SolutionPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8222193037413361371L;
	private TitledBorder border;
	private Solution solution;
		
	/**
	 * create solution panel
	 * @param solution
	 */
	public SolutionPanel(Solution solution){
		
		// store application
		this.solution = solution;							
						
		// 
		border = new TitledBorder("Solution");
		setBorder(border);
		
		// show
		setVisible(true);
	}
	
	/**
	 * get the solution
	 * @return
	 */
	public Solution getSolution(){
		return solution;
	}
	
	/**
	 * set the title of the solution panel
	 * @param title
	 */
	public void setTitle(String title){
		border.setTitle(title);
	}	
	
	/**
	 * update the panel
	 */
	public void update(){
		// repaint
		repaint();
	}
}
