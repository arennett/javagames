package de.ar.game.main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	boolean isKeyPressed_UP       = false;
	boolean isKeyPressed_DOWN     = false;
	boolean isKeyPressed_LEFT     = false;
	boolean isKeyPressed_RIGHT    = false;
	boolean isKeyPressed_W_UP     = false;
	boolean isKeyPressed_S_DOWN   = false;
	boolean isKeyPressed_A_LEFT   = false;
	boolean isKeyPressed_D_RIGHT  = false;
	private GamePanel gp;
	
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_P:
			gp.getGameControl().openDialog();
		break;
		
		
		case KeyEvent.VK_UP:
			this.isKeyPressed_UP=true;
		break;
		case KeyEvent.VK_DOWN:
			isKeyPressed_DOWN	=true;
		break;
		case KeyEvent.VK_LEFT:
			isKeyPressed_LEFT	=true;
		break;
		case KeyEvent.VK_RIGHT:
			isKeyPressed_RIGHT	=true;
		break;
		
		case KeyEvent.VK_W:
			this.isKeyPressed_W_UP=true;
		break;
		case KeyEvent.VK_S:
			isKeyPressed_S_DOWN	=true;
		break;
		case KeyEvent.VK_A:
			isKeyPressed_A_LEFT	=true;
		break;
		case KeyEvent.VK_D:
			isKeyPressed_D_RIGHT	=true;
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			isKeyPressed_UP		=false;
		break;
		case KeyEvent.VK_DOWN:
			isKeyPressed_DOWN	=false;
		break;
		case KeyEvent.VK_LEFT:
			isKeyPressed_LEFT	=false;
		break;
		case KeyEvent.VK_RIGHT:
			isKeyPressed_RIGHT	=false;
		break;
		case KeyEvent.VK_W:
			this.isKeyPressed_W_UP =false;
		break;
		case KeyEvent.VK_S:
			isKeyPressed_S_DOWN	=false;
		break;
		case KeyEvent.VK_A:
			isKeyPressed_A_LEFT	=false;
		break;
		case KeyEvent.VK_D:
			isKeyPressed_D_RIGHT=false;
		break;
		}	
	}
	
	
	public boolean isKeyPressed_UP() {
		return isKeyPressed_UP;
	}

	public boolean isKeyPressed_DOWN() {
		return isKeyPressed_DOWN;
	}

	public boolean isKeyPressed_LEFT() {
		return isKeyPressed_LEFT;
	}

	public boolean isKeyPressed_RIGHT() {
		return isKeyPressed_RIGHT;
	}
	public boolean isKeyPressed_W_UP() {
		return isKeyPressed_W_UP;
	}

	public boolean isKeyPressed_S_DOWN() {
		return isKeyPressed_S_DOWN;
	}

	public boolean isKeyPressed_A_LEFT() {
		return isKeyPressed_A_LEFT;
	}

	public boolean isKeyPressed_D_RIGHT() {
		return isKeyPressed_D_RIGHT;
	}
	
	

}
