package de.ar.game.gpcontrol;

import net.java.games.input.Event;

/*
 * GPEvent is generated for IDs
 */
public class GPValueEvent {
public static final int ID_VALUE_ROTATION_X = 0;
	
	public static final int ID_VALUE_ROTATION_Y = 1;
		
	public static final int ID_VALUE_BUTTON_A = 2;
	
	public static final int ID_VALUE_BUTTON_X = 3;
	
	public static final int ID_VALUE_BUTTON_Y = 4;
	
	public static final int ID_VALUE_BUTTON_B = 5;
	
	private int id;
	private GPController gpController;
	private Event event;

	public GPValueEvent (int id,GPController gpController,Event event) {
		this.id = id;
		this.gpController = gpController;
		this.event = event;
		
	}
	public int getId() {
		return id;
	}
	public GPController getGpController() {
		return gpController;
	}
	public Event getEvent() {
		return event;
	}
	
	float getValue() {
		return event.getComponent().getPollData();
	}
	
	public int getScaledVal(int scale) {
		float sc = (float) (scale);
		int  val =Math.round( getValue() / 2 * sc);
		return val;
	}
	
	


}
