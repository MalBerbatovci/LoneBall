package com.malberb.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.malberb.game.main.*;

public class MenuState extends State {

	@Override
	public void init() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Resources.welcome, 0, 0, null);
		
	}

	@Override
	public void onClick(MouseEvent e) {
		setCurrentState(new PlayState());
		//setCurrentState(new GameOverState(0));
		
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_H) {
			setCurrentState(new HelpState());
		}
		
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
