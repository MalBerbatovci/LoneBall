package com.malberb.game.model;

import java.awt.Rectangle;

import com.malberb.framework.util.RandomNumberGenerator;
import com.malberb.game.main.GameMain;
import com.malberb.game.main.Resources;

public class Ball {
	private int x, y, width, height, velX, velY;
	private int random;
	private Rectangle rect;

	public Ball(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		random = RandomNumberGenerator.getRandIntBetween(0, 10);
		if (random < 5)
			velX = -5;
		else
			velX = 5;

		velY = RandomNumberGenerator.getRandIntBetween(-4, 5);
		rect = new Rectangle(x, y, width, height);
	}

	public void update() {
		x += velX;
		y += velY;
		correctCollisions();
		updateRect();
	}

	private void updateRect() {
		rect.setBounds(x, y, width, height);

	}

	private void correctCollisions() {
		if (y < 0) {
			y = 0;
		} else if (y + height > GameMain.GAME_HEIGHT) {
			y = GameMain.GAME_HEIGHT - height;
		} else {
			return;
		}

		// these only occur if the BALL has gone outside the screen
		velY = -velY;
		Resources.bounce.play();

	}

	// stops the collision going 'into the paddle'
	public void onCollideWith(Paddle p) {
		// check if right or left first
		if (x < GameMain.GAME_WIDTH / 2) {
			// resolve collision by moving ball outside of the bounding box
			x = p.getX() + p.getWidth();
		} else {
			x = p.getX() - width;
		}

		velX = -velX;
		velY += RandomNumberGenerator.getRandIntBetween(-2, 3);

	}

	public boolean isDead() {
		return (x < 0 || x + width > GameMain.GAME_WIDTH);
	}

	public void reset() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		x = 300;
		y = 200;

		if (random < 5) {
			velX = -5;
		} else {
			velX = 5;
		}
		velY = RandomNumberGenerator.getRandIntBetween(-4, 5);
	}

	public void speedUp() {
		if (velX >= 15 || velX <= -15)
			return;

		if (velX < 0) {
			if (velX == -3)
				velX = -5;
			else
				velX = velX - 5;
		} else {
			if (velX == 3)
				velX = 5;
			else
				velX = velX + 5;
		}
	}

	public void speedDown() {

		if (velX == 3 || velX == -3)
			return;

		if (velX < 0) {
			if (velX == -5)
				velX = -3;
			else
				velX = velX + 5;
		} else {
			if (velX == 5)
				velX = 3;
			else
				velX = velX - 5;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Rectangle getRect() {
		return rect;
	}

	public int getVelX() {
		return velX;
	}

	public int getPositiveVelX() {
		if (velX > 0)
			return velX;
		else
			return -velX;
	}

	public int getNegativeVelX() {
		if (velX < 0)
			return velX;
		else
			return -velX;
	}

	public boolean speeded() {
		if (velX == 5 || velX == -5)
			return false;
		else
			return true;
	}

	public void setVelX(int speed) {
		velX = speed;
	}
}
