package domain.brick;

import domain.Animations.BrickExplosion;
import domain.*;

import java.util.ArrayList;

import Services.Constants;


public abstract class Brick extends PhysicalObject {
    protected BrickType type;

    protected ArrayList<IObserver> observers = new ArrayList<>();

    public Brick(int posX, int posY, double velocityX, double velocityY, int width, int height) {
        super(posX, posY, velocityX, velocityY, width, height, false,PhysicalObjectType.BRICK);
        observers.add(Score.getInstance());
        // TODO Auto-generated constructor stub
    }

    public void destroy() {
        observers.forEach(observer -> observer.update());
        GameState.getInstance().getBrickExplosionList().add(new BrickExplosion(this.posX,this.posY,this.getWidth(),this.getHeight()));
    }

    public void ballHit(Ball ball) {
        destroy();
    }
  

    public BrickType getType() {
        return type;
    }
    @Override
    public void move() {
            super.move();
            if (this.posX <0){
                resetDecimals();
                velocityX = Math.abs(velocityX);
            }
            if( posX + this.getWidth() > Constants.SCREEN_WIDTH){
                resetDecimals();
                velocityX = -Math.abs(velocityX);
            }
    }





}
