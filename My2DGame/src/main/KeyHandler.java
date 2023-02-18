package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed,keyPressed;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_UP)	{
			upPressed = true;
			keyPressed=true ;
			
		}
		
		if (code == KeyEvent.VK_DOWN)	{
			downPressed = true;
			keyPressed=true;
		}
		
		if (code == KeyEvent.VK_LEFT)	{
			leftPressed = true;
			keyPressed=true;
		}
		
		if (code == KeyEvent.VK_RIGHT)	{
			rightPressed = true;
			keyPressed=true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_UP)	{
			upPressed = false;
			keyPressed=false;
			
		}
		
		if (code == KeyEvent.VK_DOWN)	{
			downPressed = false;
			keyPressed=false;
		}
		
		if (code == KeyEvent.VK_LEFT)	{
			leftPressed = false;
			keyPressed=false;
		}
		
		if (code == KeyEvent.VK_RIGHT)	{
			rightPressed = false;
			keyPressed=false;
		}
		
	}

}
