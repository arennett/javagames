package de.ar.game.gpcontrol;

import java.util.HashMap;
import java.util.Vector;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import static de.ar.game.gpcontrol.GPValueEvent.*;

public class GPController {

	private Controller controller;
	
	boolean buttonAOn = false;
	
	
	boolean buttonXOn = false;
	
	
	boolean buttonYOn = false;
	
	
	boolean buttonBOn = false;

	public HashMap<Integer,Vector <GPValueListener>> registry;

	Event event = new Event();
	
	public int id;

	public GPController(int id, Controller controller) {
		this.id = id;
		this.controller = controller;
		registry  = new HashMap<Integer,Vector <GPValueListener>>();
	}
	
	public void addListener(GPValueListener listener,int valueID) {
		Vector<GPValueListener> v =registry.get(valueID);
		if (v==null) {
			v= new Vector<GPValueListener>();
			registry.put(valueID,v);
			v.add(listener);
		}
	}

	public int getId() {
		return id;
	}
	
		
	public void processEvents() {
		controller.poll();
		EventQueue queue = controller.getEventQueue();
		while (queue.getNextEvent(event)) {
			/* Get event component */
			Component comp = event.getComponent();

			/* Process event (your awesome code) */
			Identifier id = comp.getIdentifier();
			System.out.println(comp);

			if (id instanceof Axis) {
				if (id.equals(Component.Identifier.Axis.RX)) {
					GPValueEvent gpe = new GPValueEvent(ID_VALUE_ROTATION_X,this,event);
					updateListeners(gpe);
				
				}
				if (id.equals(Component.Identifier.Axis.RY)) {
					GPValueEvent gpe = new GPValueEvent(ID_VALUE_ROTATION_Y,this,event);
					updateListeners(gpe);
				}
			}
			if (id instanceof Button) {
				if (id.equals(Component.Identifier.Button._0)) {
					buttonAOn = comp.getPollData() == 1.0f;
					GPValueEvent gpe = new GPValueEvent(ID_VALUE_BUTTON_A,this,event);
					updateListeners(gpe);
				}
				if (id.equals(Component.Identifier.Button._2)) {
					buttonXOn = comp.getPollData() == 1.0f;
					GPValueEvent gpe = new GPValueEvent(ID_VALUE_BUTTON_X,this,event);
					updateListeners(gpe);
				}
				if (id.equals(Component.Identifier.Button._3)) {
					buttonYOn = comp.getPollData() == 1.0f;
					GPValueEvent gpe = new GPValueEvent(ID_VALUE_BUTTON_Y,this,event);
					updateListeners(gpe);
				}
				if (id.equals(Component.Identifier.Button._1)) {
					buttonBOn = comp.getPollData() == 1.0f;
					GPValueEvent gpe = new GPValueEvent(ID_VALUE_BUTTON_B,this,event);
					updateListeners(gpe);
				}

			}
		}
	}

	private void updateListeners(GPValueEvent gpe) {
		Vector<GPValueListener> listeners =registry.get(gpe.getId());
		if(listeners != null) {
			for (GPValueListener listener:listeners) {
				listener.updateGPValue(gpe);
			}
		}
	}

}
