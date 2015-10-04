package com.malberb.game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.malberb.game.state.LoadState;
import com.malberb.game.state.State;
import com.malberb.framework.util.InputHandler;

@SuppressWarnings("serial")

// Central class to the game, will host loop and have method to start and exit
// the game

// extending as JPanel means this class can now be added to the content pane of
// the JFrame in GameMain
public class Game extends JPanel implements Runnable {
	private int gameWidth; // necessary for setting size
	private int gameHeight;
	private Image gameImage;
	private InputHandler inputHandler;

	// keep track of which state is being shown, volatile means only 1 copy
	private volatile State currentState;

	private Thread gameThread;
	private volatile boolean running;

	public Game(int gameWidth, int gameHeight) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;

		// dimension object holds width and height in one place
		setPreferredSize(new Dimension(gameWidth, gameHeight));
		setBackground(Color.black);

		// necessary for user input
		setFocusable(true);
		requestFocus();

	}

	// method to initialise and set new current state
	public void setCurrentState(State newState) {
		System.gc(); // cleans any unused objects
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);
	}

	// called automatically when our game object has been added to JFrame
	public void addNotify() {
		super.addNotify();
		initInput(); 							//initialize inputHandler
		setCurrentState(new LoadState());
		initGame();								//initialize game
	}

	// create a new thread and then start it, meaning that the run method is
	// called
	private void initGame() {
		running = true;
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}
	
	//method to initialize the input Handler method,
	//and setting the inputHandler as the Listener
	private void initInput() {
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);
		addMouseListener(inputHandler);
		requestFocus();
	}

	@Override
	public void run() {
		while (running) {
			currentState.update();
			prepareGameImage();
			currentState.render(gameImage.getGraphics());
			repaint();

			try {
				Thread.sleep(14);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}

	// prepare off-screen image for
	private void prepareGameImage() {
		if (gameImage == null) {
			// Initialize gameImage
			gameImage = createImage(gameWidth, gameHeight);
		}
		Graphics g = gameImage.getGraphics();
		// clear image
		g.clearRect(0, 0, gameWidth, gameHeight);

	}
	
	public void exit() {
		running = false;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if (gameImage == null) {
			return;
		}
		
		g.drawImage(gameImage, 0, 0, null);
	}

}
