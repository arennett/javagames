package de.ar.game.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip implements LineListener {

	final String resPath = "/sounds/";

	boolean clipCompleted = false;
	private String filename;
	AudioInputStream audioStream;
	private Clip audioClip;

	public SoundClip(String filename) {
		this.filename = filename;

	}

	@Override
	public void update(LineEvent event) {

		if (event.getType() == LineEvent.Type.START) {
			System.out.println("clip started: " + filename);
		}

		if (event.getType() == LineEvent.Type.STOP) {
			clipCompleted = true;
			System.out.println("clip stopped: " + filename);
			
			try {
				resetClip();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * open the audiostram and the audio clip
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void open() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		String filePath = resPath + filename;

		// open audiostream
		InputStream inputStream = getClass().getResourceAsStream(filePath);
		audioStream = AudioSystem.getAudioInputStream(inputStream);

		// get dataline info object
		AudioFormat audioFormat = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

		// creating audioclip
		audioClip = (Clip) AudioSystem.getClip();
		audioClip.addLineListener(this);
		audioClip.open(audioStream);

	}

	public void resetClip() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioClip.setFramePosition(0);
	}

	/**
	 * play the audioclip
	 * 
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException 
	 */
	public void play() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		audioClip.start();
	}

	/**
	 * close audioclip and audiostream
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		audioClip.close();
		//audioStream.close();
	}

}
