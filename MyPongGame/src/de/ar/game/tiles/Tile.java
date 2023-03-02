package de.ar.game.tiles;

import static de.ar.game.main.GamePanel.TILE_SIZE;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Tile {
	public static final int TILE_TYPE_EMPTY=0;
	public static final int TILE_TYPE_WALL=1;
	public static final int TILE_TYPE_EARTH=2;
	
	
	int row=0; 
	int col=0;
	int type=0;
	Point pos;
	Dimension dim;
	Rectangle rect;
	
	public Tile (int row,int col,int type) {
		 this.row=row;
		 this.col=col;
		 this.type=type;
		 pos = new Point(TILE_SIZE*col,TILE_SIZE*row);
		 dim= new Dimension(TILE_SIZE,TILE_SIZE);
		 rect = new Rectangle(pos,dim);
		 
		 
		 
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getType() {
		return type;
	}

	public Point getPos() {
		return pos;
	}

	public Dimension getDim() {
		return dim;
	}

	public Rectangle getRect() {
		return rect;
	}

	public boolean isSolid() {
		return getType()==TILE_TYPE_WALL;
	}
	
	
	
}
