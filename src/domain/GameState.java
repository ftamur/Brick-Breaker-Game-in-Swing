package domain;

import Services.Constants;
import Services.HelperFunctions;
import domain.Animations.BrickExplosion;
import domain.alien.Alien;
import domain.alien.AlienType;
import domain.brick.Brick;
import domain.brick.BrickType;
import domain.brick.MineBrick;
import domain.factories.AlienFactory;
import domain.powerups.GangOfBalls;
import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;
import domain.powerups.PowerUpObject;
import domain.powerups.timedPowerups.TimedPowerUp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameState{
	private ArrayList<Ball> ballList = new ArrayList<Ball>();
	private ArrayList<Brick> brickList = new ArrayList<Brick>();
	private static boolean returnNewLists = true;
	private CopyOnWriteArrayList<Alien> alienList = new CopyOnWriteArrayList<Alien>();
	private ArrayList<BrickExplosion> brickExplosionList = new ArrayList<>();
	private ArrayList<PowerUp> powerUps = new ArrayList<>();
	private ArrayList<PowerUpObject> powerUpObjects = new ArrayList<>();
	private ArrayList<MineBrick> movingMineBricks = new ArrayList<>();


	private static GameState singleton;

	private GameState() {
		// TODO Auto-generated constructor stub

		start();
	}
	public ArrayList<BrickExplosion> getBrickExplosipnLinkedList(){
		return brickExplosionList;
	}


	public void nextState() {
		reArrangeBrickLists();
		canStartMoving();
		GameStateHelper.getInstance().handleCollisions();
		for (Alien alien : alienList) {
			CollisionHandler.getInstance().intersects(ballList.get(0), alien);
		}

		if(GangOfBalls.getNewBalls().size() != 0){
			ballList.addAll(GangOfBalls.getNewBalls());
			GangOfBalls.clearNewBalls();
		}
		moveThemAll();
		clearDestroyedBall();
		cleanPowerUpObjects();
	}

	private void clearDestroyedBall(){
		ballList.removeIf(ball -> ball.getPosY() > Constants.SCREEN_HEIGHT);
	}

	public void fail(){
		int numLives = Player.getInstance().getNumLives();
		if (numLives >= 1){
			Player.getInstance().setNumLives(numLives-1);
			start();
		}
	}


	private void start(){
		ballList.add(new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth()));
		ballList.forEach(ball-> ball.setSticked(true));
	}

	public static GameState getInstance(){
		if (singleton == null)
			singleton = new GameState();
		return singleton;
	}

	public void addAlien(AlienType alienType) {
		Alien alien = AlienFactory.getInstance().createAlien(alienType);
		this.alienList.add(alien);

	}

	public void releaseBall() {
		ballList.stream().filter(ball -> ball.isSticked()).forEach(ball-> ball.setSticked(false));

	}
	public void setAlienList(List<Alien> alienList) {
		if (alienList.getClass().equals(CopyOnWriteArrayList.class)){
			this.alienList = (CopyOnWriteArrayList) alienList;
		}else {
			CopyOnWriteArrayList cowList = new CopyOnWriteArrayList(alienList);
			this.alienList = cowList;
		}


	}
	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}

	public void usePowerUp(PowerUpEnum type) {
		Class<PowerUp> classType = PowerUpEnum.getType(type);
		PowerUp powerUpToBeUsed = null;
		for (PowerUp powerUp : powerUps) {
			if (powerUp.getClass().equals(classType)) {
				powerUpToBeUsed = powerUp;
				break;
			}
		}
		if (powerUpToBeUsed != null) {
			if (powerUpToBeUsed.getClass().isAssignableFrom(TimedPowerUp.class) && ((TimedPowerUp) powerUpToBeUsed).isInUse())
				return;
			powerUps.remove(powerUpToBeUsed);
			powerUpToBeUsed.activate();
		}
	}

	public ArrayList<PowerUpObject> getPowerUpObjectsList() {
		return powerUpObjects;
	}
	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	private void cleanPowerUpObjects() {
		ArrayList<PowerUpObject> list = new ArrayList<>();
		for (PowerUpObject powerUpObject : powerUpObjects) {
			if (CollisionHandler.getInstance().collides(Paddle.getInstance(), powerUpObject)) {
				PowerUp powerUp = powerUpObject.getPowerUp();
				if (powerUp.getType().equals(PowerUpEnum.DESTRUCTIVE_LASER_GUN)) {
					powerUp.activate();
				} else {
					powerUps.add(powerUp);
				}
			}
			else if (powerUpObject.getPosY() < Constants.SCREEN_HEIGHT)
				list.add(powerUpObject);
		}
		powerUpObjects = list;
	}

	public void canStartMoving() {
		if (movingMineBricks.size() != 0) {
			for (MineBrick mineBrick : movingMineBricks) {
				if(mineBrick!=null&&mineBrick.isStartMoving()==false) {
					boolean noBricksAround=true;
					int posX = mineBrick.getPosX() + mineBrick.getWidth() / 2;
					int posY = mineBrick.getPosY() + mineBrick.getHeight() / 2 - (int) (0.75 * Constants.L);
					for (Brick b : brickList) {
						if (b != null) {
							int bCenterX = b.getPosX() + b.getWidth() / 2;
							int bCenterY = b.getPosY() + b.getHeight() / 2;
							double euclideanDistance = Math.sqrt((posX - bCenterX) * (posX - bCenterX) + (posY - bCenterY) * (posY - bCenterY));
							if (euclideanDistance <= Constants.L *1.7) {
								noBricksAround = false;
								break;
							}
						}
					}
					if (noBricksAround) {
						mineBrick.setStartMoving(true);
					}
				}
			}
		}
	}

	public void reArrangeBrickLists(){
		if(returnNewLists){
			for(int i=0;i<brickList.size();i++){
				Brick b=brickList.get(i);
				if(b!=null&&b.getType()==BrickType.MineBrick&&b.isMoving()){
					movingMineBricks.add((MineBrick)b);
					brickList.set(i,null);
				}
			}
			returnNewLists=false;
		}
	}


	private  void moveThemAll(){
		Paddle.getInstance().move();
		Paddle.getInstance().rotatePaddle();
		alienList.forEach(alien -> alien.move());
		powerUpObjects.forEach(obj -> obj.move());
		ballList.forEach(ball -> ball.move());
		for (Brick b : brickList) {
			if (b != null && b.isMoving()) {
				b.move();
			}
		}
		for (MineBrick b: movingMineBricks) {
			if(b!=null&&b.isStartMoving())
				b.move();
		}
	}



	//Getters And Setters Physical Object Lists
	public ArrayList<Ball> getBallList() {
		return this.ballList;
	}
	public ArrayList<Brick> getBrickList() {
		return this.brickList;
	}

	public ArrayList<Brick> getBrickListWithoutNullValues() {
		ArrayList<Brick> list = new ArrayList<
				>();
		for (Brick brick: brickList){
			if (brick != null)
				list.add(brick);
		}
		return list;
	}

	public CopyOnWriteArrayList<Alien> getAliensList() {
		return this.alienList;
	}
	public ArrayList<MineBrick> getMovingMineBricks() {
		return this.movingMineBricks;
	}
	public ArrayList<BrickExplosion> getBrickExplosionList() {
		return this.brickExplosionList;
	}
	public ArrayList<MineBrick> getMineBrickList() { return movingMineBricks; }
	public void setBallList(ArrayList<Ball> ballList) {
		this.ballList = ballList;
	}
	public void setBrickList(ArrayList<Brick> brickList) {
		this.brickList = brickList;
	}
	public void setMineBrickList(ArrayList<MineBrick> mineBrickList) {
		this.movingMineBricks = mineBrickList;
	}
	
	public void setBrickListFromDB(ArrayList<Brick> brickList) {
		
		/**
		 * This method takes a brickList from DB which does not contains null values.
		 * Because we use null values to find bricks positions in the game. 
		 * We have to set nulls to and brick indices. 
		 */
			
		ArrayList<Brick> brickListWithNulls = new ArrayList<Brick>();
	 	int totalBrickSize = Constants.BUILDMODE_GRID_ROWS * Constants.BUILDMODE_GRID_COLS;

	 	for (int i=0; i < totalBrickSize; i++)
			brickListWithNulls.add(null);
	 	
		for (Brick brick : brickList) {
			
			int gridPosX = HelperFunctions.posXtoGridX(brick.getPosX());
			int gridPosY = HelperFunctions.posYtoGridY(brick.getPosY());
			
			if (gridPosX < 0) gridPosX = 0;			
			if (gridPosY < 0) gridPosY = 0;
				
			int index = gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX;
			
			brickListWithNulls.set(index, brick);
				
		}
		
		this.brickList = brickListWithNulls;
			
	}

}
