package de.ar.game.main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import static de.ar.game.main.GamePanel.*;
import static de.ar.game.entity.Player.*;


public class ScoreControl {
	GamePanel gp ;
	int scPlayerLeft=0;
	int scPlayerRight=0;
	
	
	public ScoreControl(GamePanel gp) {
		this.gp=gp;
	}
	
	public void resetScoring() {
		scPlayerLeft=0;
		scPlayerRight=0;
		
	}
	
	public void score(int player) {
		if (player == PLAYER_LEFT) {
			scPlayerLeft++;
		}
		if (player == PLAYER_RIGHT) {
			scPlayerRight++;
		}
		
	}
	
	public void draw(Graphics2D g2) {
		  Font font = new Font("MONOSPACED", Font.BOLD, 30);
		 
		  g2.setFont(font);
		 
		  FontMetrics fontMetrics = g2.getFontMetrics();
		  String str=""+scPlayerLeft+" : "+scPlayerRight;
		  int strw=fontMetrics.stringWidth(str);
		  int x =(BOARD_WIDTH-strw)/2;
		  
		  // Draw a string such that its base line is at x, y
		 
		  g2.drawString(str,x , 50);
		 
	}

}
