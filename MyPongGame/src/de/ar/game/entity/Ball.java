package de.ar.game.entity;
import static de.ar.game.main.GamePanel.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import de.ar.game.main.GamePanel;
import de.ar.game.tiles.Tile;
//import de.ar.game.tiles.TileManager.Tile;

public class Ball extends Entity{
	int dx;
	int dy;
	private GamePanel gp;
	public Ball(GamePanel gp) {
		this.gp = gp;
		pos = new Point (BOARD_WIDTH /2,BOARD_HEIGHT/2);
		speed =1;
		dx = speed;
		dy = speed;
	}
	
	public void move() {
		// get upper Tile 
		Point newPos = new Point();
		Point newPos2= new Point();
		newPos.x+=pos.x+dx;
		newPos.y+=pos.y+dy;
		newPos2.x+=pos.x+dx+TILE_SIZE;
		newPos2.y+=pos.y+dy+TILE_SIZE;
		
		
//		if (newPos.x <0) {
//			dx = -dx;
//		}
//		if (newPos.x + TILE_SIZE > BOARD_WIDTH) {
//			dx = -dx;
//		}
//		if (newPos.y <0) {
//			dy = -dy;
//		}
//		if (newPos.y +TILE_SIZE> BOARD_HEIGHT) {
//			dy = -dy;
//		}
		
		Tile tile1 = gp.getTileManager().getTile(newPos);
		Tile tile2 = gp.getTileManager().getTile(newPos2);
		Tile tile=null;
		if (tile1.getType() == Tile.TILE_TYPE_WALL) {
			tile=tile1;
		}else if (tile2.getType() == Tile.TILE_TYPE_WALL) {
			tile=tile2;
		}
		if (tile!=null) {
			if (tile.getCol() ==0) {
				dx = -dx;
			}
			if (tile.getCol() ==TILE_COLS-1) {
				dx = -dx;
			}
			if (tile.getRow() ==0) {
				dy = -dy;
			}
			if (tile.getRow() ==TILE_ROWS-1) {
				dy = -dy;
			}
			
			
		}
		
		pos.x+=dx;
		pos.y+=dy;
		
	}
	
	public void draw(Graphics2D g2) {
		
		g2.fillOval(pos.x,pos.y,TILE_SIZE, TILE_SIZE);
	}
	
}
