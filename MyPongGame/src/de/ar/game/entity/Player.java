package de.ar.game.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import de.ar.game.main.GamePanel;
import static de.ar.game.main.GamePanel.*;
import static de.ar.game.main.GameControl.*;

import de.ar.game.main.KeyHandler;
import de.ar.game.tiles.TileManager;
import de.ar.game.main.CollisionDetection;
import de.ar.game.main.CollisionDetection.tCollPos;

public class Player extends Entity {
	public static int PLAYER_LEFT = 0;
	public static int PLAYER_RIGHT = 1;

	Point pos;
	Dimension dim;
	Rectangle rec;

	int speed = 6;

	int dy = 0;
	GamePanel gp;
	private int playerType;

	public Player(int playerType, GamePanel gp) {
		this.playerType = playerType;
		this.gp = gp;
		init();

	}

	private void init() {
		int y = BOARD_HEIGHT / 2;
		int x = 0;

		if (playerType == PLAYER_LEFT) {
			x = 50 - TILE_SIZE;
		}

		if (playerType == PLAYER_RIGHT) {
			x = BOARD_WIDTH - 50;
		}
		pos = new Point();
		pos.x = x;
		pos.y = y;

		dim = new Dimension(TILE_SIZE, TILE_SIZE * 5);
		rec = new Rectangle(pos, dim);

	}

	public Rectangle getRectangle() {
		return rec;
	}

	public void move() {

		KeyHandler keyH = gp.getKeyHandler();

		dy = 0;
		if (playerType == PLAYER_RIGHT) {
			if (gp.getGamePadRight() != null) {
				dy = gp.getGamePadRight().getScaledValue();
			} else {
				if (keyH.isKeyPressed_UP()) {
					dy = -speed;
				}

				if (keyH.isKeyPressed_DOWN()) {
					dy = speed;
				}
			}
		}
		if (playerType == PLAYER_LEFT) {
			if (gp.getGameControl().getPlayMode() == PLAY_MODE_TWO_PLAYER) {
				if (gp.getGamePadLeft() != null) {
					dy = gp.getGamePadLeft().getScaledValue();
				} else {
					if (keyH.isKeyPressed_W_UP()) {
						dy = -speed;
					}

					if (keyH.isKeyPressed_S_DOWN()) {
						dy = speed;
					}
				}
			} else if (gp.getGameControl().getPlayMode() == PLAY_MODE_ONE_PLAYER) {
				// computer left player
				if (gp.getBall().pos.x < BOARD_WIDTH / 2)
					if (gp.getBall().pos.y > pos.y + dim.height / 2) {
						dy = speed * 5 / 6;
					} else {
						dy = -speed * 5 / 6;
					}

			}
		}

		Point newPos = new Point();
		newPos.y = pos.y + dy;
		newPos.x = pos.x;
		Rectangle newRec = new Rectangle(newPos, dim);

		// check newPos for collision with tiles
		CollisionDetection cd = gp.getCollisionDetection();
		tCollPos cp = cd.getTileCollPos(newRec);
		tCollPos cp2 = cd.getBallPlayerCollPos(gp.getBall().getRec(), newRec);
		if (cp != null || cp2 != null) {
			dy = 0;
			newPos.y = pos.y + dy;

		}

		pos.x = newPos.x;
		pos.y = newPos.y;
		rec.setLocation(pos);
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		if (playerType == PLAYER_LEFT) {
			if (gp.getGameControl().getPlayMode() == PLAY_MODE_ONE_PLAYER) {
				g2.setColor(Color.GREEN);
			}
		}
		g2.fillRect(pos.x, pos.y, dim.width, dim.height);
	}

}
