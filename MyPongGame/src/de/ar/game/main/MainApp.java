package de.ar.game.main;

import java.io.File;

import javax.swing.JFrame;

public class MainApp {
	   

	
	
		
	public static void main(String[] args) {
		
		System.setProperty("net.java.games.input.librarypath", new File("/workspaces/javaws/TestInput/target/natives").getAbsolutePath());
		
		JFrame window = new JFrame();
		window.setTitle("MyPong");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		GamePanel gp = new GamePanel(window);
		//EmptyPanel gp = new EmptyPanel();
		window.add(gp);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gp.getGameControl().openDialog();
		
	}

}
