package domain.brick;

import java.util.Random;

public class HalfMetalBrick extends Brick {

    private boolean isFireballHit;
    public HalfMetalBrick(int posX, int posY, double velocityX, double velocityY, int width, int height) {
        super(posX, posY, velocityX, velocityY, width, height);
		this.type=BrickType.HalfMetalBrick;
        boolean isMoving;
        this.isFireballHit=false;
        int random=new Random().nextInt(10);
        if(random==0) isMoving=true;
        if(random==0) isMoving=true;
        else isMoving=false;
        isMoving = random == 0;
        super.setMoving(isMoving);
        if(isMoving) setVelocityX(1);

	}
    public boolean isFireballHit() {
        return isFireballHit;
    }
    public void setFireballHit(boolean fireballHit) {
        isFireballHit = fireballHit;
    }

 



}
