package gui;

import domain.Animations.BrickExplosion;
import domain.*;
import domain.alien.Alien;
import domain.brick.Brick;
import domain.brick.HalfMetalBrick;
import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;
import domain.powerups.PowerUpObject;

import javax.imageio.ImageIO;

import Services.Constants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Drawer {
	private int gifCounter;
	private static Drawer drawer;
	private int pngCounter;

	File ballFile = new File("Assets/Images/Ball_gold.png");
	BufferedImage ballImage;

	File heartFile = new File("Assets/Images/lifeIcon.png");
	BufferedImage heartImage;


	File fireBallFile = new File("Assets/Images/FireBall.png");
	BufferedImage fireBallImage;


	File chemicalBallFile = new File("Assets/Images/chemical1.png");
	BufferedImage chemicalBallImage;


	File paddleImageFile1 = new File("Assets/Images/paddle1.png");
	File paddleImageFile2 = new File("Assets/Images/paddle2.png");
	File paddleImageFile3 = new File("Assets/Images/paddle3.png");

	BufferedImage paddleImage1;
	BufferedImage paddleImage2;
	BufferedImage paddleImage3;

	// Brick images

	File simpleFile = new File("Assets/Images/Simplebrick.png");
	BufferedImage simpleImage;

	File halfMetalFile = new File("Assets/Images/HalfMetalBrick.png");
	File halfMetalFile2 = new File("Assets/Images/HalfMetalBrick1.png");
	BufferedImage halfMetalImage;
	BufferedImage halfMetalImage2;

	File wrapperFile = new File("Assets/Images/Wrapperbrick.png");
	BufferedImage wrapperImage;

	File mineFile = new File("Assets/Images/MineBrick.png");
	BufferedImage mineImage;



	File fileExp0 = new File("Assets/Images/0.png");
	BufferedImage exp0Image;

	File fileExp1 = new File("Assets/Images/1.png");
	BufferedImage exp1Image;

	File fileExp2 = new File("Assets/Images/2.png");
	BufferedImage exp2Image;


	File fileExp3 = new File("Assets/Images/3.png");
	BufferedImage exp3Image;


	File fileExp4 = new File("Assets/Images/4.png");
	BufferedImage exp4Image;


	File fileExp5 = new File("Assets/Images/5.png");
	BufferedImage exp5Image;

	File fileExp6 = new File("Assets/Images/6.png");
	BufferedImage exp6Image;

	File fileExp7 = new File("Assets/Images/7.png");
	BufferedImage exp7Image;


	File fileExp8 = new File("Assets/Images/8.png");
	BufferedImage exp8Image;

	// Alien images

	File protectedFile = new File("Assets/Images/protective.png");
	BufferedImage protectedImage;

	File cooperativeFile = new File("Assets/Images/cooperative.png");
	BufferedImage cooperativeImage;

	File repairingFile = new File("Assets/Images/repairing.png");
	BufferedImage repairingImage;

	File drunkFile = new File("Assets/Images/drunk.png");
	BufferedImage drunkImage;


	File suprisePowerUp = new File("Assets/Images/faq.png");
	BufferedImage suprise;

	public Drawer() {

		pngCounter = 0;
		readImages();

	}

	private void readImages() {

		gifCounter=0;
		try {

			ballImage = ImageIO.read(ballFile);
			paddleImage1 = ImageIO.read(paddleImageFile1);
			paddleImage2 = ImageIO.read(paddleImageFile2);
			paddleImage3 = ImageIO.read(paddleImageFile3);
			simpleImage = ImageIO.read(simpleFile);
			halfMetalImage = ImageIO.read(halfMetalFile);
			halfMetalImage2 = ImageIO.read(halfMetalFile2);
			wrapperImage = ImageIO.read(wrapperFile);
			mineImage = ImageIO.read(mineFile);
			protectedImage = ImageIO.read(protectedFile);
			cooperativeImage = ImageIO.read(cooperativeFile);
			repairingImage = ImageIO.read(repairingFile);
			drunkImage = ImageIO.read(drunkFile);

			fireBallImage=ImageIO.read(fireBallFile);
			chemicalBallImage=ImageIO.read(chemicalBallFile);
			heartImage = ImageIO.read(heartFile);

			exp0Image=ImageIO.read(fileExp0);
			exp1Image=ImageIO.read(fileExp1);
			exp2Image=ImageIO.read(fileExp2);
			exp3Image=ImageIO.read(fileExp3);
			exp4Image=ImageIO.read(fileExp4);
			exp5Image=ImageIO.read(fileExp5);
			exp6Image=ImageIO.read(fileExp6);
			exp7Image=ImageIO.read(fileExp7);
			exp8Image=ImageIO.read(fileExp8);

			suprise = ImageIO.read(suprisePowerUp);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Drawer getInstance() {

		if (drawer == null)
			drawer = new Drawer();

		return drawer;
	}
	public void drawExplosion(Graphics g,BrickExplosion animation){
		gifCounter=animation.getGifCounter();
		int x=animation.getPosX();
		int y=animation.getPosY();
		int width=animation.getWidth();
		int height=animation.getHeight();
		if(gifCounter<3){
			g.drawImage(exp0Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<6){
			g.drawImage(exp1Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<9){
			g.drawImage(exp2Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<12){
			g.drawImage(exp3Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<15){
			g.drawImage(exp4Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<18){
			g.drawImage(exp5Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<21){
			g.drawImage(exp6Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else if(gifCounter<24){
			g.drawImage(exp7Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else  if (gifCounter<27){
			g.drawImage(exp8Image,x,y,width,height,null);
			animation.setGifCounter(animation.getGifCounter()+1);
		}
		else {

		}


	}


	public void draw(Graphics g, PhysicalObject obj) {
		if(obj!=null) {
			PhysicalObjectType type = obj.getObjectType();
			switch (type) {
			case PADDLE:
				this.drawPaddle(g, (Paddle) obj);
				break;
			case BALL:
				this.drawBall(g, (Ball) obj);
				break;
			case ALIEN:
				this.drawAlien(g, (Alien) obj);
				break;
			case BRICK:
				this.drawBrick(g, (Brick) obj);
				break;
			case POWERUP:
				this.drawPowerupObject(g, (PowerUpObject) obj);
			default:
				break;
			}
		}

	}




	private void drawBrick(Graphics g, Brick brick) {
		// TODO Auto-generated method stub
		int posX = brick.getPosX();
		int posY = brick.getPosY();
		int width = brick.getWidth();
		int height = brick.getHeight();
		switch (brick.getType()){
		case MineBrick:
			g.drawImage(mineImage, posX, posY, width, height, null);
			break;
		case SimpleBrick:
			g.drawImage(simpleImage, posX, posY, width, height, null);
			break;
		case WrapperBrick:
			g.drawImage(wrapperImage, posX, posY, width, height, null);
			break;
		case HalfMetalBrick:
			HalfMetalBrick obj1=(HalfMetalBrick) brick;
			if(!obj1.isFireballHit()) g.drawImage(halfMetalImage, posX, posY, width, height, null);
			else g.drawImage(halfMetalImage2, posX, posY, width, height, null);
			break;
		}
	}

	public void drawLives(Graphics g){
		int numLives = Player.getInstance().getNumLives();
		int lifeIconsLeftEdgeOffset = Constants.LIFE_ICONS_LEFT_EDGE_OFFSET;
		int lifeIconsInBetweenOffset = Constants.LIFE_ICONS_IN_BETWEEN_OFFSET;
		int lifeIconsBottomEdgeOffset = Constants.LIFE_ICONS_BOTTOM_EDGE_OFFSET;
		int lifeIconsEdgeSize = Constants.LIFE_ICON_EDGE_SIZE;
		if(numLives >= 1) {
			for (int i = 0; i < numLives; i++) {
				g.drawImage(heartImage, lifeIconsLeftEdgeOffset + i * lifeIconsInBetweenOffset, Constants.SCREEN_HEIGHT - lifeIconsBottomEdgeOffset, lifeIconsEdgeSize, lifeIconsEdgeSize, null);
			}
		}else{
			gameOver();
		}
	}

	private void gameOver(){
		GameExpert.getInstance().pause();
		Coordinator.getInstance().openScreen(ScreenEnum.GAME_OVER);
	}

	private void drawAlien(Graphics g, Alien alien) {
		// TODO Auto-generated method stub

		int posX = alien.getPosX();
		int posY = alien.getPosY();

		int width = alien.getWidth();
		int height = alien.getHeight();

		switch (alien.getType()){
		case PROTECTING:
			g.drawImage(protectedImage, posX, posY, width, height, null);
			break;
		case COOPERATIVE:
			g.drawImage(cooperativeImage, posX, posY, width, height, null);
			break;
		case REPAIRING:
			g.drawImage(repairingImage, posX, posY, width, height, null);
			break;
		case DRUNK:
			g.drawImage(drunkImage, posX, posY, width, height, null);
			break;
		}

	}

	public void drawPowerupObject(Graphics g, PowerUpObject powerUpObject) {
		g.drawImage(suprise, powerUpObject.getPosX(), powerUpObject.getPosY(), powerUpObject.getWidth(), powerUpObject.getHeight(), null);
	}

	public void drawScore(Graphics g, Score score) {
		// TODO Auto-generated method stub

		g.setColor(Color.RED);
		g.setFont(new Font(Font.SERIF, Font.PLAIN,  30));
		g.drawString("Score: " + score.getCurrentScore(), 170, Constants.SCREEN_HEIGHT-30);

	}

	private void drawPaddle(Graphics g, Paddle obj) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform initial = g2d.getTransform();
		double centerPaddleX = obj.getPosX() + obj.getWidth() / 2;
		double centerPaddleY = obj.getPosY() + obj.getHeight() / 2;
		g2d.rotate(Math.toRadians(obj.getRotateAngle()), centerPaddleX, centerPaddleY);

		if (pngCounter % 21 < 7) {
			g2d.drawImage(paddleImage1, obj.getPosX(), obj.getPosY(), obj.getWidth(), obj.getHeight(), null);
			++pngCounter;
		} else if (pngCounter % 21 > 7 && pngCounter % 21 < 14) {
			g2d.drawImage(paddleImage2, obj.getPosX(), obj.getPosY(), obj.getWidth(), obj.getHeight(), null);
			++pngCounter;
		} else {
			g2d.drawImage(paddleImage3, obj.getPosX(), obj.getPosY(), obj.getWidth(), obj.getHeight(), null);
			if (pngCounter == 21) pngCounter = 0;
			else pngCounter += 1;

		}
		g2d.setTransform(initial);
	}

	private void drawBall(Graphics g, Ball object) {
		BufferedImage ballCurrentImage;
		if(object.isChemical()){
			ballCurrentImage=chemicalBallImage;
		}
		else if(object.isFire()){
			ballCurrentImage=fireBallImage;
		}
		else{
			ballCurrentImage=ballImage;
		}
		
		g.drawImage(ballCurrentImage, object.getPosX(), object.getPosY(), object.getWidth(), object.getHeight(), null);


	}


	public void drawPowerUpCounts(Graphics g, ArrayList<PowerUp> powerUps) {
		HashMap<PowerUpEnum, Integer> counter = new HashMap<>();
		powerUps.forEach(powerUp -> addToCounter(counter, powerUp.getType()));


		int x_offset = Constants.POWERUP_PANEL_LEFT_EDGE_OFFSET;
		int inbetween_offset = Constants.POWERUP_PANEL_IN_BETWEEN_OFFSET;
		int edgeLength = Constants.POWER_UP_ICON_EDGE_LENGTH;
		int y = Constants.SCREEN_HEIGHT - Constants.POWERUP_PANEL_BOTTOM_EDGE_OFFSET;
		int labelOffsetY = 20;
		int labelOffsetX = 3;

		g.setFont(new Font(Font.SERIF, Font.PLAIN,  20));
		for (PowerUpEnum powerUpType : PowerUpEnum.getKeyTriggeredPowerUps()) {
			String count;
			if (powerUpType.equals(PowerUpEnum.DESTRUCTIVE_LASER_GUN)) {
				count = "" + Paddle.getInstance().getDestructiveLaserGunShotsLeft();
			} else {
				count = "" + counter.getOrDefault(powerUpType, 0);
			}
			g.drawString(count, x_offset + labelOffsetX - 5, y + labelOffsetY - 5);
			x_offset += inbetween_offset + edgeLength;

		}
	}

	private void addToCounter(HashMap<PowerUpEnum, Integer> counter, PowerUpEnum key) {
		if (!counter.containsKey(key)) {
			counter.put(key, 0);
		}
		counter.put(key, counter.get(key) + 1);
	}

	public void drawLaserTargetLines(Graphics g) {

		for (int i = 1; i <= 100; i+=2) {
			g.setColor(Color.RED);
			g.fillRect(Paddle.getInstance().getPosX_Left(), i * Paddle.getInstance().getPosY() / 100,
					2, Constants.SCREEN_HEIGHT/100);
			g.fillRect(Paddle.getInstance().getPosX_Right(), i * Paddle.getInstance().getPosY() / 100,
					2, Constants.SCREEN_HEIGHT/100);
	

		}
		
	}




}
