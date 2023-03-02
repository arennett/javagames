package de.ar.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import de.ar.game.entity.Ball;
import de.ar.game.entity.Player;
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
	 CollisionDetection collisionDetection;
	 ScoreControl scoreControl;
	 
	 Ball ball = new Ball(this);
	 
	 Player rightPlayer = new Player(Player.PLAYER_RIGHT,this);
	 Player leftPlayer  = new Player(Player.PLAYER_LEFT,this);
	 
	 public Player getLeftPlayer() {
		return leftPlayer;
	}

	public Player getRightPlayer() {
		return rightPlayer;
	}

	KeyHandler keyHandler = new KeyHandler();
	 
	 public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

	public GamePanel () {
		 addKeyListener(keyHandler);
		 tileManager = new TileManager();
		 collisionDetection = new CollisionDetection(this);
		 scoreControl = new ScoreControl(this);
		 setPreferredSize(new Dimension (BOARD_WIDTH,BOARD_HEIGHT));
		 this.setFocusable(true);
         this.requestFocusInWindow();	
	 }

	public CollisionDetection getCollisionDetection() {
		return collisionDetection;
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
		leftPlayer.draw(g2);
		rightPlayer.draw(g2);
		scoreControl.draw(g2);
		
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
		leftPlayer.move();
		rightPlayer.move();
	}

	public Ball getBall() {
		return ball;
	}
}
