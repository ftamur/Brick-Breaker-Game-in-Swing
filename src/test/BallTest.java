package test;

import domain.Ball;
import domain.Paddle;
import org.junit.jupiter.api.Test;

import Services.Constants;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BallTest {

    @Test
    public void getterSetterTest(){
        Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
        ball1.setPosY(10);
        assertEquals(10,ball1.getPosY());
        ball1.setPosX(10);
        assertEquals(10,ball1.getPosX());
        ball1.setVelocityY(2);
        assertEquals(2,ball1.getVelocityY());
        ball1.setVelocityX(-2);
        assertEquals(-2,ball1.getVelocityX());
        ball1.setSticked(true);
        assertEquals(true,ball1.isSticked());
        ball1.setFire(true);
        assertEquals(true,ball1.isFire());
        ball1.setChemical(true);
        assertEquals(true,ball1.isChemical());
        ball1.setSticked(false);
        assertEquals(false,ball1.isSticked());
        ball1.setFire(false);
        assertEquals(false,ball1.isFire());
        ball1.setChemical(false);
        assertEquals(false,ball1.isChemical());
    }


    @Test
    public void areSizescorrect(){
        Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
        assertEquals(17,ball1.getWidth());
        assertEquals(17,ball1.getHeight());
        System.out.println(ball1.toString());
    }

    @Test
    public void isStartsOnPaddle(){
        Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
        int ballX=ball1.getPosX();
        int ballY= ball1.getPosY();
        int paddleX=Paddle.getInstance().getPosX();
        int paddleWidth=Paddle.getInstance().getWidth();
        assertEquals(ballX,paddleX + paddleWidth / 2 - 17 / 2);
        assertEquals(ballY,Paddle.getInstance().getPosY()-20);
    }

    @Test
    public void startWithNoPowerUp(){
        Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
        assertFalse(ball1.isChemical());
        assertFalse(ball1.isFire());
    }

    @Test
    public void isMovingWithPaddleWhenSticked(){
        Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
        int difference=ball1.getPosX()-Paddle.getInstance().getPosX();
        ball1.setSticked(true);
        Paddle.getInstance().setPosX(Paddle.getInstance().getPosX()-15);
        ball1.move();
        assertEquals(difference,ball1.getPosX()-Paddle.getInstance().getPosX());
    }

    @Test
    public void onlyHasOnePowerUp(){
        Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
        assertFalse(ball1.isFire());
        assertFalse(ball1.isChemical());
        ball1.setFire(true);
        assertTrue(ball1.isFire());
        assertFalse(ball1.isChemical());
        ball1.setChemical(true);
        assertTrue(ball1.isChemical());
        assertFalse(ball1.isFire());
    }

     @Test
      public void isStartVelocityPerpendicularToPaddle(){
         Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
         Paddle.getInstance().setRotateAngle(0);
         ball1.setSticked(false);
         assertEquals(ball1.getVelocityX(),0);
         ball1.setSticked(true);
         Paddle.getInstance().setRotateAngle(30);
         double expectedVelocityX=(int)(5*Math.sin(Math.toRadians(-Paddle.getInstance().getRotateAngle())));
         double expectedVelocityY=(int)(5*Math.cos(Math.toRadians(-Paddle.getInstance().getRotateAngle())));
         ball1.setSticked(false);
         assertEquals(expectedVelocityX,ball1.getVelocityX());
         assertEquals(expectedVelocityY, ball1.getVelocityY());
     }

     @Test
      public void canGoOutofFrame(){
         Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
         ball1.setSticked(false);
         ball1.setVelocityX(-5);
         ball1.setVelocityY(-5);
         ball1.setPosX(3);
         ball1.setPosY(3);
         ball1.move();
         ball1.move();
         ball1.move();
         assertTrue(ball1.getPosX()>0);
         assertTrue(ball1.getPosY()>0);
         ball1.setVelocityX(5);
         ball1.setVelocityY(5);
         ball1.setPosX(Constants.SCREEN_WIDTH-20);
         ball1.setPosY(Constants.SCREEN_HEIGHT-20);
         ball1.move();
         ball1.move();
         ball1.move();
         assertTrue(ball1.getPosX()<Constants.SCREEN_WIDTH);
         assertTrue(ball1.getPosY()<Constants.SCREEN_HEIGHT);
     }

     @Test
     public void velocityYIsNeverZero(){
         Ball ball1 =new Ball(Paddle.getInstance().getPosX(), Paddle.getInstance().getPosY(), Paddle.getInstance().getWidth());
         ball1.setSticked(false);
         ball1.setVelocityY(0);
         ball1.move();
         assertNotEquals(ball1.getVelocityY(),0);
     }
}
