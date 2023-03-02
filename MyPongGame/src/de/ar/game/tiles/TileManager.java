package de.ar.game.tiles;

import static de.ar.game.main.GamePanel.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

public class TileManager {

	final String FILEPATH_MAINMAP = "res/maps/mainmap.txt";
	final String FILEPATH_WALL_TILE = "res/tiles/wall.png";
	final String FILEPATH_EARTH_TILE = "res/tiles/earth.png";

	int mainmap[][] = new int[TILE_ROWS][TILE_COLS];
	Tile tileMainMap[][] = new Tile[TILE_ROWS][TILE_COLS];

	/**
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	Tile getTile(int row, int col) {
		return tileMainMap[row][col];
	}

	/**
	 * 
	 * @param p arbitrary point on the board
	 * @return the tile with p inside
	 */
	public Tile getTile(Point p) {
		int col = p.x / TILE_SIZE;
		int row = p.y / TILE_SIZE;
		return getTile(row, col);
	}

	
	ImageIcon[] imageIcons = new ImageIcon[3];

	public TileManager() {
		// load map
		imageIcons[0] = null;
		imageIcons[Tile.TILE_TYPE_WALL] = new ImageIcon(FILEPATH_WALL_TILE);
		imageIcons[Tile.TILE_TYPE_EARTH] = new ImageIcon(FILEPATH_EARTH_TILE);
		try {
			loadMainMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void loadMainMap() throws IOException {
		FileReader fileReader;
		fileReader = new FileReader(FILEPATH_MAINMAP);
		BufferedReader buffReader = new BufferedReader(fileReader);
		String line = buffReader.readLine();
		int row = 0;

		while (line != null) {
			String[] strArrLine = line.split(" ");
			int col = 0;
			for (String numStr : strArrLine) {
				int type = Integer.parseInt(numStr);
				mainmap[row][col] = type;
				tileMainMap[row][col] = new Tile(row, col, type);
				col++;
			}

			line = buffReader.readLine();
			row++;
		}

		buffReader.close();
		fileReader.close();
	}

	public void drawTiles(Graphics2D g2) {

		for (int row = 0; row < TILE_ROWS; row++) {
			for (int col = 0; col < TILE_COLS; col++) {
				int num = mainmap[row][col];
				ImageIcon img = imageIcons[num];
				if (img != null) {
					g2.drawImage(img.getImage(), col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
				}

			}
		}

	}

}
