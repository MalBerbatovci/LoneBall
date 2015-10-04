package com.malberb.game.main;

import javax.swing.JFrame;

//Starting point of the game, contains the main method
public class GameMain {
	private static final String GAME_TITLE = "Lone Ball";
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static Game sGame;
	
	public static void main(String[] args){
		JFrame frame = new JFrame(GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
		sGame = new Game(GAME_WIDTH, GAME_HEIGHT);
		frame.add(sGame);
		frame.pack();								//tells to resize to preferred size
		frame.setIconImage(Resources.iconimage);

	}

}
