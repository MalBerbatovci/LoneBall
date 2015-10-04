package com.malberb.game.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.malberb.game.main.GameMain;
import com.malberb.game.main.Resources;

public class FastPowerUp extends PowerUp {

	private final static double MOVE_SPEED = 1.2;
	private final static int WIDTH = 90;
	private final static int HEIGHT = 30;
	private int x, y;
	private Rectangle rect;
	private volatile boolean powerUpAlive = false;

	public FastPowerUp(int x, int y) {
		this.x = x;
		this.y = y;

		rect = new Rectangle(x, y, WIDTH, HEIGHT);
		powerUpAlive = true;
	}

	@Override
	public void update() {
		y += MOVE_SPEED;

		// if powerUp has escaped screen then set powerUpAlive as false
		// which will cause it not to be rendered in playState
		if (y > GameMain.GAME_HEIGHT) {
			powerUpAlive = false;
			return;
		}

		updateRec();
	}

	@Override
	public void updateRec() {
		rect.setBounds(x, y, WIDTH, HEIGHT);
	}

	@Override
	public void onCollideWith() {
		powerUpAlive = false;
	}

	@Override
	public void performActionPaddle(Paddle pL, Paddle pR) {
		
	}

	public Rectangle getRect() {
		return rect;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public boolean isAlive() {
		return powerUpAlive;
	}

	@Override
	public void reset() {
		powerUpAlive = false;
	}
	
	public BufferedImage getImage() {
		return Resources.fast;
	}

	@Override
	public ArrayList<Ball> performActionBall(int speed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		return 4;
	}

	@Override
	public int performActionLife(int lives) {
		// TODO Auto-generated method stub
		return 0;
		
	}

	@Override
	public void performActionSpeedBall(Ball b) {
		b.speedUp();
	}

}
