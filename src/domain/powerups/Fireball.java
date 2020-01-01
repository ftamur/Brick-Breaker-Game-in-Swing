package domain.powerups;

import domain.Ball;

public class Fireball extends PowerUp{

    private Ball targetBall;

    public Fireball(Ball targetBall){
        super(PowerUpEnum.FIREBALL);
        this.targetBall = targetBall;
    }



    @Override
    public void activate() {
        targetBall.setFire(true);
    }
}
