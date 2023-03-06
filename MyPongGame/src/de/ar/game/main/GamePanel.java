package de.ar.game.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ar.game.entity.Ball;
import de.ar.game.entity.Player;
import de.ar.game.tiles.TileManager;

/**
 * the gamepanel holds all entities and controls and is drawing and updating the
 * entities
 * 
 * @author arenn
 *
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int TILE_SIZE = 16;
	public static final int TILE_ROWS = 40;
	public static final int TILE_COLS = 50;
	public static final int BOARD_WIDTH = TILE_SIZE * TILE_COLS;
	public static final int BOARD_HEIGHT = TILE_SIZE * TILE_ROWS;

	private TileManager tileManager;
	private CollisionDetection collisionDetection;
	private ScoreControl scoreControl;
	private CountDownControl countDownControl;
	private GameControl gameControl;
	private KeyHandler keyHandler;

	private JFrame window;

	// entities
	private Ball ball;
	private Player rightPlayer;
	private Player leftPlayer;

	public GamePanel(JFrame window) {
		this.window = window;
		keyHandler = new KeyHandler(this);
		addKeyListener(keyHandler);
		tileManager = new TileManager();
		collisionDetection = new CollisionDetection(this);
		scoreControl = new ScoreControl(this);
		countDownControl = new CountDownControl(this);
		gameControl = new GameControl(this);
		ball = new Ball(this);
		leftPlayer = new Player(Player.PLAYER_LEFT, this);
		rightPlayer = new Player(Player.PLAYER_RIGHT, this);

		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public void update() {
		ball.move();
		leftPlayer.move();
		rightPlayer.move();
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
		countDownControl.draw(g2);
		scoreControl.draw(g2);

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

	public CountDownControl getCountDownControl() {
		return countDownControl;
	}

	public void setCountDownControl(CountDownControl countDownControl) {
		this.countDownControl = countDownControl;
	}
}
