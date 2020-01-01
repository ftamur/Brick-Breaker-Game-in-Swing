package domain.powerups.timedPowerups;

import Services.Constants;
import domain.Paddle;
import domain.powerups.PowerUpEnum;

public class TallerPaddle extends  TimedPowerUp{

    public TallerPaddle(){
        super(PowerUpEnum.TALLER_PADDLE, 30);
    }

    @Override
    public void activate() {
        super.activate();
        int paddleWidth = Paddle.getInstance().getWidth();
        int centerX = Paddle.getInstance().getPosX() + paddleWidth / 2;
        Paddle.getInstance().setWidth(paddleWidth*2);
        Paddle.getInstance().setPosX(centerX-paddleWidth);
    }


    @Override
    public void deactivate() {
        int paddleWidth = Paddle.getInstance().getWidth();
        int centerX = Paddle.getInstance().getPosX() + paddleWidth /2;
        Paddle.getInstance().setWidth(paddleWidth/2);
        Paddle.getInstance().setPosX(centerX-paddleWidth/4);
    }

    public boolean isInUse() {
        return Paddle.getInstance().getWidth() != Constants.L;
    }
}
