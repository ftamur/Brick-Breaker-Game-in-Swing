package test;

import domain.Ball;
import domain.GameState;
import domain.powerups.timedPowerups.ChemicalBall;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class ChemicalBallTest {

    Ball ball1;
    Ball ball2;
    ChemicalBall chemicalBallPowerUp;


    @Before
    public void setUp(){
        ball1 = new Ball(0,0,0,0,0,0,false ,false);
        ball2 = new Ball(0,0,0,0,0,0,false ,false);
        ArrayList<Ball> balls = new ArrayList<>();
        balls.add(ball1);
        balls.add(ball2);
        GameState.getInstance().setBallList(balls);
        chemicalBallPowerUp = new ChemicalBall();
    }


    @Test
    public void setAllBallsChemicalTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method setAllBallsChemical = ChemicalBall.class.getDeclaredMethod("setAllBallsChemical", boolean.class);
        setAllBallsChemical.setAccessible(true);

        setAllBallsChemical.invoke(chemicalBallPowerUp,true);
        assertTrue( "Set all balls chemical cannot set balls chemical",ball1.isChemical()&& ball2.isChemical());
        setAllBallsChemical.invoke(chemicalBallPowerUp,false);
        assertFalse( "Set all balls chemical cannot set balls chemical",ball1.isChemical() || ball2.isChemical());
    }

    @Test
    public void activateTest() {
        chemicalBallPowerUp.activate();
        assertTrue("Ball1 is not set to chemical",ball1.isChemical());
        assertTrue("Not all balls are set to chemical",ball1.isChemical() && ball2.isChemical());
    }

    @Test
    public void deactivateTest() {
        chemicalBallPowerUp.deactivate();
        assertFalse("Ball1 is not set to not chemical",ball1.isChemical());
        assertFalse( "Not all balls are set to not chemical",ball1.isChemical() || ball2.isChemical());

    }

    @Test
    public void isInUse() {
        chemicalBallPowerUp.activate();
        assertTrue("Is in use returns false when the powerup is activated",chemicalBallPowerUp.isInUse());
        chemicalBallPowerUp.deactivate();
        assertFalse("Is in use returns true when the powerup is deactivated",chemicalBallPowerUp.isInUse());
    }

    @After
    public void tearDown(){
        chemicalBallPowerUp.deactivate();
    }
}
