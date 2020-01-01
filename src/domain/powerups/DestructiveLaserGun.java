package domain.powerups;

import domain.Paddle;

public class DestructiveLaserGun extends PowerUp {

    public DestructiveLaserGun(){
        super(PowerUpEnum.DESTRUCTIVE_LASER_GUN);
    }


    public void activate(){
        int shotsLeft = Paddle.getInstance().getDestructiveLaserGunShotsLeft();
        Paddle.getInstance().setDestructiveLaserGunShotsLeft(shotsLeft + 5);
    }

}
