package domain.factories;

import domain.brick.*;

public class BrickFactory {
    /*
     * Rep Invariant:
     * if type = (BrickType) SimpleBrick, type != (BrickType) HalfMetalBrick
     * if type = (BrickType) SimpleBrick, type != (BrickType) WrapperBrick
     * if type = (BrickType) SimpleBrick, type != (BrickType) MineBrick
     * if type = (BrickType) HalfMetalBrick, type != (BrickType) WrapperBrick
     * if type = (BrickType) HalfMetalBrick, type != (BrickType) MineBrick
     * if type = (BrickType) MineBrick, type != (BrickType) WrapperBrick
     * if posX<0, velocityX>0
     * if posX+width > FrameWidth , velocityX<0
     * if type != (BrickType) MineBrick, velecityY=0
     */


    private static BrickFactory singleton;

    /***
     * Overview: BrickFactory is a Singleton class for creating instances of
     * different types of bricks with calling constructor of different brick
     * classes.
     */
    /***
     * Abstraction Function:
     * Represent an object that creates different types of brick objects.
     */

    private BrickFactory(){}

    /***
     * createBrick function for creating instances of bricks with calling constructor of them
     * @param type       brick type
     * @param posX 		 initial position X
     * @param posY 		 initial position Y
     * @param velocityX  initial velocity X
     * @param velocityY  initial velocity Y
     * @param width      Brick width
     * @param height     Brick height
     */


    public Brick createBrick(BrickType type, int posX, int posY, double velocityX, double velocityY, int width, int height) {
        /***
		@EFFECTS Initialize brick instances with parameters type,posX,posY,
        velocityX,velocityX,width,height.
		*/


        switch (type){
            case SimpleBrick:
                return new SimpleBrick(posX, posY, velocityX, velocityY, width, height);
            case HalfMetalBrick:
                return new HalfMetalBrick(posX, posY, velocityX, velocityY, width, height);
            case WrapperBrick:
                return new WrapperBrick(posX, posY, velocityX, velocityY, width, height);
            case MineBrick:
                return new MineBrick(posX, posY, velocityX, velocityY, width, height);
        }
        return null;
    }

    /***
     * getInstance for accessing the only instance of BrickFactory.
     * @return singleton    the instance of BrickFactory
     */
    public static BrickFactory getInstance(){
        /***
         @EFFECTS Initialize BrickFactory instance if corresponding field is null
         and returns it.
         */
        if (singleton == null){
            singleton = new BrickFactory();
        }
        return singleton;
    }
}
