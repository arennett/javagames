package de.ar.game.main;

import javax.swing.JOptionPane;

/**
 * start and stop the game and opens the OptionsDialig 
 */
public class GameControl implements Runnable{
	final int  FPS = 60;
	final long FPERIOD_NSEC = 1000 * 1000 * 1000 / FPS; 
	
	private GamePanel gp;
	OptionsDialog optDialog ;
	Thread gameThread;
	private boolean gameThreadRunning =false;
	
	public GameControl(GamePanel gp) {
		this.gp = gp;
		optDialog = new OptionsDialog(gp);

	}

	public void startGame() {
		gp.getScoreControl().resetScoring();
		gp.getCountDownControl().startCountDown();
		startGameThread();
	}

	
	public void openDialog() {
		stopGameThread();
		optDialog.setVisible(true);
	}
	

	private void startGameThread() {
		gameThread = new Thread(this);
		gameThreadRunning=true;
		gameThread.start();		
	}
	
	public void stopGameThread() {
		gameThreadRunning=false;
	}

	@Override
	public void run() {
		long  timeOld = System.nanoTime();
		long  period  = 0;
		long  period2 = 0;
		long  frames = 0;
		
		while (gameThreadRunning) {
			  
			long timeNew = System.nanoTime();
			long delta = timeNew-timeOld;
			period  +=delta;
			period2 +=delta;
			
			if (period >= FPERIOD_NSEC) {
				frames++;
				period=0;
				gp.update();
				gp.repaint();
				try {
					long time = System.nanoTime();
					long workTime = time-timeNew;
					// minus 1 msec for inaccuracy of Thread.sleep
					long sleepTime =FPERIOD_NSEC-workTime-10000000; 
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

	public void CountDownFinished() {
		stopGameThread();
		JOptionPane.showMessageDialog(null, "GameOver");
		startGame();
	}


}
