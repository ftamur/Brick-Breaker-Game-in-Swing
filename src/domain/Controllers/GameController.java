package domain.Controllers;


import Services.Constants;
import Services.Database.DatabaseStrategy;
import domain.*;
import domain.powerups.PowerUpEnum;


public class GameController {
	
	private GameState gameState;
	private KeyPressWithTime lastPressWithTime;
	
	public static GameController gameController;
	
	private GameController() {
		
		this.gameState = GameState.getInstance();
			
	}


	public void rotate(Direction direction){
		if(direction == Direction.RIGHT){
			Paddle.getInstance().setRightRotate(true);
		}else {
			Paddle.getInstance().setLeftRotate(true);
		}
	}

	public void shoot(){
		gameState.releaseBall();
	}

	public void movePaddle(Direction direction){

		if (Paddle.getInstance().isMoving() && ((Paddle.getInstance().getVelocityX()> 0 && direction == Direction.LEFT) ||
				(Paddle.getInstance().getVelocityX()< 0 && direction == Direction.RIGHT))) {
			Paddle.getInstance().stop();
		}

		if (lastPressWithTime == null) {
			lastPressWithTime = new KeyPressWithTime(direction);
		}

		long interval =  System.currentTimeMillis()- lastPressWithTime.getTime();

		if (interval > Constants.HOLD_THRESHOLD && !Paddle.getInstance().isSliding()) {
			Paddle.getInstance().slidePaddle(lastPressWithTime.getDirection());
		}else if (Paddle.getInstance().isStationary()) {
			Paddle.getInstance().movePaddle(lastPressWithTime.getDirection());
		}
	}

	public void stopPaddle(){
		lastPressWithTime = null;
		if(Paddle.getInstance().isSliding()) {
			Paddle.getInstance().stop();
		}
	}

	public void stopRotate(){
		Paddle.getInstance().setLeftRotate(false);
		Paddle.getInstance().setRightRotate(false);
	}

	public void save(){
		if (GameExpert.getInstance().getThreadState() != Thread.State.WAITING)
			GameExpert.getInstance().pause();
		DatabaseStrategy.getInstance().saveGame();
	}

	public void pause(){
		if (GameExpert.getInstance().getThreadState() == Thread.State.WAITING)
			GameExpert.getInstance().resume();
		else
			GameExpert.getInstance().pause();
	}

	public void load(){
		if (GameExpert.getInstance().getThreadState() != Thread.State.WAITING)
			GameExpert.getInstance().pause();
		DatabaseStrategy.getInstance().loadGame();
	}

	public void activateChemicalBall(){
		GameState.getInstance().usePowerUp(PowerUpEnum.CHEMICAL_BALL);
	}

	public void activateMagnet(){
		GameState.getInstance().usePowerUp(PowerUpEnum.MAGNET);
	}

	public void activateTallerPaddle(){
		GameState.getInstance().usePowerUp(PowerUpEnum.TALLER_PADDLE);
	}

	public void useDestructiveLaserGun(){
		Paddle.getInstance().useDestructiveLaserGun();
	}

	public static GameController getInstance() {
		// TODO Auto-generated method stub
		
		if (gameController == null)
			gameController = new GameController();
		
		return gameController;

	}
	
	

}
