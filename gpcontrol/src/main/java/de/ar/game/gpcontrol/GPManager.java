/** Detects Controller and holds them in an array
 * 
 */
package de.ar.game.gpcontrol;

import java.util.HashMap;
import java.util.Vector;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 * @author arenn
 * handles the GPController (gamepads) , and hides the other controller stuff
 */
public class GPManager {
	public static final int GP_FIRST_CONTROLLER = 0;
	public static final int GP_SECOND_CONTROLLER = 1;

	Vector<GPController> gpControllers = new Vector<GPController>();

	public GPManager() {
		init();
	}

	private void init() {
		int id =0;
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (int i = 0; i < controllers.length; i++) {
			if (controllers[i].getName().contains("GAME")) {
				gpControllers.add(new GPController(id, controllers[i]));
				if(controllers[i].getRumblers().length > 0) {
					controllers[i].getRumblers()[0].rumble(100);
				}
				System.out.println("GpController: " + id + " detected");
				id++;
			}
		}
	}
	
	GPController getGPController(int id) {
		if (id > 0 && id < gpControllers.size()) {
			return gpControllers.elementAt(id);
		}
		return null;
			
	} 
	GPController getFirstController() {
		return gpControllers.elementAt(GP_FIRST_CONTROLLER);
	}
	
	GPController getSecondController() {
		return gpControllers.elementAt(GP_SECOND_CONTROLLER);
	}
	
	public int countControllers() {
		return gpControllers.size();
	}

		

	public void processEvents() {
		for (GPController gpController : gpControllers) {
			while (true) {
				gpController.processEvents();
			}

		}
	}

}
