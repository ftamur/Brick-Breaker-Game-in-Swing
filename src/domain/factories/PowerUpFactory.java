package domain.factories;

import domain.Ball;
import domain.powerups.*;
import domain.powerups.timedPowerups.ChemicalBall;
import domain.powerups.timedPowerups.Magnet;
import domain.powerups.timedPowerups.TallerPaddle;

public class PowerUpFactory {
    private static PowerUpFactory singleton;

    private PowerUpFactory(){}


    public PowerUp createPowerUp(PowerUpEnum type, Ball ball) {
        switch (type){
            case MAGNET:
                return new Magnet();
            case FIREBALL:
                return new Fireball(ball);
            case CHEMICAL_BALL:
                return new ChemicalBall();
            case GANG_OF_BALLS:
                return new GangOfBalls(ball);
            case TALLER_PADDLE:
                return new TallerPaddle();
            case DESTRUCTIVE_LASER_GUN:
                return new DestructiveLaserGun();
        }
        return null;
    }


    public static PowerUpFactory getInstance(){
        if (singleton == null){
            singleton = new PowerUpFactory();
        }
        return singleton;
    }

}
