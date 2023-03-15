package de.ar.game.gpcontrol;

import java.io.File;

public class ContMain {

	public static void main(String[] args) {
		System.setProperty("net.java.games.input.librarypath", new File("/workspaces/javaws/TestInput/target/natives").getAbsolutePath());
		
//		System.setProperty("net.java.games.input.librarypath", new File("/workspaces/javaws/TestInput/target/natives").getAbsolutePath());
//		ControllerInput ci = new ControllerInput(); // TODO Auto-generated method stub
//		ci.readAllEvents();
		
		GPManager gm = new GPManager();
		

	}

}
