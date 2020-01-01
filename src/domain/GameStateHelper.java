package domain;

import domain.brick.Brick;
import domain.brick.BrickType;
import domain.brick.HalfMetalBrick;
import domain.brick.MineBrick;

import java.util.ArrayList;

import Services.Constants;

public class GameStateHelper {
    private static GameStateHelper instance;
    private ArrayList<Ball> balls;
    private ArrayList<Brick> brickList;
    private ArrayList<MineBrick> movingMineBricks;

    private GameStateHelper() {
    	
    }

    public static GameStateHelper getInstance() {
        if (instance == null) instance = new GameStateHelper();
        return instance;
    }
 
    public void handleCollisions() {
        handleBallPaddleCollisions();
        handleBallsBrickCollisions();
        handleBallsMovingMineBrickCollisions();
        handleBrickBrickCollisions();
        handleBrickMineBrickCollisions();
    }

    private void handleBallPaddleCollisions() {
        balls = GameState.getInstance().getBallList();
        balls.forEach(ball -> CollisionHandler.getInstance().intersects(ball, Paddle.getInstance()));
    }

    private void handleBallsBrickCollisions() {
        balls = GameState.getInstance().getBallList();
        brickList = GameState.getInstance().getBrickList();
        balls.forEach(ball -> handleBallBrickCollisions(ball, brickList));
    }

    private void handleBallsMovingMineBrickCollisions() {
        balls = GameState.getInstance().getBallList();
        movingMineBricks = GameState.getInstance().getMovingMineBricks();
        balls.forEach(ball -> handleBallMovingMineBrickCollisions(ball, movingMineBricks));
    }

    private void handleBrickBrickCollisions() {
        brickList = GameState.getInstance().getBrickList();
        for (Brick b : brickList) {
            if (b != null && b.isMoving) {
                int Y = b.getPosY() / Constants.BRICK_HEIGHT - 1;
                int startIndex = Y * Constants.BUILDMODE_GRID_COLS;
                while (startIndex < Y * Constants.BUILDMODE_GRID_COLS + Constants.BUILDMODE_GRID_COLS) {
                    Brick brick = brickList.get(startIndex);
                    if (brick != null) {
                        CollisionHandler.getInstance().intersects(b, brick);
                    }
                    startIndex++;
                }
            }
        }
    }


    private void handleBrickMineBrickCollisions() {
        movingMineBricks = GameState.getInstance().getMovingMineBricks();
        brickList = GameState.getInstance().getBrickList();
        movingMineBricks.forEach(mineBrick -> handleBrickMineBrick(mineBrick, brickList));
    }

    private void handleBrickMineBrick(MineBrick mineBrick, ArrayList<Brick> brickList) {
        if (mineBrick != null) {
            if (brickList.isEmpty() != true) {
                for (Brick b : brickList) {
                    if (b != null) {
                        CollisionHandler.getInstance().mineBrickBrickCollisions(mineBrick, b);
                    }
                }
            }
        }
    }

    private void handleBallBrickCollisions(Ball ball, ArrayList<Brick> brickList) {
        if (brickList.isEmpty() != true) {
            for (Brick b : brickList) {
                if (b != null) {
                    CollisionHandler.getInstance().intersects(ball, b);
                    if (CollisionHandler.getInstance().removedBrick != null) {
                        break;
                    }
                }
            }
        }
        if (CollisionHandler.getInstance().removedBrick != null) {
            int index = brickList.indexOf(CollisionHandler.getInstance().removedBrick);
            CollisionHandler.getInstance().removedBrick.ballHit(ball);
            if (CollisionHandler.getInstance().removedBrick.getType() == BrickType.MineBrick)
                bricksAroundtheMineBrickWithRadius2L((MineBrick) CollisionHandler.getInstance().removedBrick, brickList, movingMineBricks);
            if (CollisionHandler.getInstance().removedBrick.getType() != BrickType.MineBrick && ball.isFire())
                destroyNeighborBricks(CollisionHandler.getInstance().removedBrick);
            if(index>0)
                brickList.set(index, null);
        }

    }

    private void handleBallMovingMineBrickCollisions(Ball ball, ArrayList<MineBrick> movingMineBricks) {
        brickList = GameState.getInstance().getBrickList();
        if (movingMineBricks.isEmpty() != true) {
            for (Brick b : movingMineBricks) {
                if (b != null) {
                    CollisionHandler.getInstance().intersects(ball, b);
                    if (CollisionHandler.getInstance().removedBrick != null) {
                        break;
                    }
                }
            }
        }
        if (CollisionHandler.getInstance().removedBrick != null) {
            
            CollisionHandler.getInstance().removedBrick.destroy();
            
            if (CollisionHandler.getInstance().removedBrick.getType() == BrickType.MineBrick)
                bricksAroundtheMineBrickWithRadius2L((MineBrick) CollisionHandler.getInstance().removedBrick, brickList, movingMineBricks);
            
            movingMineBricks.remove(CollisionHandler.getInstance().removedBrick);
        }
        CollisionHandler.getInstance().removedBrick = null;
    }


    private void bricksAroundtheMineBrickWithRadius2L(MineBrick brick, ArrayList<Brick> brickList, ArrayList<MineBrick> mineBricks) {
        int posX = brick.getPosX() + brick.getWidth() / 2;
        int posY = brick.getPosY() + brick.getHeight() / 2;
        for (Brick b : brickList) {
            if (b != null && b != brick)
                willExplode(posX, posY, b, true);
        }

        for (MineBrick b : mineBricks) {
            if (b != null && b != brick)
                willExplode(posX, posY, b, false);
        }
    }

    private void willExplode(int posX, int posY, Brick b, boolean isBrickList) {
        brickList = GameState.getInstance().getBrickList();
        movingMineBricks = GameState.getInstance().getMovingMineBricks();
        int index;
        if (b != null) {
            int bCenterX = b.getPosX() + b.getWidth() / 2;
            int bCenterY = b.getPosY() + b.getHeight() / 2;
            double euclideanDistance = Math.sqrt((posX - bCenterX) * (posX - bCenterX) + (posY - bCenterY) * (posY - bCenterY));
            if (euclideanDistance <= Constants.L * 2) {
                if (isBrickList) {
                    index = brickList.indexOf(b);
                    brickList.set(index, null);
                    b.destroy();
                } else {
                    index = movingMineBricks.indexOf(b);
                    movingMineBricks.set(index, null);
                    b.destroy();
                }
            }
        }
    }

    private void destroyNeighborBricks(Brick b) {
        int Y = b.getPosY() / Constants.BRICK_HEIGHT - 1;
        int B;
        if((Y -1)>0){
            B=Y-1;
        }
        else{
            B=Y;
        }
        int startIndex = B* Constants.BUILDMODE_GRID_COLS;
        while (startIndex < Y * Constants.BUILDMODE_GRID_COLS + Constants.BUILDMODE_GRID_COLS+Constants.BUILDMODE_GRID_COLS&&startIndex<brickList.size()) {
            Brick brick = brickList.get(startIndex);
            if (brick != null) {
                if (Math.abs(brick.getPosX() - b.getPosX()) < 1.5 * b.getWidth()) {
                    if (brick.getType() == BrickType.HalfMetalBrick) {
                        HalfMetalBrick brickPtr = (HalfMetalBrick) brick;
                        if (!brickPtr.isFireballHit()) {
                            brickPtr.setFireballHit(true);

                        }
                        else {
                            brick.destroy();
                            brickList.set(startIndex, null);
                        }
                    }
                    else{
                        brick.destroy();
                        brickList.set(startIndex, null);
                    }
                }
            }
            startIndex++;
        }
    }
}
