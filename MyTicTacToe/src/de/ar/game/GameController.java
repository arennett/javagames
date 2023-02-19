package de.ar.game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import static de.ar.game.GamePanel.*;

public class GameController implements MouseListener, ActionListener {
static private GameController instance;
		
	static public GameController getInstance() {
		if (instance==null) {
			instance = new GameController();
		}
		return instance;
	}
	
	private GameController() {

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// read position

		Point p = e.getPoint();

		System.out.println("Point clicked:" + p);

		GamePanel gp = GamePanel.getInstance();
		GameLogic gl = GameLogic.getInstance();
		
		// if board is clicked

		if (!gp.isOnBoard(p)) {
			return;
		}

		// find cell
		int x = p.x - BOARD_OFFSET;
		int y = p.y - BOARD_OFFSET;

		int col = x / CELL_SIZE;
		int row = y / CELL_SIZE;

		// if is empty cell// set player cell

		if (gl.cellArr[row][col] == CELL_TYPE_EMPTY) {
			gl.cellArr[row][col] = CELL_TYPE_PLAYER;
			gp.repaint();
		}
		// if empty cells compute else
		// msg to winner
		// start new game

		
		if (!gl.isGameOver()) {
			gl.compute();
		}
		if (gl.isGameOver()) { //GameOver
			int winner = gl.getWinner();
			String winnerStr = "nobody wins";
			if ((winner > CELL_TYPE_EMPTY) && (winner < CELL_TYPE_PLAYER)) {
				winnerStr = "computer wins";
			} else if (winner == CELL_TYPE_PLAYER) {
				winnerStr = "you win";
			}

			JOptionPane.showMessageDialog(null, "GameOver : " + winnerStr);
			gl.startGame();
			
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread() {
			@Override
			public void run() {
				GameLogic.getInstance().simulate();
			}
		};
		t.start();
		
		
	}

}
