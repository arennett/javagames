package de.ar.game.main;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class EmptyPanel extends JPanel implements KeyListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  EmptyPanel() {
          this.setPreferredSize(new Dimension(500,500));
          this.addKeyListener(this);
          this.setFocusable(true);
          this.requestFocusInWindow();
          System.out.println("KeyListener added");
    }

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped");
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("keyReleased");
		
	}
	
}
