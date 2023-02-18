package de.ar.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel  {
		
	private static final long serialVersionUID = 1L;
	static private GamePanel instance;
	
	static public GamePanel getInstance() {
		if (instance==null) {
			instance = new GamePanel();
		}
		return instance;
	}
		
	public static final int PANEL_SIZE = 700;
	public static final int BOARD_SIZE = 600;
	public static final int BOARD_OFFSET = (PANEL_SIZE - BOARD_SIZE) / 2;
	public static final int CELL_COLS = 3;
	public static final int CELL_ROWS = 3;
	public static final int CELL_SIZE = BOARD_SIZE / CELL_COLS;
	public static final int CELL_TYPE_EMPTY = 0;
	public static final int CELL_TYPE_PLAYER 	= 10;
	public static final int CELL_TYPE_COMP1 	= 1;
	public static final int CELL_TYPE_COMP2 	= 2;
	

	
    private GamePanel() {
		init();
	}

	private void init() {
		setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
		addMouseListener(GameController.getInstance());
	}

		

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawCells(g2);
		g2.dispose();
	}

	private void drawCells(Graphics2D g2) {
		for (int row = 0; row < CELL_ROWS; row++) {
			for (int col = 0; col < CELL_COLS; col++) {
				drawCell(row, col, g2);
			}
		}

	}

	private void drawCell(int row, int col, Graphics2D g2) {
		// first rectangle
		// if not emtpty cross or circle

		int posX = BOARD_OFFSET + col * CELL_SIZE;
		int posY = BOARD_OFFSET + row * CELL_SIZE;

		BasicStroke stroke = new BasicStroke(5);
		g2.setStroke(stroke);

		g2.drawRect(posX, posY, CELL_SIZE, CELL_SIZE);

		int cell_type = GameLogic.getInstance().cellArr[row][col];

		BasicStroke fat_stroke = new BasicStroke(10);
		g2.setStroke(fat_stroke);

		switch (cell_type) {
		case CELL_TYPE_COMP1:
			// draw a cross
			g2.setColor(Color.RED);
			g2.drawLine(posX + 20, posY + 20, posX - 20 + CELL_SIZE - 1, posY - 20 + CELL_SIZE - 1);
			g2.drawLine(posX + 20, posY - 20 + CELL_SIZE - 1, posX - 20 + CELL_SIZE - 1, posY + 20);
			break;
		case CELL_TYPE_COMP2:
			// draw a cross
			g2.setColor(Color.RED);
			g2.drawOval(posX + 20, posY + 20, CELL_SIZE - 40, CELL_SIZE - 40);
			break;	
		case CELL_TYPE_PLAYER:
			g2.setColor(Color.BLACK);
			g2.drawOval(posX + 20, posY + 20, CELL_SIZE - 40, CELL_SIZE - 40);
			break;
		case CELL_TYPE_EMPTY:
			// do nothing
		}
		g2.setColor(Color.BLACK);

	}

	public boolean isOnBoard(Point p) {
		int x = p.x - BOARD_OFFSET;
		int y = p.y - BOARD_OFFSET;

		return (x > 0 && x < BOARD_SIZE) && (y > 0 && y < BOARD_SIZE);
	}

	
	
}
