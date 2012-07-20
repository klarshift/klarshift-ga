package com.klarshift.ga.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.klarshift.ga.Problem;
import com.klarshift.ga.tsp.gui.TSPCityPanel;

/**
 * problem panel
 * @author timo
 *
 */
public class ProblemPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8760754355996730507L;
	
		
	private TitledBorder border;
	private Problem problem;
	
	public Problem getProblem(){
		return problem;
	}
	
	public ProblemPanel(Problem problem){
		// store application
		this.problem = problem;
		
		// create border		
		border = new TitledBorder("Problem");
		setBorder(border);
		
		// show		
		setVisible(true);
	}	
	
	public void update(){
		repaint();
	}
	
	public void setTitle(String title){		
		border.setTitle(title);
	}

	
}
