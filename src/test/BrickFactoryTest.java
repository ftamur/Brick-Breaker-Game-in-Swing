package test;

import domain.brick.*;
import domain.factories.BrickFactory;
import org.junit.Before;
import org.junit.Test;

import Services.Constants;

import static junit.framework.TestCase.*;

public class BrickFactoryTest {

    MineBrick mineBrick;
    WrapperBrick wrapperBrick;
    HalfMetalBrick halfMetalBrick;
    SimpleBrick simpleBrick;

    @Before
    public void setUp() throws Exception{
        simpleBrick = new SimpleBrick(0, 0, 2.0,0.0,
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
        halfMetalBrick = new HalfMetalBrick(20, 40, 2.0,0.0,
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
        wrapperBrick = new WrapperBrick(40, 20, 2.0,0.0,
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
        mineBrick = new MineBrick(60, 80, 2.0,0.0,
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
    }


    @Test
    public void getInstanceTest(){
        BrickFactory factory = BrickFactory.getInstance();
        assertNotNull("Get instance returns null",factory);
        assertTrue("Get instance does not return Brick Factory", factory instanceof BrickFactory);
    }


    @Test
    public void createSimpleBrickTest(){
        Brick createdBrick =  BrickFactory.getInstance().createBrick(BrickType.SimpleBrick,
                simpleBrick.getPosX(), simpleBrick.getPosY(), simpleBrick.getVelocityX(),simpleBrick.getVelocityY(),
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);

        assertTrue("Create Simple Brick does not create a wrapper brick",createdBrick instanceof SimpleBrick);
        if(createdBrick.isMoving())
            simpleBrick.setVelocityX(1);
        assertEquals("Create Simple Brick sets properties wrong",createdBrick,simpleBrick);

    }

    @Test
    public void createHalfMetalBrickTest(){
        Brick createdBrick =  BrickFactory.getInstance().createBrick(BrickType.HalfMetalBrick,
                halfMetalBrick.getPosX(), halfMetalBrick.getPosY(), halfMetalBrick.getVelocityX(),halfMetalBrick.getVelocityY(),
                Constants.BRICK_WIDTH,Constants.BRICK_HEIGHT);

        assertTrue("Create Half Metal Brick does not create a wrapper brick",createdBrick instanceof HalfMetalBrick);
        if(createdBrick.isMoving())
            halfMetalBrick.setVelocityX(1);
        assertEquals("Create Half Metal Brick sets properties wrong",createdBrick,halfMetalBrick);

    }

    @Test
    public void createWrapperBrickTest(){
        Brick createdBrick =  BrickFactory.getInstance().createBrick(BrickType.WrapperBrick,
                wrapperBrick.getPosX(), wrapperBrick.getPosY(), wrapperBrick.getVelocityX(),wrapperBrick.getVelocityY(),
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);

        assertTrue("Create Wrapper Brick does not create a wrapper brick",createdBrick instanceof WrapperBrick);
        assertEquals("Create Wrapper Brick sets properties wrong",createdBrick,wrapperBrick);
    }

    @Test
    public void createMineBrickTest(){
        Brick createdBrick =  BrickFactory.getInstance().createBrick(BrickType.MineBrick,
                mineBrick.getPosX(), mineBrick.getPosY(), mineBrick.getVelocityX(),mineBrick.getVelocityY(),
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);

        assertTrue("Create Mine Brick does not create a wrapper brick",createdBrick instanceof MineBrick);
        assertEquals("Create Mine Brick sets properties wrong",createdBrick,mineBrick);
    }

}
