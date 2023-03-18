package de.ar.game.gamepad;

import de.ar.game.gpcontrol.GPController;
import de.ar.game.gpcontrol.GPValueEvent;
import de.ar.game.gpcontrol.GPValueListener;

public class GamePad implements GPValueListener {

	int scale;
	int scaledValue = 0;
	GPController gpController;
	Thread t;
	
	public GamePad(int scale,GPController gpController) {
		this.scale = scale;
		this.gpController = gpController;
		gpController.addListener(this, GPValueEvent.ID_VALUE_ROTATION_Y);
		startInputThread();
		
	}
	
	public GPController getGpController() {
		return gpController;
	}

	public void startInputThread() {
		t =new Thread() {
			public void run(){
				while (true) {
					gpController.processEvents();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			};
		};
		t.start();
	}

	@Override
	public void updateGPValue(GPValueEvent event) {
		if (event.getId()== GPValueEvent.ID_VALUE_ROTATION_Y) {
			scaledValue=event.getScaledVal(scale);
		}
		
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getScaledValue() {
		return scaledValue;
	};
	
	
}



