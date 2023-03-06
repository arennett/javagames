package de.ar.game.main;

import static de.ar.game.main.GamePanel.BOARD_WIDTH;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownControl {

	int timeSec = 60;
	int remainTimeSec = 60;
	// set delay and period as 1000
	int delay = 1000;
	int period = 1000;
	Timer t;
	private GamePanel gp;

	public CountDownControl(GamePanel gp) {

		this.gp = gp;
		t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
			}

		}, delay, period);
	}

	public void startCountDown() {
		resetCountDown();
	}

	public void resetCountDown() {
		remainTimeSec = timeSec;
	}

	private void update() {
		if (remainTimeSec == 0) {
			t.cancel();
			// game/Level over, call gameControl
			gp.getGameControl().CountDownFinished();

			return;
		}
		remainTimeSec--;
		return;
	}

	public void draw(Graphics2D g2) {
		Font font = new Font("MONOSPACED", Font.BOLD, 30);
		g2.setFont(font);

		String str = "Time: " + remainTimeSec;
		int x = 60;

		g2.drawString(str, x, 50);
	}

}
