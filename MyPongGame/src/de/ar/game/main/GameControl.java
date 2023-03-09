package de.ar.game.main;

import static de.ar.game.countdown.CountDownManager.*;

import javax.swing.JOptionPane;

import de.ar.game.countdown.CountDown;
import de.ar.game.countdown.CountDownListener;

/**
 * start and stop the game and opens the OptionsDialig 
 */
public class GameControl implements Runnable, CountDownListener{
	final int  FPS = 60;
	final long FPERIOD_NSEC = 1000 * 1000 * 1000 / FPS; 
	public static final int PLAY_MODE_RESERVED	 = 0;
	public static final int PLAY_MODE_ONE_PLAYER = 1;
	public static final int PLAY_MODE_TWO_PLAYER = 2;
	
	private GamePanel gp;
	OptionsDialog optDialog ;
	Thread gameThread;
	private boolean gameThreadRunning =false;
	private int playMode=PLAY_MODE_ONE_PLAYER;
	
	CountDown countdown_level_start;
	CountDown countdown_level_expired;
	
	
	
	public GameControl(GamePanel gp) {
		this.gp = gp;
		optDialog = new OptionsDialog(gp);
		
		countdown_level_start 
		= gp.getCountDownManager().getCountDown(COUNTDOWN_LEVEL_START);
		countdown_level_expired
		= gp.getCountDownManager().getCountDown(COUNTDOWN_LEVEL_EXPIRED);
		
		countdown_level_start.addListener(this);
		countdown_level_expired.addListener(this);
		
	}

	public void startLevelCountDown() {
		gp.getScoreControl().resetScoring();
		gp.getKeyHandler().reset();
		countdown_level_start.startCountDown();
		
		
	}
	
	public void startLevel() {
		gp.getScoreControl().resetScoring();
		gp.getKeyHandler().reset();
		
		countdown_level_expired.startCountDown();
		startGameThread();
		
	}

	
	public void openDialog() {
		stopLevel();
		optDialog.setVisible(true);
	}
	
	public void stopLevel() {
		countdown_level_start.cancel();
		countdown_level_expired.cancel();
		stopGameThread();
		gp.getBall().setStartPos();
		gp.repaint();
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
				//System.out.println("FPS:" + frames);
				period2=0;
				frames=0;
			}
			timeOld=timeNew;
		}	
	}

	public int getPlayMode() {
		return playMode;
	}
	public void setPlayMode(int playMode) {
		this.playMode = playMode;
	}

	
	@Override
	public void updateCount(CountDown cd) {
	}

	@Override
	public void expiredCount(CountDown cd) {
		switch(cd.getId()) {
		case COUNTDOWN_LEVEL_EXPIRED:
			 	stopLevel();
				JOptionPane.showMessageDialog(gp,"Game over, try again !","MyPong V1.0" ,JOptionPane.PLAIN_MESSAGE);
				startLevelCountDown();
			
			break;
			
		case COUNTDOWN_LEVEL_START:
			startLevel();
		
			break;
		}
		
		
	}

	
	

}
