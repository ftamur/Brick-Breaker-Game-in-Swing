package domain;

import Services.Constants;
import Services.HelperFunctions;

import java.util.Timer;
import java.util.TimerTask;


public class Paddle extends PhysicalObject{

	private boolean isExpanded;
	private double rotateAngle;
	private boolean isSticky;
	private double rotationSpeed = 0.6;
	private double releasedRotationSpeed = 1.2;
	private boolean leftRotate=false;
	private boolean rightRotate=false;
	private int destructiveLaserGunShotsLeft = 0;

	private int posX_Left;
	private int posX_Right;


	// singleton paddle object.
	private static Paddle paddle;

	private Paddle() {

		super(0, 0, 0, 0, 0, Constants.T, false, PhysicalObjectType.PADDLE);

		// calculation of posX, posY by width and height
		int posx = (int) Math.floor(Constants.SCREEN_WIDTH * 0.45);
		int posy = (int) Math.floor(Constants.SCREEN_HEIGHT - 50);
		int width = Constants.L;
		this.posX = posx;
		this.posY = posy;
		this.width = width;
		this.isExpanded = false;
		this.rotateAngle = 0;
		this.isSticky = false;

	}

	public static Paddle getInstance(){
		if(paddle==null) paddle = new Paddle();
		return paddle;
	}


	public double getRotateAngle() {
		return rotateAngle;
	}

	public void setRotateAngle(double rotateAngle) {
		this.rotateAngle = rotateAngle;
	}

	@Override
	public void move() {

		if ((this.posX + getWidth() >= Constants.SCREEN_WIDTH && velocityX > 0) ||
				(this.posX <=0 && velocityX < 0)){
			resetDecimals();
		}else{
			super.move();
		}

		posX_Left = this.posX + Constants.LASER_OFFSET;
		posX_Right = this.posX + this.width - Constants.LASER_OFFSET;

	}


	public void movePaddle(Direction direction) {
		velocityX = getMovingSpeed();
		velocityX *= direction == Direction.LEFT ? -1:1;


		int stopDelay = 500; //milliseconds

		TimerTask stopTask = new TimerTask() {
			@Override
			public void run() {
				if (!isSliding()) {
					stop();
				}
			}
		};


		Timer stopTimer = new Timer();
		stopTimer.schedule(stopTask,stopDelay);

	}

	public void slidePaddle(Direction direction) {

		velocityX = getSlidingSpeed();
		velocityX *= direction == Direction.LEFT ? -1:1;
	}

	public void stop() {
		velocityX = 0;
	}

	public Direction getMovingDirection() {
		if (!isMoving())
			return null;
		return velocityX > 0 ? Direction.RIGHT: Direction.LEFT;
	}

	public void rotatePaddle() {

		if(leftRotate) {
			rotateAngle +=rotationSpeed;

			if(rotateAngle>45) 
				rotateAngle=45;

		}
		else if(rightRotate) {
			rotateAngle-=rotationSpeed;

			if(rotateAngle<-45) 
				rotateAngle=-45;

		}
		else {
			if(Math.abs(rotateAngle)<5) 
				rotateAngle=0;

			if(rotateAngle>0)
				rotateAngle-=releasedRotationSpeed;

			else if (rotateAngle<0) {
				rotateAngle+=releasedRotationSpeed;

			}
		}
	}

	public boolean isLeftRotate() {
		return leftRotate;
	}
	public void setLeftRotate(boolean leftRotate) {
		this.leftRotate = leftRotate;
	}
	public boolean isRightRotate() {
		return rightRotate;
	}
	public void setRightRotate(boolean rightRotate) {
		this.rightRotate = rightRotate;
	}
	public boolean isMoving() {
		return Math.abs(velocityX) == getMovingSpeed();
	}
	public boolean isSliding() {
		return Math.abs(velocityX) == getSlidingSpeed();
	}
	public boolean isStationary() {
		return velocityX == 0;
	}
	private double getMovingSpeed() {
		return (double) 1.5 * width / 2.0 / 500.0 * Constants.NEXT_STATE_INTERVAL;
	}
	private double getSlidingSpeed() {
		return (double) 1.5 * width * 2.0 / 1000 * Constants.NEXT_STATE_INTERVAL;
	}
	public boolean isExpanded() {
		return isExpanded;
	}
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}
	public boolean isSticky() {
		return isSticky;
	}
	public void setSticky(boolean isSticky) {
		this.isSticky = isSticky;
	}
	public void setProperties(int posX, int posY, double rotateAngle, double velocityX, double velocityY, int width, int height, boolean isSticky){
		setPosX(posX);
		setPosY(posY);
		setRotateAngle(rotateAngle);
		setVelocityX(velocityX);
		setVelocityY(velocityY);
		setWidth(width);
		setHeight(height);
		setSticky(isSticky);
	}

	public int getDestructiveLaserGunShotsLeft() {
		return destructiveLaserGunShotsLeft;
	}
	public void setDestructiveLaserGunShotsLeft(int destructiveLaserGunShotsLeft) {
		this.destructiveLaserGunShotsLeft = destructiveLaserGunShotsLeft;
	}

	public int getPosX_Left() {
		return posX_Left;
	}

	public int getPosX_Right() {
		return posX_Right;
	}


	public void useDestructiveLaserGun() {
		if (destructiveLaserGunShotsLeft > 0) {
			destructiveLaserGunShotsLeft -= 1;
			fireDestructiveLaserGun();
		}
	}

	// TODO: Implement this
	private void fireDestructiveLaserGun() {

		int index_left;
		int index_right;

		posX_Left = this.posX + Constants.LASER_OFFSET;
		posX_Right = this.posX + this.width - Constants.LASER_OFFSET;

		int gridPosX_Left = HelperFunctions.posXtoGridX(posX_Left);
		int gridPosX_Right = HelperFunctions.posXtoGridX(posX_Right);

		for (int i = 0; i < Constants.BUILDMODE_GRID_ROWS; i++) {

			index_left = i * Constants.BUILDMODE_GRID_COLS + gridPosX_Left;
			index_right = i * Constants.BUILDMODE_GRID_COLS + gridPosX_Right;

			if (index_left != -1) {
				if (GameState.getInstance().getBrickList().get(index_left) != null) {
					GameState.getInstance().getBrickList().get(index_left).destroy();
					GameState.getInstance().getBrickList().set(index_left, null);
				}
			}

			if (index_right != -1) {
				if (GameState.getInstance().getBrickList().get(index_right) != null) {
					GameState.getInstance().getBrickList().get(index_right).destroy();
					GameState.getInstance().getBrickList().set(index_right, null);
				}
			}

		}
	}

}
