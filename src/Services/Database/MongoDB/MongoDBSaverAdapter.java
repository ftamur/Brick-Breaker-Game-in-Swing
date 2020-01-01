package Services.Database.MongoDB;

import Services.Database.SaverAdapter;
import com.mongodb.BasicDBObject;
import domain.*;
import domain.alien.*;
import domain.brick.*;
import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;

public class MongoDBSaverAdapter extends SaverAdapter {

    private static MongoDBSaverAdapter mongoDBSaverAdapter;
    private MongoDBSaverAdapter(){}

    public static MongoDBSaverAdapter getInstance(){
        if (mongoDBSaverAdapter == null)
            mongoDBSaverAdapter= new MongoDBSaverAdapter();
        return mongoDBSaverAdapter;
    }




    private BasicDBObject createInitialDocument(DocumentSerializable obj){
        return new BasicDBObject().append("classType",getClassTypeString(obj.getClass()));
    }


    private BasicDBObject physicalObjectToDocument(PhysicalObject obj){
        BasicDBObject doc = createInitialDocument(obj)
                .append("posX", obj.getPosX())
                .append("posY",obj.getPosY())
                .append("velocityX",obj.getVelocityX())
                .append("velocityY",obj.getVelocityY())
                .append("width",obj.getWidth())
                .append("height",obj.getHeight())
                .append("isMoving",obj.isMoving());
        return doc;
    }

    @Override
    protected BasicDBObject paddleToDocument(Paddle paddle) {
        BasicDBObject doc = physicalObjectToDocument(paddle)
                .append("rotateAngle", paddle.getRotateAngle())
                .append("isSticky", paddle.isSticky());
        return doc;
    }

    @Override
    protected BasicDBObject playerToDocument(Player player){
        BasicDBObject doc = createInitialDocument(player)
                .append("username", player.getUserName())
                .append("password", player.getPassword());
        return doc;
    }

    @Override
    protected Object powerUpToDocument(PowerUp powerUp) {
        BasicDBObject doc = createInitialDocument(powerUp).append("powerUpType", PowerUpEnum.toInt(powerUp.getType()));
        return doc;
    }

    @Override
    protected BasicDBObject scoreToDocument(Score score) {
        BasicDBObject doc = createInitialDocument(score).append("currentScore", score.getCurrentScore())
                .append("brokenBrickCount", score.getBrokenBrickCount()).append("totalBrickCount", score.getTotalBrickCount());
        return doc;
    }

    private BasicDBObject _brickToDocument(Brick brick) {
        BasicDBObject doc = physicalObjectToDocument(brick).append("BrickType", BrickType.toInt(brick.getType()));
        return doc;
    }

    @Override
    protected  BasicDBObject simpleBrickToDocument(SimpleBrick brick){
        BasicDBObject doc = _brickToDocument(brick);
        return doc;
    }

    @Override
    protected  BasicDBObject mineBrickToDocument(MineBrick brick){
        BasicDBObject doc = _brickToDocument(brick);
        return doc;
    }

    @Override
    protected  BasicDBObject wrapperBrickToDocument(WrapperBrick brick){
        BasicDBObject doc = _brickToDocument(brick);
        return doc;
    }

    @Override
    protected  BasicDBObject halfMetalBrickToDocument(HalfMetalBrick brick){
        BasicDBObject doc = _brickToDocument(brick);
        return doc;
    }

    @Override
    protected BasicDBObject timeToDocument(GameTime time) {
        BasicDBObject doc = createInitialDocument(time)
                .append("startTime", time.getStartTime())
                .append("currentTimeInSeconds", time.getCurrentTime());
        return doc;
    }

    @Override
    protected BasicDBObject ballToDocument(Ball ball){
        BasicDBObject doc = physicalObjectToDocument(ball)
                .append("isFire",ball.isFire())
                .append("isChemical",ball.isChemical())
                .append("isSticked",ball.isSticked());
        return doc;
    }


    private BasicDBObject _alienToDocument(Alien alien) {
        BasicDBObject doc = physicalObjectToDocument(alien).append("AlienType", AlienType.toInt(alien.getType()));
        return doc;
    }

    @Override
    protected  BasicDBObject cooperativeAlienToDocument(CooperativeAlien alien){
        BasicDBObject doc = _alienToDocument(alien);
        return doc;
    }

    @Override
    protected  BasicDBObject drunkAlienToDocument(DrunkAlien alien){
        BasicDBObject doc = _alienToDocument(alien);
        return doc;
    }

    @Override
    protected  BasicDBObject protectingAlienToDocument(ProtectingAlien alien){
        BasicDBObject doc = _alienToDocument(alien);
        return doc;
    }

    @Override
    protected  BasicDBObject repairingAlienToDocument(RepairingAlien alien){
        BasicDBObject doc = _alienToDocument(alien);
        return doc;
    }


}



