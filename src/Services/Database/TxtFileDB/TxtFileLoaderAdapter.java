package Services.Database.TxtFileDB;

import Services.Database.LoaderAdapter;
import domain.*;
import domain.alien.Alien;
import domain.alien.AlienType;
import domain.brick.Brick;
import domain.brick.BrickType;
import domain.factories.AlienFactory;
import domain.factories.BrickFactory;
import domain.factories.PowerUpFactory;
import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;

public class TxtFileLoaderAdapter extends LoaderAdapter {
    private static TxtFileLoaderAdapter txtFileLoaderAdapter;

    public static TxtFileLoaderAdapter getInstance() {
        if (txtFileLoaderAdapter == null)
            txtFileLoaderAdapter = new TxtFileLoaderAdapter();
        return txtFileLoaderAdapter;
    }

    private TxtFileLoaderAdapter() {}

    public DocumentSerializable createInstance(Object serializedObjectString) {
        String[] words = ((String) serializedObjectString).split(" ");
        int i = 0;
        Class<DocumentSerializable> classType = getClassTypeFromString(words[i++]);
        if (PhysicalObject.class.isAssignableFrom(classType)) {
            int posX = Integer.parseInt(words[i++]);
            int posY = Integer.parseInt(words[i++]);
            double velocityX = Double.parseDouble(words[i++]);
            double velocityY =  Double.parseDouble(words[i++]);
            int width = Integer.parseInt(words[i++]);
            int height = Integer.parseInt(words[i++]);

            if (Brick.class.isAssignableFrom(classType)) {
                BrickType brickType = BrickType.fromInt(Integer.parseInt(words[i++]));
                return BrickFactory.getInstance().createBrick(brickType, posX, posY, velocityX, velocityY, width, height);
            }

            if (Alien.class.isAssignableFrom(classType)) {
                AlienType alienType = AlienType.fromInt(Integer.parseInt(words[i++]));
                return AlienFactory.getInstance().createAlien(alienType, posX, posY);
            }

            if (Ball.class.isAssignableFrom(classType)) {
                boolean isSticked = Boolean.parseBoolean(words[i++]);
                if (isSticked)
                    numStickedBalls++;
                else
                    return new Ball(posX, posY, velocityX, velocityY, width, height, false,isSticked);
            }

            if (Paddle.class.isAssignableFrom(classType)) {
                Double rotateAngle = Double.parseDouble(words[i++]);
                Boolean isSticky = Boolean.parseBoolean(words[i++]);
                Paddle.getInstance().setProperties(posX,posY,rotateAngle,velocityX,velocityY,width,height,isSticky);
            }
        }

        if (GameTime.class.isAssignableFrom(classType)) {
            int startTime = Integer.parseInt(words[i++]);
            int currentTimeInSeconds = Integer.parseInt(words[i++]);
            startTime += GameTime.getInstance().getCurrentTime() - currentTimeInSeconds;
            GameTime.getInstance().setStartTime(startTime);
        }


        if (Score.class.isAssignableFrom(classType)) {
            int currentScore = Integer.parseInt(words[i++]);
            int brokenBrickCount = Integer.parseInt(words[i++]);
            int totalBrickCount = Integer.parseInt(words[i++]);
            Score.getInstance().setScore(currentScore);
            Score.getInstance().setBrokenBrickCount(brokenBrickCount);
            Score.getInstance().setTotalBrickCount(totalBrickCount);
        }

        if (PowerUp.class.isAssignableFrom(classType)) {
            int type = Integer.parseInt(words[i++]);
            return PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.fromInt(type),null);
        }

        return null;
    }





}
