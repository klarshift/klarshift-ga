package com.klarshift.ga.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * status bar
 * @author timo
 *
 */
public class StatusBar extends JPanel {
	int size; 
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	
	/**
	 * create status bar
	 * @param size
	 */
	public StatusBar(int size){
		this.size = size;
		initGui();
	}
	
	private void initGui(){
		setLayout(new GridLayout(1, 3));
		
		for(int s=0; s<size; s++){
			JLabel status = new JLabel("");
			labels.add(status);
			add(status);
		}
		
		setVisible(true);
	}
	
	public void setStatus(int id, String text){
		labels.get(id).setText(text);
	}
	
	public void setStatus(String text){
		setStatus(0, text);
	}
}
