package com.malberb.game.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class PowerUp {

	// called inside playsState (if powerupAlive)
	public abstract void update();

	// called inside update (^) and sets the new boundaries
	public abstract void updateRec();

	//called when they collide, sets alive to false
	public abstract void onCollideWith();

	// perform action of powerUp (shrink/grow)
	public abstract void performActionPaddle(Paddle pL, Paddle pR);
	
	// perform action of powerUp (ball)
	public abstract ArrayList<Ball> performActionBall(int speed);
	
	public abstract int performActionLife(int lives);
	
	public abstract void performActionSpeedBall(Ball b);
	
	public abstract boolean isAlive();
	
	public abstract void reset();
	
	//return rectangle
	public abstract Rectangle getRect();
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public abstract BufferedImage getImage();
	
	public abstract int getID();
	
	
}
