package domain;

import Services.Constants;

public class Ball extends PhysicalObject{
	//Rep Invariant:
	//|velocityX|<=10
	//|velocityY|<=10
	// velocityY !=0
	//if posX<0, velocityX>0
	//if posX+width > FrameWidth , velocityX<0
	//if posY<0, velocityY>0
	//if posY+height > FrameHeight , velocityY<0
	// isFire and isChemical are both false  or only one of them true
	// isFire & isChemical -> 0
	// Abstraction Function
	// represent a moving circle with center posX+width/2,posY+height/2 and radius=width=height with velocity (velocityX,velocityY).
	// circle can be Chemical,Fire or neither of them

	private boolean isFire;
	private boolean isChemical;
	private boolean isSticked;
	private int differ;

	public int getStickPosX() {
		return stickPosX;
	}

	public void setStickPosX(int stickPosX) {
		this.stickPosX = stickPosX;
	}

	public int getStickPosY() {
		return stickPosY;
	}

	public void setStickPosY(int stickPosY) {
		this.stickPosY = stickPosY;
	}

	private int stickPosX;
    private int stickPosY;
	public double getAngle() {
		return angle;
	}

	private double angle;
	private double sticked_angle;

	/***
	 * Constructor for ball that does not start sticked to the paddle
	 * @param posX 		 initial position X
	 * @param posY 		 initial position Y
	 * @param velocityX  initial velocity X
	 * @param velocityY  initial velocity Y
	 * @param width      Ball width
	 * @param height     Ball height
	 * @param isMoving   is an moving object
	 * @param isSticked  is sticked to paddle
	 */
	public Ball(final int posX, final int posY, final double velocityX, final double velocityY, final int width, final int height, final boolean isMoving,final boolean isSticked) {
		/*
		@EFFECTS Initialize superClass with parameters posX,posY,velocityX,velocityX,width,height,isMoving
		Initialize this with parameter issticked and isFire isChemical as false
		*/
		super(posX, posY, velocityX, velocityY, width, height, isMoving, PhysicalObjectType.BALL);
        this.isFire = false;
        this.isChemical = false;
        this.isSticked = isSticked;
	}
	/***
	 * Another constructor that sets ball's coordinates  according to paddle's coordinates
	 * @param paddleX paddle's X position
	 * @param paddleY paddle's Y position
	 * @param paddleWidth paddle's width
	 */
	public Ball(final int paddleX, final int paddleY, final int paddleWidth) {
		super(0, 0, -3, -3, 17, 17, true, PhysicalObjectType.BALL);
		// calculate initial posX and posY.
        final int posX = paddleX + paddleWidth / 2 - 17 / 2;
		final int posY = paddleY - 20;
		this.posX = posX;
		this.posY = posY;
		this.isFire = false;
		this.isChemical = false;
		this.isSticked=true;


	}
	/***
	 * Move function of ball, updates the x and Y coordinates of ball according to current velocity. If the ball is sticked to
	 * paddle, updates the x and y coordinates according to the paddle position.
	 */
	@Override
	public void move() {
		// @modifies: posX and posY according to the velocityX and velocityY.
		checkVelocity();
		if (!isSticked) {
			super.move();
			if (this.posX <=0){
				velocityX = Math.abs(velocityX);
			}
			if( posX + this.getWidth() >= Constants.SCREEN_WIDTH){
				velocityX = -Math.abs(velocityX);
			}
			if (posY <=0 ) {
				velocityY = Math.abs(velocityY);
			}
		}else {
			angle=Paddle.getInstance().getRotateAngle()-sticked_angle;
			posX = Paddle.getInstance().getPosX() + differ;
			stickPosX=Paddle.getInstance().getPosX()+differ;
			rotateIfPaddleRotated();
		}
		if (this.velocityY == 0)
			this.velocityY = 1;


	}


	public void rotateIfPaddleRotated(){
		double edgeDX = Paddle.getInstance().getWidth() / 2;
		double edgeDY = Paddle.getInstance().getHeight() / 2;

		double ballX = getStickPosX()+this.getWidth()/2-Paddle.getInstance().getPosX() -edgeDX;
		double ballY = -getStickPosY()-this.getHeight()/2+Paddle.getInstance().getPosY() + edgeDY;

		double XRotated = ballX * Math.cos(Math.toRadians(-angle)) - ballY * Math.sin(Math.toRadians(-angle));
		double YRotated = ballY * Math.cos(Math.toRadians(-angle)) + ballX * Math.sin(Math.toRadians(-angle));

		XRotated=XRotated+Paddle.getInstance().getPosX()+edgeDX-this.getWidth()/2;
		YRotated=-YRotated+Paddle.getInstance().getPosY()+edgeDY-this.getHeight()/2;

		this.setPosX((int)XRotated);
        this.setPosY((int)YRotated);


	}



	/***
	 *threshold function for for velocityX and velocityY. If their magnitude is greater than 10,sets their magnitude to 10.
	 */
	public void checkVelocity(){
		if(this.velocityY>10)this.velocityY=10;
		else if(this.velocityY<-10)this.velocityY=-10;
		if(this.velocityX>10)this.velocityX=10;
		else if(this.velocityX<-10)this.velocityX=-10;
	}

	public boolean isFire() {
		return isFire;
	}

	/***
	 * Setter for isFire, if sets isFire value to true, directly makes isChemical value false.
	 * @param isFire boolean value for setting isFire either true or false
	 */
	public void setFire(final boolean isFire) {
		// @modifies: isFire and isChemical
		this.isFire = isFire;
		if(isFire)setChemical(false);
	}

	public boolean isChemical() {
		return isChemical;
	}
	/***
	 * Setter for isChemical, if sets isChemical value to true, directly makes isFire value false.
	 * @param isChemical boolean value for setting isChemical either true or false
	 */
	public void setChemical(final boolean isChemical) {
		// @modifies: isFire and isChemical
		this.isChemical = isChemical;
		if(isChemical)setFire(false);
	}

	public boolean isSticked() {
		return isSticked;
	}
	/***
	 * Setter for isSticked,If isSticked parameter is false, updates ball's velocityX and velocityY values.
	 * @param isSticked boolean value for setting isSticked either true or false
	 */
	public void setSticked(final boolean isSticked){
		// @modifies: isSticked,velocityX,velocityY
		this.isSticked = isSticked;
		if(isSticked==true){
			differ=posX-Paddle.getInstance().getPosX();
			sticked_angle=Paddle.getInstance().getRotateAngle();
			stickPosX=this.posX;
			stickPosY=this.posY;
		}
		else {
			double magnitude=Math.sqrt(this.getVelocityX()*this.getVelocityX()+this.getVelocityY()*this.getVelocityY());
			this.velocityX=(-magnitude*Math.sin(Math.toRadians(-Paddle.getInstance().getRotateAngle())));
			this.velocityY=(-magnitude*Math.cos(Math.toRadians(-Paddle.getInstance().getRotateAngle())));


		}
	}
	public String toString(){
		int centerX=posX+this.width/2;
		int centerY=posY+this.height/2;
        String property;
        if(isFire())property="\nCircle has the fire property";
        else if(isChemical())property="\nCircle has the chemical property";
        else property="";
		return "A circle with center ("+centerX+","+centerY+") with diameter "+this.width+" and velocity vector of the circle has X and Y components respectively ("+velocityX+","+velocityY+")"+property;
	}

	public Ball copy() {
		return new Ball(posX, posY, velocityX, velocityY, width, height, isMoving, isSticked);
	}

	public boolean repOK(){
		if(Math.abs(velocityX)>10)return false;
		else if(Math.abs(velocityY)>10)return false;
		else if(velocityY==0)return false;
		else if(posX<0&&velocityX<=0) return false;
		else if(posY<0&&velocityY<=0) return false;
		else if(posX+this.width>Constants.SCREEN_WIDTH&&velocityX>=0)return false;
		else if(posY+this.height>Constants.SCREEN_HEIGHT&&velocityY>=0)return false;
		else if(!(isChemical&&isFire)) return false;
		else return true;
	}
	
}
