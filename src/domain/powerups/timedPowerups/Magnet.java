package domain.powerups.timedPowerups;

import domain.Paddle;
import domain.powerups.PowerUpEnum;

public class Magnet extends TimedPowerUp {

    public Magnet() {
        super(PowerUpEnum.MAGNET, 30);
    }

    public void activate(){
        super.activate();
        Paddle.getInstance().setSticky(true);
    }

    @Override
    public void deactivate() {
        Paddle.getInstance().setSticky(false);
    }

    public boolean isInUse() {
        return Paddle.getInstance().isSticky();
    }
}
