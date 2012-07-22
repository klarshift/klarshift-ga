package com.klarshift.artificial.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.klarshift.artificial.Solution;

/**
 * solution selection
 * @author timo
 *
 */
public abstract class ComboSelection<T> extends JPanel implements ActionListener {
	private JComboBox<T> comboBox;
	
	public ComboSelection(String title) {
		
		add(new JLabel(title + ": "));
		
		comboBox = new JComboBox<T>();
		add(comboBox);
		
		comboBox.addActionListener(this);
		
		setVisible(true);
	}
	
	public void addItem(T object){
		comboBox.addItem(object);
	}
	
	public void removeSolution(Solution solution){
		comboBox.removeItem(solution);
	}
	
	public abstract void onChange();

	@Override
	public void actionPerformed(ActionEvent e) {
		onChange();
	}
	
	public T getItem(){
		return (T) comboBox.getSelectedItem();
	}
}
