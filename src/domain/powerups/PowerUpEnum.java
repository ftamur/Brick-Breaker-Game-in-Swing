package domain.powerups;

import domain.powerups.timedPowerups.ChemicalBall;
import domain.powerups.timedPowerups.Magnet;
import domain.powerups.timedPowerups.TallerPaddle;


public enum PowerUpEnum {
    DESTRUCTIVE_LASER_GUN,
    MAGNET,
    GANG_OF_BALLS,
    FIREBALL,
    CHEMICAL_BALL,
    TALLER_PADDLE;


	public static <T extends  PowerUp> Class<T> getType(PowerUpEnum type){
        switch (type){
            case DESTRUCTIVE_LASER_GUN:
                return (Class <T>) DestructiveLaserGun.class;
            case MAGNET:
                return (Class <T>) Magnet.class;
            case GANG_OF_BALLS:
                return (Class <T>) GangOfBalls.class;
            case FIREBALL:
                return (Class <T>) Fireball.class;
            case CHEMICAL_BALL:
                return (Class <T>) ChemicalBall.class;
            case TALLER_PADDLE:
                return (Class <T>) TallerPaddle.class;
        }
        return null;
    }

    public static PowerUpEnum[] getKeyTriggeredPowerUps() {
        PowerUpEnum[] list = {PowerUpEnum.MAGNET, PowerUpEnum.CHEMICAL_BALL, PowerUpEnum.TALLER_PADDLE, PowerUpEnum.DESTRUCTIVE_LASER_GUN};
        return list;
    }


    public static PowerUpEnum fromInt(int x) {
        switch (x) {
            case 0:
                return DESTRUCTIVE_LASER_GUN;
            case 1:
                return MAGNET;
            case 2:
                return GANG_OF_BALLS;
            case 3:
                return FIREBALL;
            case 4:
                return CHEMICAL_BALL;
            case 5:
                return TALLER_PADDLE;
            default:
                return null;
        }
    }


    public static int toInt(PowerUpEnum type) {
        switch (type) {
            case DESTRUCTIVE_LASER_GUN:
                return 0;
            case MAGNET:
                return 1;
            case GANG_OF_BALLS:
                return 2;
            case FIREBALL:
                return 3;
            case CHEMICAL_BALL:
                return 4;
            case TALLER_PADDLE:
                return 5;
            default:
                return -1;
        }
    }





}
