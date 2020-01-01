package domain.factories;

import Services.Constants;
import domain.alien.*;

public class  AlienFactory {

	public static final int COOPERATIVE_POS_X = 0;
	public static final int COOPERATIVE_POS_Y = 0;
	
	public static final int COOPERATIVE_VELOCITY_X = 0;
	public static final int COOPERATIVE_VELOCITY_Y = 1;
	
	public static final int PROTECTIVE_POS_X = 0;
	public static final int PROTECTIVE_POS_Y = Constants.BUILDMODE_GRID_INITIAL_Y + Constants.BUILDMODE_GRID_HEIGHT + Constants.BRICK_HEIGHT;
	
	public static final int PROTECTIVE_VELOCITY_X = 1;
	public static final int PROTECTIVE_VELOCITY_Y = 0;
	
	public static final int REPAIRING_POS_X = 0;
	public static final int REPAIRING_POS_Y = 0;
	
	public static final int REPAIRING_VELOCITY_X = 1;
	public static final int REPAIRING_VELOCITY_Y = 1;
	
	public static final int DRUNK_POS_X = 0;
	public static final int DRUNK_POS_Y = 0;
	
	public static final int DRUNK_VELOCITY_X = 1;
	public static final int DRUNK_VELOCITY_Y = 1;
	
	public static final int width = Constants.ALIEN_WIDTH;
	public static final int heigth = Constants.ALIEN_HEIGHT;
	
    private static AlienFactory singleton;

    private AlienFactory(){}


    public Alien createAlien(AlienType type) {

        switch (type){
            case COOPERATIVE:
                return createAlien(type,COOPERATIVE_POS_X, COOPERATIVE_POS_Y);
            case REPAIRING:
                return createAlien(type,REPAIRING_POS_X, REPAIRING_POS_Y);
            case PROTECTING:
                return createAlien(type,PROTECTIVE_POS_X, PROTECTIVE_POS_Y);
            case DRUNK:
                return createAlien(type,DRUNK_POS_X, DRUNK_POS_Y);
                
        }
        return null;
    }

    public Alien createAlien(AlienType type, int posX, int posY) {
        switch (type){
            case COOPERATIVE:
                return new CooperativeAlien(posX, posY, COOPERATIVE_VELOCITY_X, COOPERATIVE_VELOCITY_Y, width, heigth, false);
            case REPAIRING:
                return new RepairingAlien(posX, posY, REPAIRING_VELOCITY_X, REPAIRING_VELOCITY_Y, width, heigth, false);
            case PROTECTING:
                return new ProtectingAlien(posX, posY, PROTECTIVE_VELOCITY_X, PROTECTIVE_VELOCITY_Y, width, heigth, false);
            case DRUNK:
                return new DrunkAlien(posX, posY, DRUNK_VELOCITY_X, DRUNK_VELOCITY_Y, width, heigth, false);

        }
        return null;
    }


    public static AlienFactory getInstance(){
        if (singleton == null){
            singleton = new AlienFactory();
        }
        return singleton;
    }
}
