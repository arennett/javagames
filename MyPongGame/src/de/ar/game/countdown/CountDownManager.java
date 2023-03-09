package de.ar.game.countdown;

import java.util.HashMap;
import de.ar.game.main.GamePanel;

public class CountDownManager{
	

	public static final int COUNTDOWN_LEVEL_START 		= 0;
	public static final int COUNTDOWN_LEVEL_EXPIRED     = 1;
		
	HashMap<Integer,CountDown> countdowns;
	
	private GamePanel gp;
	CountDown countdown_level_start;
	CountDown countdown_level_expired;
	
	public CountDownManager(GamePanel gp) {

		this.gp = gp;
		countdowns = new HashMap<Integer,CountDown>();
		
		countdown_level_start= new CountDown(COUNTDOWN_LEVEL_START,3*1000,1000,0);
		countdowns.put(COUNTDOWN_LEVEL_START, countdown_level_start);
		
		countdown_level_expired= new CountDown(COUNTDOWN_LEVEL_EXPIRED,10*1000,1000,0);
		countdowns.put(COUNTDOWN_LEVEL_EXPIRED, countdown_level_expired);
		
	}

		
	public CountDown getCountDown(int id) {
		return countdowns.get(id); 
	}

}
