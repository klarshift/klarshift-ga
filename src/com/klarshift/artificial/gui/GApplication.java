package com.klarshift.artificial.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.klarshift.artificial.Problem;
import com.klarshift.artificial.Solution;

/**
 * genetic algorithm main application
 * 
 * @author timo
 * 
 */
public class GApplication extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3372663410895882962L;
	
	/* problems and solutions */
	private ArrayList<Problem> problems = new ArrayList<Problem>();
	private ArrayList<Solution> solutions = new ArrayList<Solution>();
	private Solution solution;
	private Problem problem;
	private SolutionPanel solutionPanel;
	private ProblemPanel problemPanel;
	
	private JPanel contentPanel, controllers;
	private StatusBar statusBar;
	
	private ComboSelection<Solution> solutionSelection;
	private ComboSelection<Problem> problemSelection;

	private JButton startBtn;

	/**
	 * create ga application
	 */
	public GApplication() {
		setTitle("GA Application");

		initGUI();
	}

	/**
	 * get the problem panel
	 * @return
	 */
	public ProblemPanel getProblemPanel() {
		return problemPanel;
	}

	/**
	 * get the solution panel
	 * @return
	 */
	public SolutionPanel getSolutionPanel() {
		return solutionPanel;
	}

	/**
	 * add solution
	 * @param solution
	 */
	public void addSolution(Solution solution) {
		solutions.add(solution);
		solutionSelection.addItem(solution);
		if (this.solution == null) {
			setSolution(solution);
		}
	}

	/**
	 * add problem
	 * @param problem
	 */
	public void addProblem(Problem problem) {
		problems.add(problem);
		problemSelection.addItem(problem);
		if (this.problem == null) {
			setProblem(problem);
		}
	}

	/**
	 * set the current solution
	 * @param solution
	 */
	public void setSolution(Solution solution) {
		this.solution = solution;

		// remove previous panel
		if (solutionPanel != null) {
			remove(solutionPanel);
		}

		// get panel and add it
		solutionPanel = solution.getPanel();
		if(solutionPanel != null){
			add(solutionPanel, BorderLayout.EAST);
			solutionPanel.update();
		}
		statusBar.setStatus("Solution set: " + solution);
		
	}

	public void solve() {
		if (problem != null && solution != null
				&& solution.isSolving() == false) {
			
			startBtn.setText("Stop");

			// init problem
			problem.init();

			// start thread
			new Thread() {
				public void run() {
					statusBar.setStatus("Solution solving ...");
					
					try {
						// TODO Auto-generated method stub
						solution.init(problem);
						solution.solveProblem();
						statusBar.setStatus("Solution done. " + solution.getRuntime() + " ms");
						startBtn.setText("Solve");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();

		} else {

		}
	}
	
	public void stop(){
		if(solution != null){
			solution.stopSolving();
			statusBar.setStatus("Stopped.");
		}
	}

	public void setProblem(Problem problem) {
		this.problem = problem;

		if (problemPanel != null) {
			remove(problemPanel);
		}

		problemPanel = problem.getPanel();
		add(problemPanel, BorderLayout.WEST);

		statusBar.setStatus("Problem set: " + problem);
	}

	public Problem getProblem() {
		return problem;
	}

	public Solution getSolution() {
		return solution;
	}

	private void initGUI() {
		// set layout
		setLayout(new BorderLayout());
		
		controllers = new JPanel();
		add(controllers, BorderLayout.NORTH);
		
		problemSelection = new ComboSelection<Problem>("Problem"){

			@Override
			public void onChange() {
				setProblem(this.getItem());
			}

			
			
		};		
		controllers.add(problemSelection);
		
		solutionSelection = new ComboSelection<Solution>("Solution"){

			@Override
			public void onChange() {
				setSolution(this.getItem());
			}
			
		};		
		controllers.add(solutionSelection);
		
		// start button
		startBtn = createButton("Solve");		
		
		
		// content
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		add(contentPanel, BorderLayout.CENTER);
		
		// status bar
		statusBar = new StatusBar(2);
		add(statusBar, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(1000, 800));
		pack();
		setVisible(true);
		
		statusBar.setStatus("Application loaded.");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private JButton createButton(String text){
		JButton btn = new JButton(text);
		btn.addActionListener(this);
		controllers.add(btn);



		return btn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn == startBtn){
			if(startBtn.getText() == "Solve"){				
				solve();				
			}else if(startBtn.getText() == "Stop"){
				stop();
				startBtn.setText("Stop");
			}
		}
	}		
}
