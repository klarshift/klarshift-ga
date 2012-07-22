package com.klarshift.artificial.gui;

import javax.swing.JPanel;

import com.klarshift.artificial.genetic.mutation.MutationList;
import com.klarshift.artificial.util.RandomMap;
import java.awt.FlowLayout;

public class MutationPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MutationPanel(MutationList mutationList) {
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		RandomMap rMap = mutationList.getMap();
		for(String key : rMap.getKeys()){
			MutationPanelItem pi = new MutationPanelItem(key, mutationList); 
			add(pi);
		}
	}
	
	private void createPanels(){
		
	}

}
