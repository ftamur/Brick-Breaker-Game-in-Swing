package domain.powerups.timedPowerups;

import domain.GameState;
import domain.powerups.PowerUpEnum;


/**
 * Extends abstract TimedPowerUp class.
 * ChemicalBall is a type of powerup.
 * When it is activated it can destroy every brick it hits and does not reflect
 * after the collusion.
 */
public class ChemicalBall extends TimedPowerUp {

    /*representation invariant
    duration must be greater than 0
    there must be atleast one ball for chemical ball to appear
    pause cannot pause powerups
     */

    public ChemicalBall() {
        super(PowerUpEnum.CHEMICAL_BALL, 60);

    }

    // TODO: does chemical ball makes all balls chemical upon activation?
    @Override
    public void activate() {
        //@requires: must have the chemical ball power up
        //@effects: all the balls in the game are set to chemical ball
        //@modifies: ballList in the GameState
        super.activate();
        setAllBallsChemical(true);
    }


    @Override
    public void deactivate() {
        //@requires: must be using the chemical ball power up, time must be up.
        //@effects: all the balls in the game are back to regular ball.
        setAllBallsChemical(false);
    }

    /**
     * Sets all the balls in the game chemical.
     * @param isChemical
     */
    private void setAllBallsChemical(boolean isChemical){
        //@requires: isChemical is true.
        //@modifies: ballList in the GameState.
        GameState.getInstance().getBallList().forEach(ball -> ball.setChemical(isChemical));
    }


    /**
     *
     * @return a boolean indicating if the chemical ball is in use.
     */
    public boolean isInUse() {
        return GameState.getInstance().getBallList().get(0).isChemical();
    }
}
