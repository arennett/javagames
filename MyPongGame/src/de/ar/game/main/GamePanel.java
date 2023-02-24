package de.ar.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import de.ar.game.entity.Ball;
import de.ar.game.tiles.TileManager;

public class GamePanel extends JPanel implements Runnable {
	 final int  FPS = 60;
	 final long PERIOD_FPS = 1000 * 1000 * 1000 / FPS; 
	 public static final int TILE_SIZE = 16;
	 public static final int TILE_ROWS = 40;
	 public static final int TILE_COLS = 50;
	 public static final int BOARD_WIDTH  = TILE_SIZE * TILE_COLS;
	 public static final int BOARD_HEIGHT = TILE_SIZE * TILE_ROWS;
	
	 Thread gameThread;
	 TileManager tileManager;
	 Ball ball = new Ball(this);
	 
	 public TileManager getTileManager() {
		return tileManager;
	}

	public GamePanel () {
		 tileManager = new TileManager();
		 setPreferredSize(new Dimension (BOARD_WIDTH,BOARD_HEIGHT));
	 }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		tileManager.drawTiles(g2);
		ball.draw(g2);
//		drawBall();
//		drawPlayer();
	}


	@Override
	public void run() {
		long  timeOld = System.nanoTime();
		long  period  = 0;
		long  period2 = 0;
		long  frames = 0;
		
		while (true) {
			  
			long timeNew = System.nanoTime();
			long delta = timeNew-timeOld;
			period  +=delta;
			period2 +=delta;
			
			if (period >= PERIOD_FPS) {
				frames++;
				period=0;
				update();
				repaint();
				try {
					long time = System.nanoTime();
					long workTime = time-timeNew;
					// minus 1 msec for inaccuracy of Thread.sleep
					long sleepTime =PERIOD_FPS-workTime-10000000; 
					if (sleepTime > 0 ) {
					//	System.out.println("Sleeptime:" + sleepTime /1000000);
						Thread.sleep(sleepTime /1000000,(int) sleepTime % 1000000); //for lower cpu load ;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (period2 >= 1000*1000*1000) {
				System.out.println("FPS:" + frames);
				period2=0;
				frames=0;
			}
			timeOld=timeNew;
		}	
	}

	private void update() {
		ball.move();
	}
}
