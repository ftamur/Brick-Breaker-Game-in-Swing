package domain.alien;

import domain.GameState;
import domain.Score;
import domain.brick.BrickType;
import domain.factories.BrickFactory;

import java.util.Random;

import Services.Constants;

public class RepairingAlien extends Alien{

	private Random random;
	private int index;
	private int targetPosX;
	private int targetPosY;
	private int counter;
	private int times;
	private boolean firstTarget = true;
	
	public RepairingAlien(int posX, int posY, int velocityX, int velocityY,
			int width, int height, boolean isDrunk) {

		super(posX, posY, velocityX, velocityY, width, height, isDrunk);
		this.type = AlienType.REPAIRING;
		random = new Random();
		times = random.nextInt(Constants.REPAIRING_ALIEN_TIMES) + 1;
	

	}
 
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		if (firstTarget) {
			pickPosition();
			firstTarget = false;
		}

		if (times != 0) {
			goToTarget(this.posX, this.posY, targetPosX, targetPosY);
			
		} else {
								
			if (!this.isDrunk())
				GameState.getInstance().getAliensList().remove(this);
			else
				this.setDone(true);
				
		}


	}

	private void putBrick() {
		// TODO Auto-generated method stub

		GameState.getInstance().getBrickList().set(index, 
				BrickFactory.getInstance().createBrick(BrickType.SimpleBrick, targetPosX, targetPosY, 0, 0,
						Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT));
		
		Score.getInstance().setTotalBrickCount(Score.getInstance().getTotalBrickCount() + 1);

	}


	private void pickPosition() {
		// TODO Auto-generated method stub

		this.index = random.nextInt(GameState.getInstance().getBrickList().size());

		while(GameState.getInstance().getBrickList().get(this.index) != null) {
			this.index = random.nextInt(GameState.getInstance().getBrickList().size());
		}

		targetPosX = indexToPosX(this.index);
		targetPosY = indexToPosY(this.index);

	} 

	
	private int indexToPosX(int index) {

		return (index % Constants.BUILDMODE_GRID_COLS) * Constants.BRICK_WIDTH + Constants.BUILDMODE_GRID_INITIAL_X;

	}

	private int indexToPosY(int index) {

		return (index / Constants.BUILDMODE_GRID_COLS) * Constants.BRICK_HEIGHT + Constants.BUILDMODE_GRID_INITIAL_Y;

	}
	
	public boolean isDone() {
		return isDone;
	}

	private void goToTarget(int posX_1, int posY_1, int posX_2, int posY_2) {

		if (posX_1 - posX_2 < 0)
			this.posX += this.velocityX;
		else if (posX_1 - posX_2 > 0)
			this.posX -= this.velocityX;
		else if (posY_1 - posY_2 < 0)
			this.posY += this.velocityY;
		else if(posY_1 - posY_2 > 0)
			this.posY -= this.velocityY;
		else {
			counter++;
			if (counter == 5000 / Constants.NEXT_STATE_INTERVAL) {
				putBrick();
				pickPosition();
				counter = 0;
				times--;
			}
		}
	}


}
