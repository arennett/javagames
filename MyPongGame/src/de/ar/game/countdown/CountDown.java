package de.ar.game.countdown;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class CountDown {
	private  int id = 0;
	private  Vector<CountDownListener> listeners;
	private long time=0;
	private long updatePeriod=0;
	private Timer t;
	private long remainTime=0;
	private long activeTime=0;
	
	private boolean isActive =false;
	private long delay=0;
	private boolean firstUpdate = false;
	private long startTime =0;
	/**
	 * 
	 * @param id
	 * @param time
	 * @param updatePeriod
	 */
	public CountDown(int id,long time, long updatePeriod,long delay) {
		this.id = id;
		this.time = time;
		this.updatePeriod = updatePeriod;
		this.delay = delay;
		this.listeners=new Vector<CountDownListener>();
		
		
	}
	
	public void addListener(CountDownListener cdl) {
		this.listeners.add(cdl);
	}
	
	public void startCountDown() {
		resetCountDown();
		t = new Timer();
		isActive =true;
		startTime= System.currentTimeMillis();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
			}

		}, delay, updatePeriod);
	}

	public int getId() {
		return id;
	}

	public long getTime() {
		return time;
	}
	public boolean isActive() {
		return isActive;
	}

	public long getUpdatePeriod() {
		return updatePeriod;
	}

	public long getRemainTime() {
		return remainTime;
	}

	public void resetCountDown() {
		if (t!=null) {
			t.cancel();
		}
		isActive =false;
		firstUpdate = true;
		remainTime = time;
		activeTime=0;
		startTime =0;
	}

	private void update() {
		if(firstUpdate) {
			firstUpdate =false;
			remainTime-=delay;
		}else {
			remainTime-=updatePeriod;
		}
		
		long currentTime =System.currentTimeMillis();
		
		activeTime = currentTime-startTime;
		//remainTime = time-activeTime;
		
		//if (currentTime >= startTime+time) {
		if( remainTime <= 0) {
			cancel();
			for (CountDownListener cd:listeners) {
				cd.expiredCount(this);
			}
			return;
		}
		
		for (CountDownListener cd:listeners) {
			cd.updateCount(this);
		}
		
		
		
		return;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void cancel() {
		resetCountDown();
		
	}
	

}
