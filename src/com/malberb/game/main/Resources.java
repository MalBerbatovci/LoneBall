package com.malberb.game.main;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class Resources {

	public static BufferedImage welcome, iconimage, instructions, line, grow, ballImg, shrink, life, lifePowerUp, fast,
			slow;
	public static AudioClip hit, bounce, powerUpHit, lifeLost, noLivesLeft;
	public static Color darkBlue, darkRed;

	// loads all resources in the game
	public static void load() {
		welcome = loadImage("welcome.png");
		iconimage = loadImage("iconimage.png");
		instructions = loadImage("Instructions.png");
		line = loadImage("line.png");
		grow = loadImage("grow.png");
		ballImg = loadImage("ball.png");
		shrink = loadImage("shrink.png");
		life = loadImage("life.png");
		lifePowerUp = loadImage("lifepowerUp.png");
		fast = loadImage("fast.png");
		slow = loadImage("slow.png");
		hit = loadSound("hit.wav");
		bounce = loadSound("bounce.wav");
		powerUpHit = loadSound("powerUpHit.wav");
		lifeLost = loadSound("lifeLost.wav");
		noLivesLeft = loadSound("noLivesLeft.wav");
		darkBlue = new Color(25, 83, 105);
		darkRed = new Color(105, 13, 13);
	}

	// method to load audioClip
	private static AudioClip loadSound(String filename) {
		URL fileURL = Resources.class.getResource("/resources/" + filename);
		return Applet.newAudioClip(fileURL);
	}

	// method to load BufferedImage
	private static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(Resources.class.getResourceAsStream("/resources/" + filename));
		} catch (Exception e) {
			System.out.println("Error while reading file: " + filename);
			e.printStackTrace();
		}
		return img;

	}

}
