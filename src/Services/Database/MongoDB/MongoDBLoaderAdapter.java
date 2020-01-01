package Services.Database.MongoDB;

import Services.Database.LoaderAdapter;
import com.mongodb.DBObject;
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

public class MongoDBLoaderAdapter extends LoaderAdapter {
    private static MongoDBLoaderAdapter mongoDBLoaderAdapter;

    public static MongoDBLoaderAdapter getInstance() {
        if (mongoDBLoaderAdapter == null)
            mongoDBLoaderAdapter = new MongoDBLoaderAdapter();
        return mongoDBLoaderAdapter;
    }

    private MongoDBLoaderAdapter() {}


    public DocumentSerializable createInstance(Object dbDocument) {
        DBObject document = (DBObject) dbDocument;
        Class<DocumentSerializable> classType = getClassTypeFromString(document.get("classType").toString());
        if (PhysicalObject.class.isAssignableFrom(classType)) {
            int posX = (int) document.get("posX");
            int posY = (int) document.get("posY");
            double velocityX = (double) document.get("velocityX");
            double velocityY = (double) document.get("velocityY");
            int width = (int) document.get("width");
            int height = (int) document.get("height");

            if (Brick.class.isAssignableFrom(classType)) {
                BrickType brickType = BrickType.fromInt((int) document.get("BrickType"));
                return BrickFactory.getInstance().createBrick(brickType, posX, posY, velocityX, velocityY, width, height);
            }

            if (Alien.class.isAssignableFrom(classType)) {
                AlienType alienType = AlienType.fromInt((int) document.get("AlienType"));
                return AlienFactory.getInstance().createAlien(alienType, posX, posY);
            }

            if (Ball.class.isAssignableFrom(classType)) {
                boolean isSticked = (boolean) document.get("isSticked");
                if (isSticked){
                    this.numStickedBalls++;
                }else{
                    return new Ball(posX, posY, velocityX, velocityY, width, height, false,isSticked);
                }
            }

            if (Paddle.class.isAssignableFrom(classType)) {
                Double rotateAngle = (double) document.get("rotateAngle");
                boolean isSticky = (boolean) document.get("isSticky");
                Paddle.getInstance().setProperties(posX,posY,rotateAngle,velocityX,velocityY,width,height,isSticky);
            }
        }

        if (GameTime.class.isAssignableFrom(classType)) {
            int startTime = (int) document.get("startTime");
            int currentTimeInSeconds = (int) document.get("currentTimeInSeconds");
            startTime += GameTime.getInstance().getCurrentTime() - currentTimeInSeconds;
            GameTime.getInstance().setStartTime(startTime);
        }


        if (Score.class.isAssignableFrom(classType)) {
            int currentScore = (int) document.get("currentScore");
            int brokenBrickCount = (int) document.get("brokenBrickCount");
            int totalBrickCount = (int) document.get("totalBrickCount");
            Score.getInstance().setScore(currentScore);
            Score.getInstance().setBrokenBrickCount(brokenBrickCount);
            Score.getInstance().setTotalBrickCount(totalBrickCount);
        }

        if (PowerUp.class.isAssignableFrom(classType)) {
            int type = (int) document.get("powerUpType");
            return PowerUpFactory.getInstance().createPowerUp(PowerUpEnum.fromInt(type),null);
        }

        return null;
    }






}
