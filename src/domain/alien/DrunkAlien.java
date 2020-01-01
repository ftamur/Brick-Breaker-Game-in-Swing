package domain.alien;

import Services.Constants;
import domain.BuildMode;
import domain.GameState;
import domain.Score;
import domain.factories.AlienFactory;

public class DrunkAlien extends Alien{

	private Alien theAlien;
	private int totalBrick;
	private int brokenBrickCount;
	private int counter;

	public DrunkAlien(int posX, int posY, int velocityX, int velocityY, int width, int height,
			boolean isDrunk) {
		// TODO Auto-generated constructor stub
		super(posX, posY, velocityX, velocityY, width, height, isDrunk);
		this.type = AlienType.DRUNK;
		totalBrick = BuildMode.getInstance().getTotalBrickCount();
		brokenBrickCount = Score.getInstance().getBrokenBrickCount();
		counter = 0;
		setBehavior();

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		if (theAlien == null)
			doNothing();
		
		else {
			if (theAlien != null && theAlien.getType().equals(AlienType.REPAIRING) && theAlien.isDone())
				theAlien = AlienFactory.getInstance().createAlien(AlienType.PROTECTING);
			theAlien.move();
			updatePositons();
		}
		
		
			
	}

	private void setBehavior() {
		// TODO Auto-generated method stub

		double ratio = ((double) brokenBrickCount) / totalBrick;
		
		if (ratio <= 0.3)
			theAlien = AlienFactory.getInstance().createAlien(AlienType.COOPERATIVE);
		else if (ratio > 0.4 && ratio <= 0.5)
			theAlien = AlienFactory.getInstance().createAlien(AlienType.PROTECTING);
		else if (ratio > 0.5 && ratio <= 0.6)
			theAlien = AlienFactory.getInstance().createAlien(AlienType.REPAIRING);
		else if (ratio > 0.7) {
			theAlien = AlienFactory.getInstance().createAlien(AlienType.REPAIRING);
			theAlien.setDrunk(true);
		}else
			this.theAlien = null;
		
	}
	
	private void updatePositons() {
		
		this.posX = theAlien.getPosX();
		this.posY = theAlien.getPosY();
		
	}
	
	public void setTheAlien(Alien theAlien) {
		this.theAlien = theAlien;
	}
	
	public Alien getTheAlien() {
		return theAlien;
	}

	private void doNothing() {
		// TODO Auto-generated method stub

		if (counter == 5000 / Constants.NEXT_STATE_INTERVAL)
			GameState.getInstance().getAliensList().remove(this);
		else
			++counter;
	}

	@Override
	public boolean equals(Object obj){
		return super.equals(obj) && ((DrunkAlien) obj).isDrunk == this.isDrunk;
	}

}
