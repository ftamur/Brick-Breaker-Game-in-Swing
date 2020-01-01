package domain.brick;

import java.util.Random;

import Services.Constants;

/**
 * Extends abstract Brick class.
 * MineBrick is a type of Brick which has a circular shape and does circular movement.
 * Also, it causes to explosion when it is hit by ball and the explosion destroys all the bricks
 * in an area of a specific radius.
 */

public class MineBrick extends Brick {

	// Representation Invariant:
	//
	// rotationAngle is 1 or -1, initially -1.
	// 0 <= angle < 360
	// 0 <= posX <= Constants.SCREEN_WIDTH - width
	// 0 <= posY <= Constants.SCREEN_HEIGHT - height
	// -10 < velocityX < 10
	// -10 < velocityY < 10
	// width == height

	/**
	 * rotationDirection: if clockwise movement then 1, otherwise -1.
	 * startMoving: if the movement path is free then true, otherwise false.
	 * angle: state of the circular movement, describes the deviation from initial state.
	 */

	private int rotationDirection=-1;
	private boolean startMoving=false;
	private  double angle=0;

	/**
	 *
	 * Constructor for MineBrick class.
	 * @param posX: x position of the brick.
	 * @param posY: y position of the brick.
	 * @param velocityX: horizontal velocity of the brick.
	 * @param velocityY: vertical velocity of the brick.
	 * @param width: horizontal diameter of the brick.
	 * @param height: vertical diameter of the brick.
	 *
	 */
	public MineBrick(int posX, int posY, double velocityX, double velocityY, int width, int height) {
		super(posX, posY, velocityX, velocityY, width, height);
		this.type = BrickType.MineBrick;
		boolean isMoving=false;
		int random=new Random().nextInt(10);
        if(posX>1.5*Constants.L&&posX<Constants.SCREEN_WIDTH-Constants.L*1.5&&posY>1.5*Constants.L&&posY<Constants.SCREEN_HEIGHT-Constants.L*1.5) {
		 isMoving = random == 0;
		 }
        super.setMoving(isMoving);

	}

	/**
	 * getter for startMoving field.
	 * @return boolean: startMoving field.
	 */
	public boolean isStartMoving() {
		return startMoving;
	}

	/**
	 * setter for startMoving field.
	 * @param startMoving: a boolean value to set startMoving field.
	 */
	public void setStartMoving(boolean startMoving) {
		// @modifies: startMoving.
		// @effects: if startMoving is set to true, then mine brick starts to move in a circular path.
		// otherwise, it does not start moving and stays stable.
		this.startMoving = startMoving;
	}

	/**
	 *  Overridden move() method.
	 *  First, sets horizontal and vertical velocities according to the angle,
	 *  then updates the angle since it moves in a circular path.
	 *  After all, calls the move() function of its superclass.
	 */
	@Override
	public void move(){
		// @modifies: velocityX, velocityY, angle, posX, posY.
		// velocityX and velocityY are set according to angle
		// angle is incremented according to rotation direction and if a lap is done which is 360 degree,
		// then angle is set to 0.
		// @effects: position of mine brick is changed according to modified velocityX, velocityY values and
		// previous position. A circular path is followed.
		this.velocityX=rotationDirection*(Math.cos(Math.toRadians(angle)))*Constants.L/80;
		this.velocityY=rotationDirection*(Math.sin(Math.toRadians(angle)))*Constants.L/80;
		angle+= rotationDirection*-1;
		if(angle==360)angle=0;
        super.move();
	}


	/**
	 * getter for rotationDirection field.
	 * @return int: rotationDirection field.
	 */
	public int getRotationDirection() {
		return rotationDirection;
	}

	/**
	 * setter for rotationDirection field.
	 * Note that, it also calls move() function twice to avoid unexpected bugs.
	 * @param rotationDirection: new value of rotationDirection to set.
	 */
	public void setRotationDirection(int rotationDirection) {
		// @requires: rotationDirection must be either 1 or -1.
		// @modifies: rotationDirection, velocityX, velocityY, angle, posX, posY.
		// @effects: rotationDirection is updated.
		// velocityX, velocityY and angle is changed after calling move.
		// position of brick is changed according to new values of velocityX and velocityY.
		this.rotationDirection = rotationDirection;
	    this.move();
	    this.move();
		this.move();
	}

	/**
	 * getter for angle field.
	 * @return double: angle field.
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * setter for angle field.
	 * @param angle: new value of angle to set.
	 */
	public void setAngle(double angle) {
		// @requires: angle must be between 0 and 360.
		// @modifies: angle.
		// @effects: angle is changed to given new angle value.

		this.angle = angle;
	}

	/**
	 * Abstract function of MineBrick class to present.
	 * @return representation of Mine Brick object as a string.
	 */
	public String toString(){
		return  "MineBrick with: \n" + "position: " + "("+ posX + ","+ posY + ")" +
				" velocity: " + "(" + velocityX + "," +  velocityY + ")"  + " width: " + width + " height: " + height +
			 " angle: " + angle;
	}

	/**
	 * checker of representation invariants.
	 * throws an error if at least one of the invariants is not satisfied.
	 */
	public void repOK(){
		assert rotationDirection != 1 && rotationDirection != -1;
		assert  0 > angle && angle >= 360;
		assert  0 > posX && posX > Constants.SCREEN_WIDTH - width;
		assert  0 > posY && posY > Constants.SCREEN_HEIGHT - height;
		assert  -10 >= velocityX && velocityX >= 10;
		assert  -10 >= velocityY && velocityY >= 10;
		assert  width != height;

	}
 
}
