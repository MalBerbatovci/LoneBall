package com.malberb.game.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.malberb.framework.util.RandomNumberGenerator;
import com.malberb.game.main.GameMain;
import com.malberb.game.main.Resources;

public class BallPowerUp extends PowerUp {
	private final static double MOVE_SPEED = 1.2;
	private final static int WIDTH = 30;
	private final static int HEIGHT = 100;
	private final static int BALL_DIAMETER = 20;
	private int x, y;
	private Rectangle rect;
	private volatile boolean powerUpAlive = false;

	public Ball ball1, ball2, ball3, ball4, ball5;

	public BallPowerUp(int x, int y) {
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
		System.out.println("Paddle action perform happened!");

	}

	@Override
	public ArrayList<Ball> performActionBall(int speed) {
		ArrayList<Ball> balls = new ArrayList<Ball>();

		ball1 = new Ball(RandomNumberGenerator.getRandIntBetween(200, 500),
				RandomNumberGenerator.getRandIntBetween(150, 730), BALL_DIAMETER, BALL_DIAMETER);
		ball1.setVelX(speed);
		balls.add(ball1);

		ball2 = new Ball(RandomNumberGenerator.getRandIntBetween(200, 500),
				RandomNumberGenerator.getRandIntBetween(150, 730), BALL_DIAMETER, BALL_DIAMETER);
		ball2.setVelX(-speed);
		balls.add(ball2);

		ball3 = new Ball(RandomNumberGenerator.getRandIntBetween(200, 500),
				RandomNumberGenerator.getRandIntBetween(150, 730), BALL_DIAMETER, BALL_DIAMETER);
		ball3.setVelX(speed);
		balls.add(ball3);

		ball4 = new Ball(RandomNumberGenerator.getRandIntBetween(200, 500),
				RandomNumberGenerator.getRandIntBetween(150, 730), BALL_DIAMETER, BALL_DIAMETER);
		ball4.setVelX(speed);
		balls.add(ball4);

		ball5 = new Ball(RandomNumberGenerator.getRandIntBetween(200, 500),
				RandomNumberGenerator.getRandIntBetween(150, 730), BALL_DIAMETER, BALL_DIAMETER);
		ball1.setVelX(-speed);
		balls.add(ball5);

		powerUpAlive = true;
		return balls;
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

	// change when you have an image!
	public BufferedImage getImage() {
		return Resources.ballImg;
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public int performActionLife(int lives) {
		return 0;
		
	}

	@Override
	public void performActionSpeedBall(Ball b) {
		// TODO Auto-generated method stub
		
	}

}
