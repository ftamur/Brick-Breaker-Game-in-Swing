package domain.alien;

import Services.Constants;

public class ProtectingAlien extends Alien{

	public ProtectingAlien(int posX, int posY, int velocityX, int velocityY, int width,
                           int height, boolean isDrunk) {
        super(posX, posY, velocityX, velocityY, width, height, isDrunk);
        this.type = AlienType.PROTECTING;

	}
	
	@Override
	public void move() {

		if ((this.posX + getWidth() > Constants.SCREEN_WIDTH && velocityX > 0) ||
				(this.posX <0 && velocityX < 0)) {
			
			this.velocityX = -velocityX;
			
		}
		
		this.posX += this.velocityX;
		
			
	}

}
