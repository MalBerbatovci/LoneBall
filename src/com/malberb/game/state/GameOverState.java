package com.malberb.game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.malberb.game.main.GameMain;
import com.malberb.game.main.Resources;

public class GameOverState extends State {
	private int playerScore;
	private Font scoreFont;
	private Font gameOverFont;
	private Font clickFont;
	
	public GameOverState(int playerScore) {
		this.playerScore = playerScore;
	}

	@Override
	public void init() {
		scoreFont = new Font("SansSerif", Font.CENTER_BASELINE, 50);
		gameOverFont = new Font("SansSerif", Font.BOLD, 75);
		clickFont = new Font("Courier", Font.PLAIN, 35);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Resources.darkRed);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);

		g.setColor(Color.white);
		g.setFont(gameOverFont);
		g.drawString("Game Over!", 168, 100);

		g.setFont(scoreFont);
		g.drawString("Score :		" + "" + playerScore, 175, 250);

		g.setColor(Color.GREEN);
		g.setFont(clickFont);
		g.drawString("-CLICK TO PLAY AGAIN-", 300, 400);
		

	}

	@Override
	public void onClick(MouseEvent e) {
		setCurrentState(new PlayState());

	}

	@Override
	public void onKeyPress(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
