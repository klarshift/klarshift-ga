package com.klarshift.artificial;

import javax.swing.JPanel;

import com.klarshift.artificial.gui.GApplication;
import com.klarshift.artificial.gui.ProblemPanel;

/**
 * abstract problem
 * @author timo
 *
 */
public abstract class Problem {	
	/**
	 * init the problem
	 */
	public abstract void init();
	
	/**
	 * create gui panel for the problem
	 * @param application
	 * @return
	 */
	public abstract ProblemPanel getPanel();		
	
	public void log(String message){
		System.out.println(message);
	}
}
