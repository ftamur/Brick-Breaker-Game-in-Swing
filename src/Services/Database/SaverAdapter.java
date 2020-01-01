package Services.Database;

import domain.*;
import domain.alien.*;
import domain.brick.*;
import domain.powerups.PowerUp;


public abstract class SaverAdapter {

    protected  <T extends DocumentSerializable> String getClassTypeString(Class<T> classType){

        if(classType.equals(Paddle.class))
            return "Paddle";

        if(classType.equals(Score.class))
            return  "Score";

        if(classType.equals(Player.class))
            return "Player";

        if(classType.equals(GameTime.class))
            return "GameTime";

        if(classType.equals(Ball.class))
            return "Ball";

        if(classType.equals(SimpleBrick.class))
            return "SimpleBrick";

        if(classType.equals(HalfMetalBrick.class))
            return "HalfMetalBrick";

        if(classType.equals(MineBrick.class))
            return "MineBrick";

        if(classType.equals(WrapperBrick.class))
            return "WrapperBrick";

        if(classType.equals(ProtectingAlien.class))
            return "ProtectingAlien";

        if(classType.equals(CooperativeAlien.class))
            return "CooperativeAlien";

        if(classType.equals(DrunkAlien.class))
            return "DrunkAlien";

        if(classType.equals(RepairingAlien.class))
            return "RepairingAlien";

        if(PowerUp.class.isAssignableFrom(classType))
            return "PowerUp";

        return null;
    }


    protected abstract Object simpleBrickToDocument(SimpleBrick brick);
    protected abstract Object mineBrickToDocument(MineBrick brick);
    protected abstract Object wrapperBrickToDocument(WrapperBrick brick);
    protected abstract Object halfMetalBrickToDocument(HalfMetalBrick brick);
    protected abstract Object cooperativeAlienToDocument(CooperativeAlien alien);
    protected abstract Object drunkAlienToDocument(DrunkAlien alien);
    protected abstract Object protectingAlienToDocument(ProtectingAlien alien);
    protected abstract Object repairingAlienToDocument(RepairingAlien alien);

    protected Object brickToDocument(Brick brick) {
        switch (brick.getType()){
            case SimpleBrick:
                return simpleBrickToDocument((SimpleBrick) brick);
            case MineBrick:
                return mineBrickToDocument((MineBrick) brick);
            case WrapperBrick:
                return wrapperBrickToDocument((WrapperBrick) brick);
            case HalfMetalBrick:
                return halfMetalBrickToDocument((HalfMetalBrick) brick);
        }
        return null;
    }

    protected Object alienToDocument(Alien alien) {
        switch (alien.getType()){
            case COOPERATIVE:
                return cooperativeAlienToDocument((CooperativeAlien) alien);
            case PROTECTING:
                return protectingAlienToDocument((ProtectingAlien) alien);
            case DRUNK:
                return drunkAlienToDocument((DrunkAlien) alien);
            case REPAIRING:
                return repairingAlienToDocument((RepairingAlien) alien);
        }
        return null;
    }


    protected abstract Object ballToDocument(Ball ball);
    protected abstract Object paddleToDocument(Paddle paddle);
    protected abstract Object scoreToDocument(Score score);
    protected abstract Object timeToDocument(GameTime gameTime);
    protected abstract Object playerToDocument(Player player);
    protected abstract Object powerUpToDocument(PowerUp powerUp);


    public Object retrieveSaveEnconding(DocumentSerializable T){
        if(T.getClass().equals(Ball.class)){
            return ballToDocument((Ball) T);
        }

        if(Brick.class.isAssignableFrom(T.getClass())){
            return brickToDocument((Brick) T);
        }

        if(T.getClass().equals(Paddle.class)){
            return paddleToDocument((Paddle) T);
        }

        if(T.getClass().equals(Score.class)){
            return scoreToDocument((Score) T);
        }

        if(T.getClass().equals(GameTime.class)){
            return timeToDocument((GameTime) T);
        }

        if(T.getClass().equals(Player.class)){
            return playerToDocument((Player) T);
        }

        if(Alien.class.isAssignableFrom(T.getClass())){
            return alienToDocument((Alien) T);
        }

        if(PowerUp.class.isAssignableFrom(T.getClass())){
            return powerUpToDocument((PowerUp) T);
        }


        return null;
    }
}
