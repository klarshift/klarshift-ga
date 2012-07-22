package com.klarshift.artificial.problem.tsp.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.klarshift.artificial.gui.ProblemPanel;
import com.klarshift.artificial.gui.StatusBar;
import com.klarshift.artificial.problem.tsp.TSPProblem;

public class TSPProblemPanel extends ProblemPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3293511851400563591L;
	
	private JButton loadMatrixBtn, drawBtn;
	
	
	TSPProblem problem;
	
	JPanel contentPanel, topPanel;
	StatusBar statusBar;
	
	TSPCityDesigner designer;
	
	JLabel distanceLabel;

	private JTextArea resultOrder;

	private JLabel cityLabel;
	

	public TSPProblemPanel(final TSPProblem problem) {
		super(problem);
		
		// store problem
		this.problem = problem;
		
		initGui();
		
		
		


		
		//setPreferredSize(new Dimension(550, 700));
		
		
	}
	
	@Override
	public void update() {	
		super.update();
		
		distanceLabel.setText("minD=" + problem.getCurrentDistance());
		cityLabel.setText("cityCount=" + problem.getCityCount());
		
		if(problem.getCurrentSolution() != null)
			resultOrder.setText(problem.getCurrentSolution().toString());
	}
	
	
	private void initGui(){
		// set layout
		setLayout(new BorderLayout());
		setTitle("TSP Problem");
		
		setPreferredSize(new Dimension(500, 400));
		
		// add controllers
		topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.setPreferredSize(new Dimension(400, 320));
		add(topPanel, BorderLayout.NORTH);
		
		
		
		// add info
		JPanel resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(380, 160));
		resultPanel.setBorder(new TitledBorder("Results"));		
		topPanel.add(resultPanel);
		
		distanceLabel = new JLabel("minD=0");
		distanceLabel.setPreferredSize(new Dimension(360, 20));
		resultPanel.add(distanceLabel);
		
		cityLabel = new JLabel("cityCount=0");
		cityLabel.setPreferredSize(new Dimension(360, 20));
		resultPanel.add(cityLabel);
		
		resultOrder = new JTextArea(5, 20);
		resultOrder.setLineWrap(true);
		JScrollPane sp = new JScrollPane(resultOrder);
		sp.setPreferredSize(new Dimension(360, 80));
		resultPanel.add(sp);
		
		// add buttons
		loadMatrixBtn = createButton("Load Matrix");
		drawBtn = createButton("Draw Cities");

					
		// add status bar
		statusBar = new StatusBar(1);
		add(statusBar, BorderLayout.SOUTH);
		
		statusBar.setStatus("TSPProblem GUI loaded");
	}	
	
	private void showDesigner(){
		if(designer != null){
			remove(designer);
		}
		
		designer = new TSPCityDesigner(problem);
		add(designer, BorderLayout.CENTER);		
		
		updateUI();
		
		statusBar.setStatus("Source: Drawn cities.");
	}
	
	private void hideDesigner(){
		if(designer != null){
			designer.setVisible(false);
			remove(designer);
		}
	}
	
	
	/**
	 * button helper
	 * @param title
	 * @return
	 */
	private JButton createButton(String title){
		JButton button = new JButton(title);
		button.addActionListener(this);
		topPanel.add(button);
		return button;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loadMatrixBtn){
			hideDesigner();
			problem.chooseFile();
			if(problem.getFile() != null){
				statusBar.setStatus("File selected: " + problem.getFile().toString());
			}
		}else if(e.getSource() == drawBtn){
			showDesigner();
		}
		
		update();
		
	}
	
	public TSPCityPanel getCityPanel() {
		return designer.getCityPanel();
	}
	
	public TSPCityDesigner getDesigner(){
		return designer;
	}

	
	
	

}
