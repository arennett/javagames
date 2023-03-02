package de.ar.game.main;

import javax.swing.JFrame;

public class MainApp {

		
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setTitle("MyPong");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		GamePanel gp = new GamePanel();
		//EmptyPanel gp = new EmptyPanel();
		window.add(gp);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gp.startGameThread();
		
		

	}

}
