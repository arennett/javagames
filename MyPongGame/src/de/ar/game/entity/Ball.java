package de.ar.game.entity;

import static de.ar.game.main.GamePanel.*;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import de.ar.game.main.CollisionDetection;
import de.ar.game.main.CollisionDetection.tCollPos;
import de.ar.game.main.GamePanel;
import de.ar.game.sound.SoundPlayer;

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
			gp.getScoreControl().score(Player.PLAYER_LEFT);
			dx = speed;
			pos.x = gp.getLeftPlayer().pos.x+2*gp.getLeftPlayer().dim.width;;
			pos.y = gp.getLeftPlayer().pos.y;
			
		}
		if (player==Player.PLAYER_RIGHT) {
			gp.getScoreControl().score(Player.PLAYER_RIGHT);
			dx = -speed;
			Point p=gp.getRightPlayer().pos;
			pos.x = gp.getRightPlayer().pos.x-2*gp.getRightPlayer().dim.width;
			pos.y = gp.getRightPlayer().pos.y;
		}
				  
		
		//collisions
		tCollPos collPos = null;

		if (collPos == null) {
			collPos = colldetect.getTileCollPos(newrec);
			if (collPos != null) {
				gp.getSoundPlayer().play(SoundPlayer.CLIP_BALL_BEEP1);
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
				gp.getSoundPlayer().play(SoundPlayer.CLIP_BALL_BEEP2);
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

	
	public void setStartPos() {
		pos.x=BOARD_WIDTH /2 -dim.width/2;
		pos.y=BOARD_HEIGHT/2 -dim.height/2;
		
	}
	
	public Dimension getDim() {
		return dim;
	}

	public void draw(Graphics2D g2) {

		g2.fillOval(pos.x, pos.y, TILE_SIZE, TILE_SIZE);
	}

}
