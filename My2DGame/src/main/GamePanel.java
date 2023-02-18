package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	//WORLD SETTINGS
	public final int maxWorldCol  = 50;
	public final int maxWorldRow  = 50;
	public final int worldWidth   = tileSize * maxWorldCol;
	public final int worldHeight  = tileSize * maxWorldRow;
	
	
	
	
	
	// FPS
	int FPS = 60;

	
	
	
	
	TileManager tileManager = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker  collChecker = new CollisionChecker(this);
	
	public Player player = new Player(this,keyH);
	

	// Set player´s default position
	

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	@Override
	public void run() {
							  
		gameLoopDeltaMethod();
	}

	private void gameLoopSleepMethod() {
		double drawInterval = 1000000000 / FPS; // 1sec/60 0.0166 sec
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {
//			1 UPDATE:	update information such as character positions
			update();

//			2 DRAW:		draw the screen with the updated information
			repaint();
			
			try {
				//time to sleep
				double remainingTime = nextDrawTime - System.nanoTime();
				
				if(remainingTime < 0) { // update and repaint was longer as a frame (drawInterval)
					remainingTime = 0;
				}
				
				Thread.sleep((long) (remainingTime)/1000000);
				
				nextDrawTime = System.nanoTime() + drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void gameLoopDeltaMethod() {
		double 	drawInterval = 1000000000 / FPS; // 1sec/60 0.0166 sec
		double 	delta = 0;
		long 	lastTime = System.nanoTime();
		long 	currentTime;
		
		long    monitorFPSTime = 0;
		long    monitorFPSDrawCount = 0;
		
		while (gameThread != null) { 
			currentTime = System.nanoTime();
			delta 			+= (currentTime - lastTime) ;
			monitorFPSTime 	+= (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if (delta > drawInterval) {
				update();
				repaint();
				delta=0;
				monitorFPSDrawCount++;
			}
			if (monitorFPSTime > 1000000000) { // 1sec
				System.out.println("FPS:" + monitorFPSDrawCount);
				monitorFPSDrawCount=0;
				monitorFPSTime=0;
			}
			
						
		}
		
	}
	
	
	private void update() {
		player.update();
		
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		tileManager.draw(g2);
		player.draw(g2);

		g2.dispose();

	}

}
