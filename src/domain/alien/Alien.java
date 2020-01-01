package domain.alien;

import domain.PhysicalObject;
import domain.PhysicalObjectType;

public class Alien extends PhysicalObject{
	
	protected AlienType type;
	protected boolean isDrunk;
	protected boolean isDone;

    public Alien(int posX, int posY, double velocityX, double velocityY, int width, int height, boolean isDrunk) {
		super(posX, posY, velocityX, velocityY, width, height, false, PhysicalObjectType.ALIEN);

		this.isDone = false;
		this.isDrunk = false;
		
	}
    
    public Alien() {
		// TODO Auto-generated constructor stub
	}
    
	public AlienType getType() {
		return this.type;
	}

	public boolean isDrunk() {
		return this.isDrunk;
	}
	
	public void setDrunk(boolean isDrunk) {
		this.isDrunk = isDrunk;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
}

