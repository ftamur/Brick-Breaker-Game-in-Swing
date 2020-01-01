package Services.Database;

import domain.*;
import domain.alien.CooperativeAlien;
import domain.alien.DrunkAlien;
import domain.alien.ProtectingAlien;
import domain.alien.RepairingAlien;
import domain.brick.HalfMetalBrick;
import domain.brick.MineBrick;
import domain.brick.SimpleBrick;
import domain.brick.WrapperBrick;
import domain.powerups.PowerUp;

public abstract class LoaderAdapter {

    public abstract DocumentSerializable createInstance(Object dbDocument);
    protected int numStickedBalls = 0;

    @SuppressWarnings("unchecked")
	protected  <T extends DocumentSerializable> Class<T> getClassTypeFromString(String classTypeString){

        switch (classTypeString) {
            case "Paddle":
                return ((Class<T>) Paddle.class);

            case "Score":
                return ((Class<T>) Score.class);

            case "Player":
                return ((Class<T>) Player.class);

            case "GameTime":
                return ((Class<T>) GameTime.class);

            case "Ball":
                return ((Class<T>) Ball.class);

            case "SimpleBrick":
                return ((Class<T>) SimpleBrick.class);

            case "HalfMetalBrick":
                return ((Class<T>) HalfMetalBrick.class);

            case "MineBrick":
                return ((Class<T>) MineBrick.class);

            case "WrapperBrick":
                return ((Class<T>) WrapperBrick.class);

            case "ProtectingAlien":
                return ((Class<T>) ProtectingAlien.class);

            case "CooperativeAlien":
                return ((Class<T>) CooperativeAlien.class);

            case "DrunkAlien":
                return ((Class<T>) DrunkAlien.class);

            case "RepairingAlien":
                return ((Class<T>) RepairingAlien.class);
            case "PowerUp":
                return ((Class<T>) PowerUp.class);
            default:
                return null;
        }
    }

    public void createStickedBalls() {
        for (int i = 0; i < numStickedBalls; i++){
            GameState.getInstance().getBallList().add(new Ball
                    (Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth()));
        }
        numStickedBalls = 0;
    }
}
