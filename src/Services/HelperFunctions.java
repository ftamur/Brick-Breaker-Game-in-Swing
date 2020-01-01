package Services;

public class HelperFunctions {

    public static int trueFloor(double num){
    	
    	/*
    	 * This method floors the number with same idea for positive and negative numbers.
    	 * Example:
    	 * trueFloor(7.5) = 7
    	 * trueFloor(-7.5) = -7
    	 *
    	 */
    	
        return (int) (Math.floor(Math.abs(num)) * (num >0 ? 1:-1));
        
    }
    
    public static int posXtoGridX(int posX) {
		// TODO Auto-generated method stub
		int gridPosX = posX / Constants.BRICK_WIDTH - Constants.BUILDMODE_GRID_INITIAL_X / Constants.BRICK_WIDTH;
		return (gridPosX < 0 || gridPosX >= Constants.BUILDMODE_GRID_COLS) ? -1 : gridPosX;
	}

	public static int posYtoGridY(int posY) {
		// TODO Auto-generated method stub
		int gridPosY = posY / Constants.BRICK_HEIGHT - Constants.BUILDMODE_GRID_INITIAL_Y / Constants.BRICK_HEIGHT;
		return (gridPosY < 0 || gridPosY >= Constants.BUILDMODE_GRID_ROWS) ? -1 : gridPosY;
	}
	
	public static int gridPosXtoPosX(int gridPosX) {
		// TODO Auto-generated method stub
		return gridPosX * Constants.BRICK_WIDTH + Constants.BUILDMODE_GRID_INITIAL_X;
	}

	public static int gridPosYtoPosY(int gridPosY) {
		// TODO Auto-generated method stub
		return gridPosY * Constants.BRICK_HEIGHT + Constants.BUILDMODE_GRID_INITIAL_Y;
	}

    
    
}
