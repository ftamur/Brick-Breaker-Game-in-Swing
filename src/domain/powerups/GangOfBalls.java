package domain.powerups;

import domain.Ball;
import domain.PhysicalObject;

import java.util.ArrayList;

public class GangOfBalls extends PowerUp{

    private Ball targetBall;
    private static ArrayList<Ball> newBalls = new ArrayList<>();

    public GangOfBalls(Ball targetBall){
        super(PowerUpEnum.GANG_OF_BALLS);
        this.targetBall = targetBall;
    }

    @Override
    public void activate() {
        newBalls.addAll(getBallList(targetBall));
    }


    private ArrayList<Ball> getBallList(Ball b){
        ArrayList<Ball> list = new ArrayList<>();
        Ball lastBall = b;
        for (int i = 0; i < 9; i++) {
            lastBall = rotateVelocityOfBall(lastBall, 36);
            list.add(lastBall);
        }
        return list;
    }

    private Ball rotateVelocityOfBall(Ball ball, int angle) {
        Ball newBall = ball.copy();
        rotateVelocityOfPhysicalObject(newBall, angle);
        return newBall;
    }

    private void rotateVelocityOfPhysicalObject(PhysicalObject obj, double angle) {
        double angleInRad = Math.PI * angle / 180;
        double cos = Math.cos(angleInRad);
        double sin = Math.sin(angleInRad);
        double velocityX = obj.getVelocityX();
        double velocityY = obj.getVelocityY();

        obj.setVelocityX(cos * velocityX - sin * velocityY);
        obj.setVelocityY(sin * velocityX + cos * velocityY);
    }

    public static ArrayList<Ball> getNewBalls() {
        return newBalls;
    }

    public static void clearNewBalls() {
        newBalls.clear();
    }

}
