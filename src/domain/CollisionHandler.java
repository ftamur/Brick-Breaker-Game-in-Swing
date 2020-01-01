package domain;

import domain.alien.Alien;
import domain.alien.AlienType;
import domain.alien.CooperativeAlien;
import domain.alien.DrunkAlien;
import domain.brick.Brick;
import domain.brick.BrickType;
import domain.brick.HalfMetalBrick;
import domain.brick.MineBrick;

public class CollisionHandler {

    private static CollisionHandler collisionHandler;
    public Brick removedBrick;
    private Alien removedAlien;
    private boolean isCollide;

    private CollisionHandler() {
        removedBrick = null;
        removedAlien = null;
        isCollide = false;
    }

    public boolean collides(PhysicalObject obj1, PhysicalObject obj2) {
        if (obj1.getPosX() > obj2.getPosX() + obj2.getWidth() || obj2.getPosX() > obj1.getPosX() + obj1.getWidth()) {
            return false;
        } 

        return obj1.getPosY() <= obj2.getPosY() + obj2.getHeight() && obj2.getPosY() <= obj1.getPosY() + obj1.getHeight();
    }


    public static CollisionHandler getInstance() {
        if (collisionHandler == null)
            collisionHandler = new CollisionHandler();
        return collisionHandler;
    }


    public void intersects(PhysicalObject obj1, PhysicalObject obj2) {

        PhysicalObjectType type1 = obj1.getObjectType();
        PhysicalObjectType type2 = obj2.getObjectType();

        if (type1.equals(PhysicalObjectType.BALL) && type2.equals(PhysicalObjectType.PADDLE)) {
            ballPaddleCollision((Ball) obj1);
        } else if (type1.equals(PhysicalObjectType.BALL) && type2.equals(PhysicalObjectType.BRICK)) {
            ballBrickCollision((Ball) obj1, (Brick) obj2);
        } else if (type1.equals(PhysicalObjectType.BRICK) && type2.equals(PhysicalObjectType.BRICK)) {
            brickBrickCollision((Brick) obj1, (Brick) obj2);

        } else if (type1.equals(PhysicalObjectType.BALL) && type2.equals(PhysicalObjectType.ALIEN)) {
            ballAlienCollision((Ball) obj1, (Alien) obj2);
        }

    }

    private void brickBrickCollision(Brick obj1, Brick obj2) {
        if ((obj1.getPosX() + obj1.getWidth() > obj2.getPosX() && obj1.getPosX() < obj2.getPosX())
                || (obj1.getPosX() < obj2.getPosX() + obj2.getWidth() && obj1.getPosX() > obj2.getPosX())
                && obj1.getPosY() == obj2.getPosY()) {

            obj1.setVelocityX(-obj1.getVelocityX());
        }

    }

    private void ballBrickCollision(Ball obj1, Brick obj2) {
        if (obj2.getType().equals(BrickType.MineBrick)) {
            squareBrickCollision(obj1, obj2);
        } else {
            squareBrickCollision(obj1, obj2);
        }
    }
    
 
    private void ballAlienCollision(Ball ball, Alien alien) {
        if (!(alien.getPosX() > ball.getPosX() + ball.getWidth() || alien.getPosX() + alien.width < ball.getPosX()
                || alien.getPosY() > ball.getPosY() + ball.getHeight() || alien.getPosY() + alien.getHeight() < ball.getPosY())) {
            double dx = alien.posX + alien.getWidth() / 2 - (ball.getPosX() + ball.getWidth() / 2);
            double dy = alien.posY + alien.getHeight() / 2 - (ball.posY + ball.getHeight() / 2);
            double theta = Math.atan2(dy, dx);
            double diagTheta = Math.atan2(alien.getHeight(), alien.getWidth());
            if (-diagTheta <= theta && theta <= diagTheta) {
                if (ball.getPosY() + ball.getHeight() / 2 < alien.getPosY()) {
                    ballBounceBrick(ball, Direction.UPPER_LEFT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.UPPER_LEFT_EDGE);
                } else if (ball.getPosY() + ball.getHeight() / 2 > alien.getPosY() + alien.getHeight()) {
                    ballBounceBrick(ball, Direction.LOWER_LEFT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.LOWER_LEFT_EDGE);
                } else {
                    ballBounceBrick(ball, Direction.LEFT,alien.getVelocityX());
                    removeAlien(alien, Direction.LEFT);
                }
            } else if (diagTheta <= theta && theta <= Math.PI - diagTheta) {
                if (alien.getPosX() >= ball.getPosX() + ball.getWidth() / 2) {
                    ballBounceBrick(ball, Direction.UPPER_LEFT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.UPPER_LEFT_EDGE);
                } else if (ball.getPosX() + ball.getWidth() / 2 >= alien.getPosX() + alien.getWidth()) {
                    ballBounceBrick(ball, Direction.UPPER_RIGHT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.UPPER_RIGHT_EDGE);
                } else {
                    ballBounceBrick(ball, Direction.UP,alien.getVelocityX());
                    removeAlien(alien, Direction.UP);
                }
            } else if (Math.PI - diagTheta <= theta || theta <= diagTheta - Math.PI) {
                if (ball.getPosY() + ball.getHeight() / 2 < alien.getPosY()) {
                    ballBounceBrick(ball, Direction.UPPER_RIGHT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.UPPER_RIGHT_EDGE);
                } else if (ball.getPosY() + ball.getHeight() / 2 > alien.getPosY() + alien.getHeight()) {
                    ballBounceBrick(ball, Direction.LOWER_RIGHT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.LOWER_RIGHT_EDGE);
                } else {
                    ballBounceBrick(ball, Direction.RIGHT,alien.getVelocityX());
                    removeAlien(alien, Direction.RIGHT);
                }
            } else if (diagTheta - Math.PI <= theta && theta <= diagTheta) {

                if (alien.getPosX() > ball.getPosX() + ball.getWidth() / 2) {
                    ballBounceBrick(ball, Direction.LOWER_LEFT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.LOWER_LEFT_EDGE);
                } else if (ball.getPosX() + ball.getWidth() / 2 > alien.getPosX() + alien.getWidth()) {
                    ballBounceBrick(ball, Direction.LOWER_RIGHT_EDGE,alien.getVelocityX());
                    removeAlien(alien, Direction.LOWER_RIGHT_EDGE);
                } else {
                    ballBounceBrick(ball, Direction.DOWN,alien.getVelocityX());
                    removeAlien(alien, Direction.DOWN);
                }
            }
        }
    }

    private void squareBrickCollision(Ball obj1, Brick obj2) {
        if (!(obj2.getPosX() > obj1.getPosX() + obj1.getWidth() || obj2.getPosX() + obj2.width < obj1.getPosX()
                || obj2.getPosY() > obj1.getPosY() + obj1.getHeight() || obj2.getPosY() + obj2.getHeight() < obj1.getPosY())) {
            double dx = obj2.posX + obj2.getWidth() / 2 - (obj1.getPosX() + obj1.getWidth() / 2);
            double dy = obj2.posY + obj2.getHeight() / 2 - (obj1.posY + obj1.getHeight() / 2);
            double theta = Math.atan2(dy, dx);
            double diagTheta = Math.atan2(obj2.getHeight(), obj2.getWidth());
            if (-diagTheta <= theta && theta <= diagTheta) {
                if (obj1.getPosY() + obj1.getHeight() / 2 < obj2.getPosY()) {
                    ballBounceBrick(obj1, Direction.UPPER_LEFT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.UPPER_LEFT_EDGE,obj1.isFire());
                } else if (obj1.getPosY() + obj1.getHeight() / 2 > obj2.getPosY() + obj2.getHeight()) {
                    ballBounceBrick(obj1, Direction.LOWER_LEFT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.LOWER_LEFT_EDGE,obj1.isFire());
                } else {
                    ballBounceBrick(obj1, Direction.LEFT,obj2.velocityX);
                    removeTheBrick(obj2, Direction.LEFT,obj1.isFire());
                }
            } else if (diagTheta <= theta && theta <= Math.PI - diagTheta) {
                if (obj2.getPosX() >= obj1.getPosX() + obj1.getWidth()) {
                    ballBounceBrick(obj1, Direction.UPPER_LEFT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.UPPER_LEFT_EDGE,obj1.isFire());
                } else if (obj1.getPosX() + obj1.getWidth() / 2 >= obj2.getPosX() + obj2.getWidth()) {
                    ballBounceBrick(obj1, Direction.UPPER_RIGHT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.UPPER_RIGHT_EDGE,obj1.isFire());
                } else {
                    ballBounceBrick(obj1, Direction.UP,obj2.velocityX);
                    removeTheBrick(obj2, Direction.UP,obj1.isFire());
                }
            } else if (Math.PI - diagTheta <= theta || theta <= diagTheta - Math.PI) {
                if (obj1.getPosY() + obj1.getHeight() / 2 < obj2.getPosY()) {
                    ballBounceBrick(obj1, Direction.UPPER_RIGHT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.UPPER_RIGHT_EDGE,obj1.isFire());
                } else if (obj1.getPosY() + obj1.getHeight() / 2 > obj2.getPosY() + obj2.getHeight()) {
                    ballBounceBrick(obj1, Direction.LOWER_RIGHT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.LOWER_RIGHT_EDGE,obj1.isFire());
                } else {
                    ballBounceBrick(obj1, Direction.RIGHT,obj2.velocityX);
                    removeTheBrick(obj2, Direction.RIGHT,obj1.isFire());
                }
            } else if (diagTheta - Math.PI <= theta && theta <= diagTheta) {
                if (obj2.getPosX() > obj1.getPosX() + obj1.getWidth() / 2) {
                    ballBounceBrick(obj1, Direction.LOWER_LEFT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.LOWER_LEFT_EDGE,obj1.isFire());
                } else if (obj1.getPosX() + obj1.getWidth() / 2 > obj2.getPosX() + obj2.getWidth()) {
                    ballBounceBrick(obj1, Direction.LOWER_RIGHT_EDGE,obj2.velocityX);
                    removeTheBrick(obj2, Direction.LOWER_RIGHT_EDGE,obj1.isFire());
                } else {
                    ballBounceBrick(obj1, Direction.DOWN,obj2.velocityX);
                    removeTheBrick(obj2, Direction.DOWN,obj1.isFire());
                }
            }
        }
    }


    private void ballBounceBrick(Ball obj, Direction dir,double velocityXBrick) {
        double temp;
        double velocitySign=0;
        if(velocityXBrick!=0)
             velocitySign=velocityXBrick/Math.abs(velocityXBrick);
        if (!obj.isChemical()) {
            switch (dir) {
                case UP:
                    obj.setVelocityY(-Math.abs(obj.getVelocityY()));
                   // System.out.println(obj.getVelocityX());
                    obj.setVelocityX(obj.getVelocityX()+velocitySign*0.3);
                   // System.out.println(obj.getVelocityX());
                    break;
                case DOWN:
                    obj.setVelocityY(Math.abs(obj.getVelocityY()));
                    //System.out.println(obj.getVelocityX());
                    obj.setVelocityX(obj.getVelocityX()+velocitySign*0.3);
                    //System.out.println(obj.getVelocityX());
                    break;
                case LEFT:
                    obj.setVelocityX(-Math.abs(obj.getVelocityX()));
                    break;
                case RIGHT:
                    obj.setVelocityX(Math.abs(obj.getVelocityX()));
                    break;
                case UPPER_RIGHT_EDGE:
                    temp = obj.getVelocityX();
                    obj.setVelocityX(obj.getVelocityY());
                    obj.setVelocityY(temp);
                    break;
                case UPPER_LEFT_EDGE:
                    temp = obj.getVelocityX();
                    obj.setVelocityX(-obj.getVelocityY());
                    obj.setVelocityY(-temp);
                    break;
                case LOWER_LEFT_EDGE:
                    temp = obj.getVelocityX();
                    obj.setVelocityX(obj.getVelocityY());
                    obj.setVelocityY(temp);
                    break;
                case LOWER_RIGHT_EDGE:
                    temp = obj.getVelocityX();
                    obj.setVelocityX(-obj.getVelocityY());
                    obj.setVelocityY(-temp);
                    break;
            }
        }
    }

    private void removeAlien(Alien alien, Direction dir) {
        if (alien.getType().equals(AlienType.PROTECTING) && !(dir.equals(Direction.DOWN)||dir.equals(Direction.LOWER_LEFT_EDGE)||dir.equals(Direction.LOWER_RIGHT_EDGE)))
            GameState.getInstance().getAliensList().remove(alien);
        else if (alien.getType().equals(AlienType.COOPERATIVE)) {
            GameState.getInstance().getAliensList().remove(alien);
            CooperativeAlien.setHit(true);
        } else if (alien.getType().equals(AlienType.DRUNK)) {
            if (((DrunkAlien) alien).getTheAlien() != null) {
                if (((DrunkAlien) alien).getTheAlien().getType().equals(AlienType.PROTECTING) && dir.equals(Direction.UP))
                    GameState.getInstance().getAliensList().remove(alien);
                else if (!((DrunkAlien) alien).getTheAlien().getType().equals(AlienType.PROTECTING))
                    GameState.getInstance().getAliensList().remove(alien);
            } else {
                GameState.getInstance().getAliensList().remove(alien);
            }
        } else if (alien.getType().equals(AlienType.REPAIRING))
            GameState.getInstance().getAliensList().remove(alien);

    }

    private void removeTheBrick(Brick obj, Direction dir,boolean isFireBall) {
        if (obj.getType().equals(BrickType.HalfMetalBrick) &&
                (dir.equals(Direction.DOWN) || dir.equals(Direction.LOWER_LEFT_EDGE) || dir.equals(Direction.LOWER_RIGHT_EDGE))) {
            HalfMetalBrick obj1=(HalfMetalBrick) obj;
           if(isFireBall) {
               if (obj1.isFireballHit()) {
                   removedBrick = obj;
               } else {
                   obj1.setFireballHit(true);
                   removedBrick = null;
               }
           }
           else{
               removedBrick = null;
           }
        } else {
            removedBrick = obj;
        }
    }

    private void ballPaddleCollision(Ball obj1) {

        if (!obj1.isSticked()) {
            double edgeDX = Paddle.getInstance().getWidth() / 2;
            double edgeDY = Paddle.getInstance().getHeight() / 2;

            double upperLeftX = -edgeDX;
            double upperLeftY = edgeDY;

            double upperRightX = edgeDX;
            double upperRightY = edgeDY;

            double lowerLeftX = -edgeDX;
            double lowerLeftY = -edgeDY;

            double lowerRightX = edgeDX;
            double lowerRightY = -edgeDY;

            double angle = Math.toRadians(-Paddle.getInstance().getRotateAngle());

            double upperLeftXRotated = upperLeftX * Math.cos(angle) - upperLeftY * Math.sin(angle);
            double upperLeftYRotated = upperLeftY * Math.cos(angle) + upperLeftX * Math.sin(angle);

            double upperRightXRotated = upperRightX * Math.cos(angle) - upperRightY * Math.sin(angle);
            double upperRightYRotated = upperRightY * Math.cos(angle) + upperRightX * Math.sin(angle);

            double lowerLeftXRotated = lowerLeftX * Math.cos(angle) - lowerLeftY * Math.sin(angle);

            double lowerRightXRotated = lowerRightX * Math.cos(angle) - lowerRightY * Math.sin(angle);

            double ballX = obj1.getPosX() + (double) obj1.getWidth() / 2 - Paddle.getInstance().getPosX() - edgeDX;
            double ballY = -obj1.getPosY() - (double) obj1.getHeight() / 2 + Paddle.getInstance().getPosY() + edgeDY;

            double mUpper = (upperLeftYRotated - upperRightYRotated) / (upperLeftXRotated - upperRightXRotated);


            if (isCollide == false) {
                if ((ballY - (mUpper * ballX + edgeDY) <= obj1.getHeight() / 2)
                        && (ballX > upperLeftXRotated)
                        && (ballX <= upperRightXRotated)
                        && (ballY - (mUpper * ballX + edgeDY) > 0)) {
                    double velX = obj1.getVelocityX();
                    double velY = -Math.abs(obj1.getVelocityY());
                    double rotatedVelX = velX * Math.cos(-2 * angle) - velY * Math.sin(-2 * angle);
                    double rotatedVelY = velY * Math.cos(-2 * angle) + velX * Math.sin(-2 * angle);
                    obj1.posX = obj1.posX + (int) rotatedVelX;
                    obj1.posY = obj1.posY + (int) rotatedVelY;
                    obj1.setVelocityX(rotatedVelX);
                    obj1.setVelocityY(rotatedVelY);
                    if (Paddle.getInstance().isSticky()) {
                        obj1.setSticked(true);

                    }
                    obj1.move();

                    isCollide = true;

                } else if ((((mUpper * ballX - edgeDY) - ballY <= obj1.getHeight() / 2)
                        && (ballX >= lowerLeftXRotated)
                        && (ballX <= lowerRightXRotated)
                        && ((ballY - mUpper * ballX - edgeDY) < 0))) {
                    double velX = obj1.getVelocityX();
                    double velY = Math.abs(obj1.getVelocityY());
                    if (velX * Paddle.getInstance().getVelocityX() > 0)
                        obj1.posX = obj1.posX + (int) (Paddle.getInstance().getVelocityX());
                    double rotatedVelX = velX * Math.cos(-2 * angle) - velY * Math.sin(-2 * angle);
                    double rotatedVelY = velY * Math.cos(-2 * angle) + velX * Math.sin(-2 * angle);
                    obj1.posX = obj1.posX + (int) rotatedVelX;
                    obj1.posY = obj1.posY + (int) rotatedVelY;
                    obj1.setVelocityX(rotatedVelX);
                    obj1.setVelocityY(rotatedVelY);
                    isCollide = true;

                } else if (((ballY - (mUpper * ballX + edgeDY)) <= obj1.getHeight() / 2)
                        && ((ballY - (mUpper * ballX - edgeDY)) >= -obj1.getHeight() / 2)
                        && (((ballX + obj1.getWidth() / 2) <= upperLeftXRotated) && ((ballX + obj1.getWidth() / 2) >= lowerLeftXRotated))) {
                    double velX = -obj1.getVelocityX();
                    double velY = -obj1.getVelocityY();
                    if (velX * Paddle.getInstance().getVelocityX() > 0)
                        obj1.posX = obj1.posX + (int) (Paddle.getInstance().getVelocityX());
                    obj1.posX = obj1.posX + (int) velX;
                    obj1.posY = obj1.posY + (int) velY;
                    obj1.setVelocityX(velX);
                    obj1.setVelocityY(velY);
                    isCollide = true;

                } else if (((ballY - (mUpper * ballX + edgeDY)) <= obj1.getHeight() / 2)
                        && ((ballY - (mUpper * ballX - edgeDY)) >= -obj1.getHeight() / 2)
                        && ((ballX >= upperRightXRotated) && ((ballX - obj1.getWidth()) <= lowerRightXRotated))) {
                    double velX = -obj1.getVelocityX();
                    double velY = -obj1.getVelocityY();
                    if (velX * Paddle.getInstance().getVelocityX() > 0)
                        obj1.posX = obj1.posX + (int) (Paddle.getInstance().getVelocityX());

                    obj1.posX = obj1.posX + (int) velX;
                    obj1.posY = obj1.posY + (int) velY;
                    obj1.setVelocityX(velX);
                    obj1.setVelocityY(velY);
                    isCollide = true;
                }
            } else {
                isCollide = false;
            }
        }
    }

    public void mineBrickBrickCollisions(MineBrick obj1, Brick obj2) {
        if (!(obj2.getPosX() > obj1.getPosX() + obj1.getWidth() || obj2.getPosX() + obj2.getWidth() < obj1.getPosX()
                || obj2.getPosY() > obj1.getPosY() + obj1.getHeight() || obj2.getPosY() + obj2.getHeight() < obj1.getPosY())) {
            obj1.setRotationDirection(obj1.getRotationDirection() * -1);
            obj2.setVelocityX(-1 * obj2.getVelocityX());
        }
    }

    public Alien getRemovedAlien() {
        return removedAlien;
    }


}
