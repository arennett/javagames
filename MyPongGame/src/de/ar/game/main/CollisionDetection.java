package de.ar.game.main;

import java.awt.Point;
import java.awt.Rectangle;
import de.ar.game.entity.Player;
import de.ar.game.tiles.Tile;
import de.ar.game.tiles.TileManager;
import static de.ar.game.main.GamePanel.*;
public class CollisionDetection {
	private GamePanel gp;

	static public enum tCollPos {
		COLL_UPPER_LEFT,
		COLL_UPPER,
		COLL_UPPER_RIGHT,
		COLL_RIGHT,
		COLL_LOWER_RIGHT,
		COLL_LOWER,
		COLL_LOWER_LEFT,
		COLL_LEFT};
		
		public CollisionDetection(GamePanel gp) {
			this.gp = gp;
			
		}
	
		/**
		 * checks if the rectangle rec intersects a Tile if yes and the tile is a solid
		 * one, it returns the collision position
		 * 
		 * @param rec
		 * @return the collision positon or null if no collision is detected
		 */
		public tCollPos getTileCollPos (Rectangle rec) {
			TileManager tm= gp.getTileManager();
			Point pUL =  new Point  (rec.x,rec.y);
			Point pUR =  new Point  (rec.x+rec.width,rec.y);
			Point pLR =  new Point  (rec.x+rec.width,rec.y+rec.height);
			Point pLL =  new Point  (rec.x,rec.y+rec.height);
					
			Tile tileUL = tm.getTile(pUL);
			Tile tileUR = tm.getTile(pUR);
			Tile tileLR = tm.getTile(pLR);
			Tile tileLL = tm.getTile(pLL);
			
						
			if(tileUL.isSolid() && tileUR.isSolid()) {
				return tCollPos.COLL_UPPER;
			}
			if(tileUR.isSolid() && tileLR.isSolid()) {
				return tCollPos.COLL_RIGHT;
			}
			if(tileLL.isSolid() && tileLR.isSolid()) {
				return tCollPos.COLL_LOWER;
			}
			if(tileLL.isSolid() && tileUL.isSolid()) {
				return tCollPos.COLL_LEFT;
			}
			
			if(tileUL.isSolid()) {
				return tCollPos.COLL_UPPER_LEFT;
			}
			if(tileUR.isSolid()) {
				return tCollPos.COLL_UPPER_RIGHT;
			}
			if(tileLR.isSolid() ) {
				return tCollPos.COLL_LOWER_RIGHT;
			}
			if(tileLL.isSolid() ) {
				return tCollPos.COLL_LOWER_LEFT;
			}
			return null;
				
		}
		/**
		 * Checks if ball has player collision, and return where the ball is touched
		 * @param ball
		 * @param player
		 * @return the collision position if the ball was touched by the player,
		 *         returns null if there is no collision    
		 */
		public tCollPos getBallPlayerCollPos (Rectangle ballRec,Rectangle recPlayer) {
			
			Point pUL =  new Point  (ballRec.x,ballRec.y);
			Point pUR =  new Point  (ballRec.x+ballRec.width,ballRec.y);
			Point pLR =  new Point  (ballRec.x+ballRec.width,ballRec .y+ballRec.height);
			Point pLL =  new Point  (ballRec.x,ballRec.y+ballRec.height);
					
			if  (recPlayer.contains(pUL) && recPlayer.contains(pUR)) {
				return tCollPos.COLL_UPPER;
			}
			if  (recPlayer.contains(pUR) && recPlayer.contains(pLR)) {
				return tCollPos.COLL_RIGHT;
			}
			if  (recPlayer.contains(pLL) && recPlayer.contains(pLR)) {
				return tCollPos.COLL_LOWER;
			}
			if  (recPlayer.contains(pUL) && recPlayer.contains(pLL)) {
				return tCollPos.COLL_LEFT;
			}
			
			if  (recPlayer.contains(pUL)){
				return tCollPos.COLL_UPPER_LEFT;
			}
			if  (recPlayer.contains(pUR)){
				return tCollPos.COLL_UPPER_RIGHT;
			}
			if  (recPlayer.contains(pLL)){
				return tCollPos.COLL_LOWER_LEFT;
			}
			if  (recPlayer.contains(pLR)){
				return tCollPos.COLL_LOWER_RIGHT;
			}
			
		
			return null;
				
		}
		
		/**
		 * 	checks all 4 points of the rectangle if at least one of them is	in a goal 
		 * @param ballRec
		 * @return the Player who makes the goal or -1 if no goal detected
		 */
		public int getGoalDetection(Rectangle ballRec) {
			int ret = -1; // no goal
			
			Point pUL =  new Point  (ballRec.x,ballRec.y);
			Point pUR =  new Point  (ballRec.x+ballRec.width,ballRec.y);
			Point pLR =  new Point  (ballRec.x+ballRec.width,ballRec .y+ballRec.height);
			Point pLL =  new Point  (ballRec.x,ballRec.y+ballRec.height);
			
			
			ret = getPointGoalDetection(pUL);
			if ( ret > -1) {
				return ret;
			}
			ret = getPointGoalDetection(pUR);
			if ( ret > -1) {
				return ret;
			}
			ret = getPointGoalDetection(pLR);
			if ( ret > -1) {
				return ret;
			}
			ret = getPointGoalDetection(pLL);
			if ( ret > -1) {
				return ret;
			}
			return ret;
			
		}
		public int getPointGoalDetection(Point p) {
			TileManager tm= gp.getTileManager();
			if (p.x > BOARD_WIDTH) {
				return Player.PLAYER_LEFT;
			}
			if (p.x < 0) {
				return Player.PLAYER_RIGHT;
			}
			if (p.x > BOARD_WIDTH /2 ) {
				Tile tile = tm.getTile(p);
				if (tile.getType()==Tile.TILE_TYPE_EARTH) {
					return Player.PLAYER_LEFT;
				}
			}else {
				Tile tile = tm.getTile(p);
				if (tile.getType()==Tile.TILE_TYPE_EARTH) {
					return Player.PLAYER_RIGHT;
				}
			}
			
			return -1;
			
		}
		
		
}
