package test;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


import domain.GameState;
import domain.alien.Alien;
import domain.brick.BrickType;
import domain.brick.WrapperBrick;
import domain.powerups.PowerUp;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class WrapperBrickTests {

	WrapperBrick wrapperBrick;
	Method trigger;
	
	
	@Before
	public void setUp() {
		
		wrapperBrick = new WrapperBrick(100, 100, 0, 0, 20, 20);
		CopyOnWriteArrayList<Alien> emptyAlienList = new CopyOnWriteArrayList<Alien>();
		ArrayList<PowerUp> emptyPowerUps = new ArrayList<PowerUp>();
		
		GameState.getInstance().setAlienList(emptyAlienList);
		GameState.getInstance().setPowerUps(emptyPowerUps);
		
	}
	
	@Test
	public void initialsTest() {
		
		assertEquals(100, wrapperBrick.getPosX());
		assertEquals(100, wrapperBrick.getPosY());
		assertEquals(0, wrapperBrick.getVelocityX());
		assertEquals(0, wrapperBrick.getVelocityY());
		assertEquals(20, wrapperBrick.getWidth());
		assertEquals(20, wrapperBrick.getHeight());
		assertEquals(BrickType.WrapperBrick, wrapperBrick.getType());
			
	}
	
	@Test
	public void triggerTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		trigger = WrapperBrick.class.getDeclaredMethod("trigger");
		trigger.setAccessible(true);
		
		assertEquals(0, GameState.getInstance().getAliensList().size());
		
		trigger.invoke(wrapperBrick);
		assertEquals(1, GameState.getInstance().getAliensList().size());
		
		trigger.invoke(wrapperBrick);
		assertEquals(2, GameState.getInstance().getAliensList().size());
		
	}
	
	@Test
	public void setVelocityTest() {
		
		wrapperBrick.setVelocityX(10);
		assertEquals(10, wrapperBrick.getVelocityX());
		
		wrapperBrick.setVelocityY(12);
		assertEquals(12, wrapperBrick.getVelocityY());
	
	}
	
	@Test
	public void setPosTest() {
		
		wrapperBrick.setPosX(100);
		assertEquals(100, wrapperBrick.getPosX());
		
		
		wrapperBrick.setPosY(100);
		assertEquals(100, wrapperBrick.getPosY());
		
	}
	
	
	
	
}
