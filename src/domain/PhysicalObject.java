package domain;

import Services.HelperFunctions;

public abstract class PhysicalObject implements DocumentSerializable {
		
	protected int posX;
	protected int posY;
	protected double velocityX;
	protected double velocityY;
	protected int width;
	protected int height; 
	protected boolean isMoving;
	protected PhysicalObjectType objectType;

	private double decimalHolderForPositionX = 0;
	private double decimalHolderForPositionY = 0;

	public PhysicalObject(int posX, int posY, double velocityX, double velocityY, int width, int height, boolean isMoving, PhysicalObjectType type) {
		this.posX = posX;
		this.posY = posY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.width = width;
		this.height = height;
		this.isMoving = isMoving;
		this.objectType = type;
		
	}

	public PhysicalObject() {
	}


	public void move(){
		moveDoubleInX();
		moveDoubleInY();
	}

	public void resetDecimals(){
		decimalHolderForPositionX = 0;
		decimalHolderForPositionY = 0;
	}

	private void moveDoubleInX(){
		decimalHolderForPositionX += velocityX - HelperFunctions.trueFloor(velocityX);
		posX +=  (int) velocityX;
		if  (Math.floor(Math.abs(decimalHolderForPositionX)) != 0 ){
			int whole = HelperFunctions.trueFloor(decimalHolderForPositionX);
			posX += whole;
			decimalHolderForPositionX -= whole;
		}
	}

	private void moveDoubleInY(){
		decimalHolderForPositionY += velocityY - HelperFunctions.trueFloor(velocityY);
		posY +=  (int) velocityY;

		if  (Math.floor(Math.abs(decimalHolderForPositionY)) != 0 ){
			int whole = HelperFunctions.trueFloor(decimalHolderForPositionY);
			posY += whole;
			decimalHolderForPositionY -= whole;
		}
	}

	public int getPosX() { return posX; }
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public double getVelocityX() {
		return velocityX;
	}
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isMoving(){return this.isMoving;}
	public void setMoving(boolean moving){this.isMoving = moving;}
	public PhysicalObjectType getObjectType() {
		return objectType;
	}

	@Override
	public boolean equals(Object obj){
		if((obj == null) || (obj.getClass() != this.getClass())) { return false; }
		PhysicalObject otherObj = (PhysicalObject) obj;
		return this.posX == otherObj.getPosX() &&
				this.posY == otherObj.getPosY() &&
				this.velocityX == otherObj.getVelocityX() &&
				this.velocityY == otherObj.getVelocityY() &&
				this.width == otherObj.getWidth() &&
				this.height == otherObj.getHeight();
	}
}
