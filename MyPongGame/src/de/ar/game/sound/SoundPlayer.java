package de.ar.game.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
	public static final int CLIP_BALL_BEEP1 = 1;
	public static final int CLIP_BALL_BEEP2 = 2;
	public HashMap<Integer,SoundClip> clipHash = new HashMap<Integer,SoundClip>();
	
	public SoundPlayer() {
		init();
		
	}

	private void init() {
		clipHash.clear();
		clipHash.put(CLIP_BALL_BEEP1,new SoundClip("pongblipd4.wav"));
		clipHash.put(CLIP_BALL_BEEP2,new SoundClip("pongblipd5.wav"));
	}
	
	/**
	 * open all sound clips
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void open() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		for (Map.Entry<Integer,SoundClip> entry:clipHash.entrySet()){
			SoundClip sc =entry.getValue();
			sc.open();
		}
		
	}
	
	/**
	 * close all soundclips
	 * @throws IOException
	 */
	private void close() throws IOException {
		for (Map.Entry<Integer,SoundClip> entry:clipHash.entrySet()){
			SoundClip sc =entry.getValue();
			sc.close();
		}
		
	}
	
	/**
	 * play sound clip
	 * @param clip
	 */
	public void play(int clipNr) {
		try {
			SoundClip clip = clipHash.get(clipNr);
			clip.play();
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	

}
