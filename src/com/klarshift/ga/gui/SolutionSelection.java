package com.klarshift.ga.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.klarshift.ga.Solution;

public abstract class SolutionSelection extends JPanel implements ActionListener {
	private JComboBox comboBox;
	
	public SolutionSelection() {
		
		add(new JLabel("Solution: "));
		
		comboBox = new JComboBox<Solution>();
		add(comboBox);
		
		comboBox.addActionListener(this);
		
		setVisible(true);
	}
	
	public void addSolution(Solution solution){
		comboBox.addItem(solution);
	}
	
	public void removeSolution(Solution solution){
		comboBox.removeItem(solution);
	}

	public Object getComboBox() {
		return comboBox;
	}
	
	public abstract void onChange();

	@Override
	public void actionPerformed(ActionEvent e) {
		onChange();
	}
	
	public Solution getSolution(){
		return (Solution) comboBox.getSelectedItem();
	}
}
