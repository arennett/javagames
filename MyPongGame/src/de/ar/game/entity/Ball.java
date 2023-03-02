package de.ar.game.entity;

import static de.ar.game.main.GamePanel.*;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import de.ar.game.main.CollisionDetection;
import de.ar.game.main.CollisionDetection.tCollPos;
import de.ar.game.main.GamePanel;

//import de.ar.game.tiles.TileManager.Tile;

public class Ball extends Entity {
	int dx;
	int dy;
	Dimension dim;
	Rectangle rec;
	private GamePanel gp;

	public Ball(GamePanel gp) {
		this.gp = gp;
		pos = new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
		speed = 4;
		dim = new Dimension(TILE_SIZE, TILE_SIZE);
		rec = new Rectangle(pos, dim);
		dx = speed;
		dy = speed;
	}

	public void move() {
		// get upper Tile
		Point newPos = new Point();
		newPos.x += pos.x + dx;
		newPos.y += pos.y + dy;
		Rectangle newrec = new Rectangle(newPos, dim);
		CollisionDetection colldetect = gp.getCollisionDetection();
		//goal at newpos ?
		
		int player = colldetect.getGoalDetection(newrec);
		if (player==Player.PLAYER_LEFT) {
			pos.x = 200;
			dx = speed;
			pos.y =BOARD_HEIGHT/2;
		}
		if (player==Player.PLAYER_RIGHT) {
			pos.x = BOARD_WIDTH-200;
			dx = -speed;
			pos.y =BOARD_HEIGHT/2;
		}
				
		
		//collisions
		tCollPos collPos = null;

		if (collPos == null) {
			collPos = colldetect.getTileCollPos(newrec);
			if (collPos != null) {
				switch (collPos) {
				case COLL_UPPER_LEFT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_UPPER:
					dy = -dy;
					break;
				case COLL_UPPER_RIGHT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_RIGHT:
					dx = -dx;
					break;
				case COLL_LOWER_RIGHT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_LOWER:
					dy = -dy;
					break;
				case COLL_LOWER_LEFT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_LEFT:
					dx = -dx;
					break;
				}
			}
		}
		if (collPos == null) {
			collPos = colldetect.getBallPlayerCollPos(newrec, gp.getLeftPlayer().getRectangle());
			if (collPos == null) {
				collPos = colldetect.getBallPlayerCollPos(newrec, gp.getRightPlayer().getRectangle());
			}
			
			if (collPos != null) {
				switch (collPos) {
				case COLL_UPPER_LEFT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_UPPER:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_UPPER_RIGHT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_RIGHT:
					dx = -dx;
					break;
				case COLL_LOWER_RIGHT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_LOWER:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_LOWER_LEFT:
					dx = -dx;
					dy = -dy;
					break;
				case COLL_LEFT:
					dx = -dx;
					break;
				}
			}
		}
		
		
		pos.x += 3*dx/2;
		pos.y += dy;
		rec.setLocation(pos);

	}

	
	public Rectangle getRec() {
		return rec;
	}

	public void draw(Graphics2D g2) {

		g2.fillOval(pos.x, pos.y, TILE_SIZE, TILE_SIZE);
	}

}
