package com.malberb.game.model;

import java.awt.Rectangle;

import com.malberb.game.main.GameMain;

public class Paddle {

	private int x, y, width, height, velY;
	private Rectangle rect;
	private int MOVE_SPEED_Y = 10;
	private static final int PADDLE_WIDTH = 15;
	private static final int PADDLE_HEIGHT = 60;

	public Paddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		// bounding box, to check collisions
		rect = new Rectangle(x, y, width, height);
		velY = 0;
	}

	public void update() {
		y += velY;

		// the paddle can either leave the screen if velocity is less than zero
		// which would mean that the top of the paddle has left the top of the
		// screen
		// or the other case, if the bottom of the paddle has left the bottom of
		// screen
		if (y < 0) {
			y = 0;
		} else if (y + height > GameMain.GAME_HEIGHT) {
			y = GameMain.GAME_HEIGHT - height;
		}
		updateRect();
	}

	// used to update the bounding box
	public void updateRect() {
		rect.setBounds(x, y, width, height);
	}

	public void accelUp() {
		velY = -MOVE_SPEED_Y;
	}

	public void accelDown() {
		velY = MOVE_SPEED_Y;
	}

	public void stop() {
		velY = 0;
	}

	// getters so that playState can access the variables in
	// order to render them correctly
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getRect() {
		return rect;
	}
	
	public void setWidth(int newWidth){
		width = newWidth;
	}
	
	public void setHeight(int newHeight){
		height = newHeight;
	}
	
	public void setSpeed(int newSpeed) {
		MOVE_SPEED_Y = newSpeed;
	}
	
	public void reset(){
		this.height = PADDLE_HEIGHT;
		this.width = PADDLE_WIDTH;
		this.MOVE_SPEED_Y = 10;
	}

}
