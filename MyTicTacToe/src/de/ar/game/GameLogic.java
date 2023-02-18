package de.ar.game;

import java.util.Random;
import java.util.Vector;
import static de.ar.game.GamePanel.*;

public class GameLogic {
	static private GameLogic instance;
	
	
	
	static public GameLogic getInstance() {
		if (instance==null) {
			instance = new GameLogic();
		}
		return instance;
	}
		
	int cellArr[][];

	class Cell {
		public int row;
		public int col;

		Cell(int row, int col) {
			this.row = row;
			this.col = col;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Cell) {
				Cell otherCell = (Cell) other;
				return this.row == otherCell.row && this.col == otherCell.col;
			}
			return super.equals(other);
		}

	}
	private GameLogic() {
		cellArr = new int[CELL_COLS][CELL_ROWS];
	}
	
	public void cleanCells() {
		for (int row = 0; row < CELL_ROWS; row++) {
			for (int col = 0; col < CELL_COLS; col++) {
				cellArr[row][col] = CELL_TYPE_EMPTY;
			}
		}
	}

	public void startGame() {
		cleanCells();
		GameLogic.getInstance().compute();
    }
	

	public Vector<Cell> getEmptyCells() {
		Vector<Cell> vEmptyCells = new Vector<Cell>();
		for (int row = 0; row < CELL_ROWS; row++) {
			for (int col = 0; col < CELL_COLS; col++) {
				if (cellArr[row][col] == CELL_TYPE_EMPTY) {
					Cell p = new Cell(row, col);
					vEmptyCells.add(p);
				}
			}
		}

		return vEmptyCells;
	}
	
	public boolean compute() {
		
		if (compute_optimized_try_win(CELL_TYPE_COMP1)) {
			return true;
		}
		
		return compute_random(CELL_TYPE_COMP1);
	}
	
	public boolean compute_comp1() {
		
		return compute_random(CELL_TYPE_COMP1);
	}
	
	public boolean compute_comp2() {
		
		if (compute_optimized_try_win(CELL_TYPE_COMP2)) {
			return true;
		}
		
		return compute_random(CELL_TYPE_COMP2);
	}
	
	
	private boolean compute_random(int comp) {

		// find empty cells
		GamePanel gp = GamePanel.getInstance();

		Vector<Cell> vEmptyCells = getEmptyCells();
		if (!vEmptyCells.isEmpty()) {
			Random rnd = new Random();
			int i = rnd.nextInt(vEmptyCells.size());
			Cell p = vEmptyCells.elementAt(i);
			cellArr[p.row][p.col] = comp;
			gp.repaint();
			return true;
		}
		return false;
	}
	
	private boolean compute_optimized_try_win(int comp) {
		GamePanel gp = GamePanel.getInstance();
		// find empty cells

		// find if win is possible
		Vector<Cell> vEmptyCells = getEmptyCells();
		if (!vEmptyCells.isEmpty()) {
			for (Cell p:vEmptyCells) {
				cellArr[p.row][p.col] = comp;
				// test if winner
				if(getWinner() == comp) {
					gp.repaint();
					return true;
				} else {
				// reset to empty	
					cellArr[p.row][p.col] = CELL_TYPE_EMPTY;
				}
			}
		}
		return false;
	}
	
	public boolean isGameOver() {
		
		return getEmptyCells().isEmpty() || getWinner() > 0;
	}
	
	public void simulate () {
		int max_trials = 100;
		TicTacToe.getInstance().labelDrawn.setText("");;
		TicTacToe.getInstance().labelComp1.setText("");;
		TicTacToe.getInstance().labelComp2.setText("");;
		
		int scoreDrawn = 0;
		int scoreComp1 = 0;
		int scoreComp2 = 0;
		
		
		for (int i = 0; i<max_trials;i++) {
			
			boolean endGame = false;
			while (!endGame) {
				compute_comp1();
				endGame = isGameOver();
				if (!endGame) {
					compute_comp2();
				}
				endGame = isGameOver();
				
				if(endGame) {
					int comp = getWinner();
					switch (comp) {
					case 0:
						scoreDrawn++;
						break;
					case CELL_TYPE_COMP1:
						scoreComp1++;
						break;
					case CELL_TYPE_COMP2:
						scoreComp2++;
						break;
						
					}
					GamePanel.getInstance().repaint();
					try {
						Thread.sleep(100);;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cleanCells();
				}
				
			}
			
			
			
			
		}
		
		TicTacToe.getInstance().labelDrawn.setText("drawn: "+scoreDrawn);;
		TicTacToe.getInstance().labelComp1.setText("comp1: "+scoreComp1);;
		TicTacToe.getInstance().labelComp2.setText("comp2: "+scoreComp2);;
		
		
		
		
	}
	
	private boolean compute_optimized_prevent_player_win() {
		GamePanel gp = GamePanel.getInstance();
		// find empty cells

		// find if win is possible
		// find if win is possible
		Vector<Cell> vEmptyCells = getEmptyCells();
		if (!vEmptyCells.isEmpty()) {
			for (Cell p:vEmptyCells) {
				cellArr[p.row][p.col] = CELL_TYPE_COMP1;
				// test if winner
				if(getWinner() == CELL_TYPE_COMP1) {
					gp.repaint();
					return true;
				} else {
				// reset to empty	
					cellArr[p.row][p.col] = CELL_TYPE_EMPTY;
				}
			}
		}
		return false;
	}
	
	
	public int getWinner() {
		boolean found = false;
		for (int row = 0; row < CELL_ROWS; row++) {
			if (cellArr[row][0] > CELL_TYPE_EMPTY) {
				for (int col = 1; col < CELL_COLS; col++) {
					found = cellArr[row][col] == cellArr[row][col - 1];
					if (!found)
						break;
				}
			}
			if (found)
				return cellArr[row][0];
		}
		for (int col = 0; col < CELL_COLS; col++) {
			if (cellArr[0][col] > CELL_TYPE_EMPTY) {
				for (int row = 1; row < CELL_ROWS; row++) {
					found = cellArr[row][col] == cellArr[row - 1][col];
					if (!found)
						break;
				}
			}
			if (found)
				return cellArr[0][col];

		}

		if (cellArr[0][0] > CELL_TYPE_EMPTY) {
			for (int idx = 1; idx < CELL_COLS; idx++) {
				found = cellArr[idx][idx] == cellArr[idx - 1][idx - 1];
				if (!found)
					break;
			}
			if (found)
				return cellArr[0][0];
		}

		if (cellArr[0][CELL_COLS - 1] > CELL_TYPE_EMPTY) {
			for (int idx = 1; idx < CELL_COLS; idx++) {
				found = cellArr[idx][CELL_COLS - 1 - idx] == cellArr[idx - 1][CELL_COLS - idx];
				if (!found)
					break;
			}
			if (found)
				return cellArr[0][CELL_COLS - 1];
		}

		return 0;
	}


}
