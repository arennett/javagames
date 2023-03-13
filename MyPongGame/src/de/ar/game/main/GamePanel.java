package de.ar.game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ar.game.countdown.CountDown;
import de.ar.game.countdown.CountDownListener;
import de.ar.game.countdown.CountDownManager;
import de.ar.game.entity.Ball;
import de.ar.game.entity.Player;
import de.ar.game.sound.SoundPlayer;
import de.ar.game.tiles.TileManager;

/**
 * the gamepanel holds all entities and controls and is drawing and updating the
 * entities
 * 
 * @author arenn
 *
 */
public class GamePanel extends JPanel implements CountDownListener {

	private static final long serialVersionUID = 1L;

	public static final int TILE_SIZE = 16;
	public static final int TILE_ROWS = 40;
	public static final int TILE_COLS = 50;
	public static final int BOARD_WIDTH = TILE_SIZE * TILE_COLS;
	public static final int BOARD_HEIGHT = TILE_SIZE * TILE_ROWS;

	private TileManager tileManager;
	private CollisionDetection collisionDetection;
	private ScoreControl scoreControl;
	private CountDownManager countDownManager;
	private GameControl gameControl;
	private KeyHandler keyHandler;
	private SoundPlayer soundPlayer;

	private JFrame window;

	// entities
	private Ball ball;
	private Player rightPlayer;
	private Player leftPlayer;
	
	private  CountDown countdown_level_start;
	private CountDown countdown_level_expired;

	public GamePanel(JFrame window) {
		this.window = window;
		keyHandler = new KeyHandler(this);
		addKeyListener(keyHandler);
		tileManager = new TileManager();
		collisionDetection = new CollisionDetection(this);
		scoreControl = new ScoreControl(this);
		countDownManager = new CountDownManager(this);
		gameControl = new GameControl(this);
		ball = new Ball(this);
		leftPlayer = new Player(Player.PLAYER_LEFT, this);
		rightPlayer = new Player(Player.PLAYER_RIGHT, this);
		
		countdown_level_start = countDownManager.getCountDown(CountDownManager.COUNTDOWN_LEVEL_START);
		countdown_level_start.addListener(this);
		
		countdown_level_expired 
		= countDownManager.getCountDown(CountDownManager.COUNTDOWN_LEVEL_EXPIRED);
		countdown_level_expired.addListener(this);
		
		soundPlayer=new SoundPlayer();
		try {
			soundPlayer.open();
		} catch (Exception e) {
			e.printStackTrace();
		} 

		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public void update() {
		ball.move();
		leftPlayer.move();
		rightPlayer.move();
	}

	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		tileManager.drawTiles(g2);
		ball.draw(g2);
		leftPlayer.draw(g2);
		rightPlayer.draw(g2);
		scoreControl.draw(g2);
		draw(g2);

	}

	public void draw(Graphics2D g2) {
		// drawing level countdown
		
		Font font = new Font("MONOSPACED", Font.BOLD, 30);
		g2.setFont(font);
		String str = "Time: " + countdown_level_expired.getRemainTime() /1000;
		g2.drawString(str, 60, 50);
		
		
		// drawing level countdown
		if (countdown_level_start.isActive()) {
			font = new Font("MONOSPACED", Font.BOLD, 60);
			g2.setColor(Color.RED);
			g2.setFont(font);
			str = "" + countdown_level_start.getRemainTime() /1000;
			g2.drawString(str, BOARD_WIDTH/2-ball.getDim().width, BOARD_HEIGHT/2-ball.getDim().width);
		}
		
			
	}
	
	
	
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

	public GameControl getGameControl() {
		return gameControl;
	}

	public JFrame getWindow() {
		return window;
	}

	public CollisionDetection getCollisionDetection() {
		return collisionDetection;
	}

	public ScoreControl getScoreControl() {
		return scoreControl;
	}

	public Player getLeftPlayer() {
		return leftPlayer;
	}

	public Player getRightPlayer() {
		return rightPlayer;
	}

	public Ball getBall() {
		return ball;
	}

	public CountDownManager getCountDownManager() {
		return countDownManager;
	}

	public void setCountDownControl(CountDownManager countDownControl) {
		this.countDownManager = countDownControl;
	}

	@Override
	public void updateCount(CountDown cd) {
		repaint();
		
	}

	@Override
	public void expiredCount(CountDown cd) {
		// TODO Auto-generated method stub
		
	}
}
