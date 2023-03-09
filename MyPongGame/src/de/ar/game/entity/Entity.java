package de.ar.game.entity;

import java.awt.Point;

public class Entity {
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	Point pos;
	int speed;

}
