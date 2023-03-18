package de.ar.game.main;

import static de.ar.game.countdown.CountDownManager.*;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import de.ar.game.countdown.CountDown;
import de.ar.game.countdown.CountDownListener;
import de.ar.game.gpcontrol.GPManager;
import de.ar.game.gpcontrol.GPValueEvent;
import de.ar.game.gpcontrol.GPValueListener;
import de.ar.game.sound.SoundPlayer;

/**
 * start and stop the game and opens the OptionsDialig 
 */
public class GameControl implements Runnable, CountDownListener, GPValueListener{
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
	JLabel gameoverLabel=null;
	
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
		
		if (gp.getGamePadRight()!=null) {
			gp.getGamePadRight().getGpController().addListener(this, GPValueEvent.ID_VALUE_BUTTON_A);
		}
		
		gameoverLabel = new JLabel("Game over, try again !");
		Font f=new Font( "Arial", Font.BOLD, 18);  
	 	gameoverLabel.setFont(f);
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
			 	for (int i=0; i<4;i++) {
					gp.getSoundPlayer().play(SoundPlayer.CLIP_BALL_BEEP1);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//			 	 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
//			 	          "Arial", Font.BOLD, 18)));  
//			 	
			    if (gp.getScoreControl().scPlayerLeft > gp.getScoreControl().scPlayerRight) {
			    	if (playMode==PLAY_MODE_ONE_PLAYER) {
			    		gameoverLabel.setText("time over, computer wins !");
			    	}else {
			    		gameoverLabel.setText("time over, left player wins !");
			    	}
			    	
			    }else  if (gp.getScoreControl().scPlayerLeft < gp.getScoreControl().scPlayerRight){
			    	if (playMode==PLAY_MODE_ONE_PLAYER) {
			    		gameoverLabel.setText("time over, you win !");
			    	}else {
			    		gameoverLabel.setText("time over, right player wins !");
			    	}
			    }else {
			    	gameoverLabel.setText("time over, nobody wins !");
			    }
				JOptionPane.showMessageDialog(gp,gameoverLabel,"MyPong V1.0" ,JOptionPane.PLAIN_MESSAGE);
				
				startLevelCountDown();
			
			break;
			
		case COUNTDOWN_LEVEL_START:
			for (int i=0; i<4;i++) {
				gp.getSoundPlayer().play(SoundPlayer.CLIP_BALL_BEEP1);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			startLevel();
		
			break;
		}
		
		
	}

	@Override
	public void updateGPValue(GPValueEvent event) {
		if (event.getId()==GPValueEvent.ID_VALUE_BUTTON_A) {
			Window win=SwingUtilities.getWindowAncestor(gameoverLabel);
			if(win!=null) {
				win.setVisible(false);
			}
			if (optDialog.isVisible()) {
				optDialog.setVisible(false);
				gp.getGameControl().startLevelCountDown();
			}
		}
		
	}

	
	

}
