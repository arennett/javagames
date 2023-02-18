package de.ar.game;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe {
	
	private static TicTacToe ticTacToe;
	JLabel labelComp1 = new JLabel( "comp1: 0");
	JLabel labelComp2 = new JLabel( "comp2: 0");
	JLabel labelDrawn = new JLabel( "drawn: 0");
	
	JButton buttonSimulation = new JButton("Simulations");
	
	private  TicTacToe() {
		
		
		
	}
	static public TicTacToe getInstance() {
		return ticTacToe;
	}
	
	private void init() {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setTitle("My Tic Tac Toe");
		
		BorderLayout bl = new BorderLayout();
		window.setLayout(bl);
		
		GamePanel gp = GamePanel.getInstance();
		window.add(gp, BorderLayout.CENTER);
				
		JPanel pn =new JPanel();
		
		pn.add(labelDrawn);
		pn.add(labelComp1);
		pn.add(labelComp2);
		
		pn.add(buttonSimulation);
		buttonSimulation.addActionListener(GameController.getInstance());
				
		window.add(pn, BorderLayout.SOUTH);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		GameLogic.getInstance().startGame();
		
	}
	
	
	

	public static void main(String[] args) {
		
		ticTacToe = new TicTacToe();
		ticTacToe.init();

	}

}
