package domain.brick;

import domain.Ball;
import domain.GameState;
import domain.alien.AlienType;
import domain.alien.CooperativeAlien;
import domain.factories.PowerUpFactory;
import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;
import domain.powerups.PowerUpObject;

import java.util.Random;

public class WrapperBrick extends Brick {

    Random random;

    public WrapperBrick(int posX, int posY, double velocityX, double velocityY, int width, int height) {
        super(posX, posY, velocityX, velocityY, width, height);
        this.type = BrickType.WrapperBrick;
        random = new Random();

    }



    private void trigger(Ball ball) {

        int randInt = random.nextInt(10);
        switch (randInt) { 
            case 0: // case for protective alien.
            	GameState.getInstance().addAlien(AlienType.PROTECTING);
                break;

            case 1: // case for cooperative alien.
            	if (!CooperativeAlien.isHit()) GameState.getInstance().addAlien(AlienType.COOPERATIVE);
                break;

            case 2: // case for repairing alien.
            	GameState.getInstance().addAlien(AlienType.REPAIRING);
                break;

            case 3: // case for drunk alien.
            	GameState.getInstance().addAlien(AlienType.DRUNK);
                break;

            case 4:
                PowerUp magnet = PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.MAGNET, ball);
                PowerUpObject powerUpObject = new PowerUpObject(posX, posY, magnet);
                GameState.getInstance().getPowerUpObjectsList().add(powerUpObject);
                break;

            case 5:
                PowerUp laser = PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.DESTRUCTIVE_LASER_GUN, ball);
                PowerUpObject laserObj = new PowerUpObject(posX, posY, laser);
                GameState.getInstance().getPowerUpObjectsList().add(laserObj);
                break;

            case 6:
                PowerUp chemical = PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.CHEMICAL_BALL, ball);
                PowerUpObject chemicalObj = new PowerUpObject(posX, posY, chemical);
                GameState.getInstance().getPowerUpObjectsList().add(chemicalObj);
                break;

            case 7:
                PowerUp expand = PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.TALLER_PADDLE, ball);
                PowerUpObject expandObj = new PowerUpObject(posX, posY, expand);
                GameState.getInstance().getPowerUpObjectsList().add(expandObj);
                break;

            case 8:
                PowerUp fire = PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.FIREBALL, ball);
                fire.activate();
                break;

            case 9:
                if(GameState.getInstance().getBallList().size() >1){
                    return;
                }
                PowerUp gangOfBalls = PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.GANG_OF_BALLS, ball);
                gangOfBalls.activate();
                break;

            default:
                break;
        }
    }


    // TODO: remove testing line which is commented
    @Override
    public void ballHit(Ball ball) {
       super.destroy();
       trigger(ball);
    }



}
