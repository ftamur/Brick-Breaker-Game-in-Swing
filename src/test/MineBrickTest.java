package test;


import domain.brick.BrickType;
import domain.brick.MineBrick;
import org.junit.Before;
import org.junit.Test;

import Services.Constants;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class MineBrickTest {

    private MineBrick brick;
    private double angle = 90;

    @Before
    public void setUp(){
        this.brick = new MineBrick(300,300,1,1,10,10);
        this.brick.setAngle(angle);
    }

    @Test
    public void typeTest(){
        assertEquals(BrickType.MineBrick, this.brick.getType());
        assertEquals(300, this.brick.getPosX());
        assertEquals(300, this.brick.getPosY());
        assertEquals(1, this.brick.getVelocityY());
        assertEquals(1, this.brick.getVelocityY());
        assertEquals(10, this.brick.getWidth());
        assertEquals(10, this.brick.getHeight());
        assertEquals(angle, this.brick.getAngle());
    }

    @Test
    public void isStartMovingTest(){
        assertFalse(this.brick.isStartMoving());
    }

    @Test
    public void setStartMovingTest(){
        this.brick.setStartMoving(true);
        assertTrue(this.brick.isStartMoving());

    }

    @Test
    public void getRotationDirectionTest(){
        assertEquals(-1, this.brick.getRotationDirection());
    }

    @Test
    public void setRotationDirectionTest(){
        brick.setRotationDirection(1);
        assertEquals(1, brick.getRotationDirection());
    }

    @Test
    public void getAngleTest(){
        assertEquals(angle, brick.getAngle());
    }

    @Test
    public void setAngleTest(){
        brick.setAngle(180);
        assertEquals(180, brick.getAngle());
    }


    @Test
    public void moveTest(){

        double velocityX = this.brick.getRotationDirection()*(Math.cos(Math.toRadians(this.brick.getAngle())))* Constants.L/80;
        double velocityY = this.brick.getRotationDirection()*(Math.sin(Math.toRadians(this.brick.getAngle())))* Constants.L/80;
        double angle = this.brick.getAngle() + this.brick.getRotationDirection() * -1;

        this.brick.move();

        assertEquals(velocityX, brick.getVelocityX());
        assertEquals(velocityY, brick.getVelocityY());
        assertEquals(angle, brick.getAngle());

        this.brick.setAngle(359);
        this.brick.move();

        assertEquals(0.0, this.brick.getAngle());

    }


}
