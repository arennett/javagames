package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {

		this.gp = gp;

		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		loadTileImages();
		loadMap("/maps/world01.txt"); // res is a sourcefolder

	}

	public void loadTileImages() {

		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResource("/tiles/grass.png"));

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResource("/tiles/wall.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResource("/tiles/water.png"));
			tile[2].collision = true;

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResource("/tiles/earth.png"));

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResource("/tiles/tree.png"));
			tile[4].collision = true;

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResource("/tiles/sand.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for (int row = 0; row < gp.maxWorldRow; row++) {

				String line = br.readLine();
				String numbers[] = line.split(" ");

				for (int col = 0; col < gp.maxWorldCol; col++) {
					mapTileNum[col][row] = Integer.parseInt(numbers[col]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
//		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize,null);
//		g2.drawImage(tile[0].image, 48, 0, gp.tileSize, gp.tileSize,null);
//		g2.drawImage(tile[0].image, 96, 0, gp.tileSize, gp.tileSize,null);

//		for (int row=0; row < gp.maxScreenRow; row++) {
//			for (int col=0; col < gp.maxScreenCol; col++) {
//				
//				int num = mapTileNum[row][col];
//			
//				int x = col * gp.tileSize;	
//				int y = row * gp.tileSize;
//				
//				g2.drawImage(tile[num].image, x, y, gp.tileSize, gp.tileSize, null);
//		
//			}

		
		for (int col = 0; col < gp.maxWorldCol; col++) {
			for (int row = 0; row < gp.maxWorldRow; row++) {
			
				int num = mapTileNum[col][row];

				int worldX = col * gp.tileSize;
				int worldY = row * gp.tileSize;
				
				int screenX = worldX - gp.player.worldX + gp.player.screenX;
				int screenY = worldY - gp.player.worldY + gp.player.screenY;
				
				if (	screenX >= -gp.tileSize 	&& screenY >= -gp.tileSize
				   && 	screenX < gp.screenWidth 	&& screenY <  gp.screenHeight) 
				{
					g2.drawImage(tile[num].image, screenX, screenY, gp.tileSize, gp.tileSize, null);	
				}
				

			}
		}
		

	}

}
