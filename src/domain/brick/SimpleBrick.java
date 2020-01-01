package domain.brick;


import java.util.Random;

public class SimpleBrick extends Brick{


    public SimpleBrick(int posX, int posY, double velocityX, double velocityY, int width, int height) {
        super(posX, posY, velocityX, velocityY,width, height);
		this.type=BrickType.SimpleBrick;
        boolean isMoving;
        int random=new Random().nextInt(10);

        if(random==0)
            isMoving=true;
        else
            isMoving=false;

        super.setMoving(isMoving);

        if(isMoving)
            setVelocityX(1);
	}

}
