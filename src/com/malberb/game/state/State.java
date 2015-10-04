package com.malberb.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.malberb.game.main.GameMain;

//'super' abstract class for the many different states of the game
//abstract means it is still a superclass (is-a relationship), and not all methods needs to be abstract
public abstract class State {

	// will be called when we transition into a new game state
	public abstract void init();

	// called on every game loop to update
	public abstract void update();

	// called on every game loop to render
	public abstract void render(Graphics g);

	public abstract void onClick(MouseEvent e);

	public abstract void onKeyPress(KeyEvent e);

	public abstract void onKeyRelease(KeyEvent e);
	
	//Inherited method in all states
	public void setCurrentState(State newState)
	{
		GameMain.sGame.setCurrentState(newState);
	}

}
