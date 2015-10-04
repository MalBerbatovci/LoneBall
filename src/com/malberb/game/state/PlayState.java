package com.malberb.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.malberb.framework.util.RandomNumberGenerator;
import com.malberb.game.main.GameMain;
import com.malberb.game.main.Resources;
import com.malberb.game.model.Ball;
import com.malberb.game.model.BallPowerUp;
import com.malberb.game.model.FastPowerUp;
import com.malberb.game.model.GrowPowerUp;
import com.malberb.game.model.LifePowerUp;
import com.malberb.game.model.Paddle;
import com.malberb.game.model.PowerUp;
import com.malberb.game.model.ShrinkPowerUp;
import com.malberb.game.model.SlowPowerUp;

public class PlayState extends State {
	private Paddle paddleLeft, paddleRight;
	private static final int PADDLE_WIDTH = 15;
	private static final int PADDLE_HEIGHT = 60;

	private int playerScore = 0;
	private int rally = 0;
	private Font scoreFont;
	private Font pauseFont;
	private Ball ball;
	private static final int BALL_DIAMETER = 20;
	private static final int NO_POWERUPS = 6;
	private int lives = 2;

	private int speed;
	private PowerUp powerUp;
	private BufferedImage powerUpImage;
	private boolean powerUpInstatiated = false;

	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private boolean newBalls = false;
	private boolean gamePaused = false;

	private int powerUpNo;

	@Override
	public void init() {
		paddleLeft = new Paddle(0, (GameMain.GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT);
		paddleRight = new Paddle(GameMain.GAME_WIDTH - 15, (GameMain.GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
				PADDLE_WIDTH, PADDLE_HEIGHT);

		// initialize Font object, name, bold, and size
		scoreFont = new Font("SansSerif", Font.BOLD, 25);
		pauseFont = new Font("SansSerif", Font.BOLD, 70);

		ball = new Ball(300, 200, BALL_DIAMETER, BALL_DIAMETER);
	}

	@Override
	public void update() {

		if (gamePaused)
			return;

		paddleLeft.update();
		paddleRight.update();

		if (newBalls) {
			for (int i = 0; i < balls.size(); i++) {
				balls.get(i).update();

				if (balls.size() == 1) {
					if (rally >= 3) {
						if (balls.get(i).getPositiveVelX() == 3)
							rally = 1;
						else
							rally = 2;
					}
					newBalls = false;
					ball = balls.get(0);
					return;
				}
				if (ballCollides(paddleLeft, balls.get(i))) {
					playerScore++;
					rally++;
					balls.get(i).onCollideWith(paddleLeft);
					Resources.hit.play();
				} else if (ballCollides(paddleRight, balls.get(i))) {
					playerScore++;
					rally++;
					balls.get(i).onCollideWith(paddleRight);
					Resources.hit.play();
				} else if (balls.get(i).isDead())
					balls.remove(i);
			}
			return;
		}

		ball.update();

		if (ballCollides(paddleLeft, ball)) {
			playerScore++;
			rally++;
			ball.onCollideWith(paddleLeft);
			Resources.hit.play();
		} else if (ballCollides(paddleRight, ball)) {
			playerScore++;
			rally++;
			ball.onCollideWith(paddleRight);
			Resources.hit.play();
		} else if (ball.isDead()) {
			playerScore -= 3;
			ball.reset();
			rally = 0;
			lives--;

			if (lives <= 0){
				setCurrentState(new GameOverState(playerScore));
				Resources.noLivesLeft.play();
			}
			
			Resources.lifeLost.play();

			if (powerUpInstatiated) {
				powerUp.reset();
				paddleLeft.reset();
				paddleRight.reset();
				powerUpInstatiated = false;
			}
		}

		if (!ball.speeded()) {
			if (rally == 3) {
				createNewPowerUp();
				rally = 0;
			}
		} else if (ball.getPositiveVelX() == 3) {
			if (rally == 2) {
				createNewPowerUp();
				rally = 0;
			}
		} else {
			if (rally == 5) {
				if (!powerUp.isAlive())
					createNewPowerUp();
				rally = 0;
			}
		}

		if (powerUpInstatiated && powerUp.isAlive()) {
			powerUp.update();
		}

		if (powerUpInstatiated && powerUp.isAlive() && powerUpHit(ball)) {
			powerUpHitEffect();

			if (ball.getPositiveVelX() == 10) {
				paddleLeft.setSpeed(15);
				paddleRight.setSpeed(15);
			} else if (ball.getPositiveVelX() == 15) {
				paddleLeft.setSpeed(20);
				paddleRight.setSpeed(20);
			}

		}
	}

	private void powerUpHitEffect() {

		Resources.powerUpHit.play();
		// grow or shrink power up
		if (powerUp.getID() == 0 || powerUp.getID() == 2) {
			powerUp.performActionPaddle(paddleLeft, paddleRight);
			powerUp.onCollideWith();
		}
		// ball power up
		else if (powerUp.getID() == 1) {
			speed = ball.getPositiveVelX();
			balls = powerUp.performActionBall(speed);
			balls.add(ball);
			newBalls = true;
			powerUp.onCollideWith();
		}
		// life powerUp
		else if (powerUp.getID() == 3) {
			lives = powerUp.performActionLife(lives);
			powerUp.onCollideWith();
		}

		else if (powerUp.getID() == 4 || powerUp.getID() == 5) {
			powerUp.performActionSpeedBall(ball);
			powerUp.onCollideWith();

			if (ball.getPositiveVelX() < 10) {
				paddleLeft.setSpeed(10);
				paddleRight.setSpeed(10);
			}
		}

		if(ball.getPositiveVelX() >= 10)
			return;
		else
		rally = 0;

	}

	private void createNewPowerUp() {
		powerUpNo = RandomNumberGenerator.getRandIntBetween(0, NO_POWERUPS);
		// int powerUpNo = 1;

		switch (powerUpNo) {
		case 0:
			if (paddleLeft.getHeight() == 220) {
				createNewPowerUp();
				break;
			}
			powerUp = new GrowPowerUp(GameMain.GAME_WIDTH / 2 - RandomNumberGenerator.getRandIntBetween(-100, 100), 0);
			powerUpInstatiated = true;
			powerUpImage = powerUp.getImage();
			rally = 0;
			break;
		case 1:
			powerUp = new BallPowerUp(GameMain.GAME_WIDTH / 2 - RandomNumberGenerator.getRandIntBetween(-100, 100), 0);
			powerUpInstatiated = true;
			powerUpImage = powerUp.getImage();
			rally = 0;
			break;
		case 2:
			if (paddleLeft.getHeight() < 60) {
				createNewPowerUp();
				break;
			}
			powerUp = new ShrinkPowerUp(GameMain.GAME_HEIGHT / 2 - RandomNumberGenerator.getRandIntBetween(-100, 100),
					0);
			powerUpInstatiated = true;
			powerUpImage = powerUp.getImage();
			rally = 0;
			break;
		case 3:
			if (lives >= 3) {
				createNewPowerUp();
				break;
			}
			powerUp = new LifePowerUp(GameMain.GAME_HEIGHT / 2 - RandomNumberGenerator.getRandIntBetween(-100, 100), 0);
			powerUpInstatiated = true;
			powerUpImage = powerUp.getImage();
			rally = 0;
			break;
		case 4:
			if (ball.getPositiveVelX() >= 15) {
				createNewPowerUp();
				break;
			}
			powerUp = new FastPowerUp(GameMain.GAME_HEIGHT / 2 - RandomNumberGenerator.getRandIntBetween(-100, 100), 0);
			powerUpInstatiated = true;
			powerUpImage = powerUp.getImage();
			rally = 0;
			break;
		case 5:
			if (ball.getPositiveVelX() == 3) {
				createNewPowerUp();
				break;
			}
			powerUp = new SlowPowerUp(GameMain.GAME_HEIGHT / 2 - RandomNumberGenerator.getRandIntBetween(-100, 100), 0);
			powerUpInstatiated = true;
			powerUpImage = powerUp.getImage();
			rally = 0;
			break;

		}

	}

	@Override
	public void render(Graphics g) {

		g.setColor(Resources.darkBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);

		g.setColor(Resources.darkRed);
		g.fillRect(GameMain.GAME_WIDTH / 2, 0, GameMain.GAME_WIDTH / 2, GameMain.GAME_HEIGHT);

		// minus 2 as the width of the image is 4px
		g.drawImage(Resources.line, (GameMain.GAME_WIDTH / 2) - 2, 0, null);

		// Draw paddles
		g.setColor(Color.white);
		g.fillRect(paddleLeft.getX(), paddleLeft.getY(), paddleLeft.getWidth(), paddleLeft.getHeight());
		g.fillRect(paddleRight.getX(), paddleRight.getY(), paddleRight.getWidth(), paddleRight.getHeight());

		if (newBalls) {
			for (int i = 0; i < balls.size(); i++) {
				g.drawRect(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getWidth(), balls.get(i).getHeight());
			}
		} else
			g.drawRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

		g.setFont(scoreFont);
		g.drawString("" + playerScore, 700, 40);

		if (powerUpInstatiated && powerUp.isAlive()) {
			// g.drawImage....
			// g.fillRect(powerUp.getX(), powerUp.getY(), powerUp.getWidth(),
			// powerUp.getHeight());
			g.drawImage(powerUpImage, powerUp.getX(), powerUp.getY(), null);
		}

		drawLives(g);

		if (gamePaused) {
			g.setColor(Color.WHITE);
			g.setFont(pauseFont);
			g.drawString("PAUSED", (GameMain.GAME_WIDTH / 2) - 122, (GameMain.GAME_HEIGHT / 2) + 20);

			g.setColor(Color.WHITE);
			g.setFont(scoreFont);
			g.drawString("Press 'p' to resume", 500, 420);
			return;
		}
	}

	private void drawLives(Graphics g) {
		if (lives == 0)
			return;

		switch (lives) {
		case 1:
			g.drawImage(Resources.life, 60, 20, null);
			break;
		case 2:
			g.drawImage(Resources.life, 60, 20, null);
			g.drawImage(Resources.life, 90, 20, null);
			break;
		case 3:
			g.drawImage(Resources.life, 60, 20, null);
			g.drawImage(Resources.life, 90, 20, null);
			g.drawImage(Resources.life, 120, 20, null);
			break;
		}
	}

	@Override
	public void onClick(MouseEvent e) {
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			paddleLeft.accelUp();
			paddleRight.accelDown();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			paddleLeft.accelDown();
			paddleRight.accelUp();
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			gamePaused = !(gamePaused);
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			paddleLeft.stop();
			paddleRight.stop();

		}

	}

	// check if ball has collided with a paddle
	private boolean ballCollides(Paddle p, Ball b) {
		return b.getRect().intersects(p.getRect());
	}

	private boolean powerUpHit(Ball b) {
		return powerUp.getRect().intersects(b.getRect());
	}

}
