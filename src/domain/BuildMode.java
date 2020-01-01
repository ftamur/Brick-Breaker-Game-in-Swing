package domain;

import domain.brick.Brick;
import domain.brick.BrickType;
import domain.factories.BrickFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import Services.Constants;
import Services.HelperFunctions;

/**
 * BuildMode is a singleton class which stores numbers of different types of bricks,
 * initializes bricks list by creating Bricks and put them in an available position
 * so that any two bricks do not collide with each other.
 *
 * Also, in the build mode of the game, if any brick is removed or dragged to another position,
 * BuildMode class updates bricks list and after everything is done in build mode, it passes the bricks list
 * to play mode of the game.
 *
 *
 *
 */
public class BuildMode {

	private int simpleBrickCount;
	private int halfMetalBrickCount;
	private int wrapperBrickCount;
	private int mineBrickCount;
	private int totalBrickCount;

	private int buildModeGridRow = Constants.BUILDMODE_GRID_ROWS;
	private int buildModeGridCol = Constants.BUILDMODE_GRID_COLS;

	private int[][] buildModeGrid = new int[buildModeGridRow][buildModeGridCol];
	private LinkedList<int[]> avaliableGridPositions = new LinkedList<int[]>();
	private ArrayList<Brick> bricks = new ArrayList<Brick>();

	private Random random = new Random();

	private static BuildMode buildMode;

	private boolean firstClick;
	private Brick movedBrick;
	private int prevPosX;
	private int prevPosY;
	private int prevGridPosX;
	private int prevGridPosY;

	/**
	 * Private constructor of singleton BuildMode class.
	 *
	 */
	private BuildMode() {
		// TODO Auto-generated constructor stub

		// default counts for bricks.

		simpleBrickCount = 75;
		halfMetalBrickCount = 10;
		wrapperBrickCount = 10;
		mineBrickCount = 5;
		totalBrickCount = 100;

		firstClick = false;

	}

	/**
	 *
	 * @param simpleBrick: number of simple bricks specified in input panel
	 * @param halfMetalBrick: number of half metal bricks specified in input panel
	 * @param wrapperBrick: number of wrapper bricks specified in input panel
	 * @param mineBrick: number of mine bricks specified in input panel
	 */

	public void getBrickCounts (int simpleBrick, int halfMetalBrick,
			int wrapperBrick, int mineBrick) {
		// @modifies: simpleBrickCount, halfMetalBrickCount, wrapperBrickCount, mineBrickCount, totalBrickCount.
		// @effects: sets the attributes to values given in the parameters. Calls initializeGridArray
		//	function after all values are set.



		simpleBrickCount = simpleBrick;
		halfMetalBrickCount = halfMetalBrick;
		wrapperBrickCount = wrapperBrick;
		mineBrickCount = mineBrick;

		totalBrickCount = simpleBrick + halfMetalBrick + wrapperBrick + mineBrick;

		initializeGridArray();

	}

	/**
	 *  Initialize availablePositions list to get an available position faster than random choosing.
	 */
	private void initializeAvaliableGridPositions() {

		// @modifies: availableGridPositions list, adds the positions to the list.
		// @effects: all possible available positions is added to the list.

		for (int i=0; i < buildModeGridRow; i++) {

			for (int j=0; j < buildModeGridCol; j++) {

				int[] position = {i, j};
				avaliableGridPositions.add(position);

			}

		}

	}

	/**
	 *  Initializes buildModeGridArray with values either 1,2,3 or 4 according to numbers and types of bricks.
	 *  Also, it first initializes available positions to pick random positions to set values.
	 *
	 * @see Arrays
	 *
	 */

	private void initializeGridArray() {
		// TODO Auto-generated method stub

		// @modifies: buildModeGrid 2D array, availableGridPositions list, gets a random
		// position from availableGridPositions and set that position in buildModeGrid to either 1,2,3 or 4.
		// and then removes that position from availablePositions.

		// @effects: buildModeGrid 2D array is filled with either 1,2,3 or 4 (types of bricks)
		// that means there will be a brick in that position.
		// availableGridPositions size is decremented by the number of bricks.

		// first initializes half-metal bricks. Set the 1 in the array.
		// row for half-metals is fixed that at bottom row.

		initializeAvaliableGridPositions();

		int positionStartForHalfMetal;

		for (int i = 0; i < halfMetalBrickCount; i++) {

			positionStartForHalfMetal = avaliableGridPositions.size() - buildModeGridCol;

			int randomPosition = random.nextInt(buildModeGridCol) + positionStartForHalfMetal;
			int[] position = avaliableGridPositions.get(randomPosition);
			buildModeGrid[position[0]][position[1]] = 1;
			avaliableGridPositions.remove(position);

		}

		// initialize simple bricks.

		for (int i = 0; i < simpleBrickCount; i++) {

			int randomPosition = random.nextInt(avaliableGridPositions.size());
			int[] position = avaliableGridPositions.get(randomPosition);
			buildModeGrid[position[0]][position[1]] = 2;
			avaliableGridPositions.remove(position);

		}

		// initialize wrapper bricks.

		for (int i = 0; i < wrapperBrickCount; i++) {

			int randomPosition = random.nextInt(avaliableGridPositions.size());
			int[] position = avaliableGridPositions.get(randomPosition);
			buildModeGrid[position[0]][position[1]] = 3;
			avaliableGridPositions.remove(position);

		}

		// initialize mine bricks.

		for (int i = 0; i < mineBrickCount; i++) {

			int randomPosition = random.nextInt(avaliableGridPositions.size());
			int[] position = avaliableGridPositions.get(randomPosition);
			buildModeGrid[position[0]][position[1]] = 4;
			avaliableGridPositions.remove(position);

		}


	}

	/**
	 *
	 * @return static instance buildMode of BuildMode singleton class
	 */
	public static BuildMode getInstance() {

		if (buildMode == null)
			buildMode = new BuildMode();

		return buildMode;

	}

	public int[][] getBuildModeGrid() {
		return buildModeGrid;
	}

	/**
	 * Creates all bricks and adds them to bricks list according to values of buildModeGrid 2D Array.
	 * @return bricks: an ArrayList that stores all bricks.
	 *
	 * @see ArrayList
	 *
	 */
	public ArrayList<Brick> createBricks() {
		// TODO Auto-generated method stub

		int velocityX = 0;
		int velocityY = 0;

		int brickWidth = Constants.BRICK_WIDTH;
		int brickHeight = Constants.BRICK_HEIGHT;

		int mineRadius =  Constants.MINE_BRICK_RADIUS;

		// paint the grid matrix.

		for (int i_rows = 0; i_rows < Constants.BUILDMODE_GRID_ROWS; i_rows++) {

			for (int i_cols = 0; i_cols < Constants.BUILDMODE_GRID_COLS; i_cols++) {

				// Here -1 for to put some margin from top, left and right.
				int brickCode = buildModeGrid[i_rows][i_cols];
				int posX = i_cols * brickWidth + Constants.BUILDMODE_GRID_INITIAL_X;
				int posY = i_rows * brickHeight + Constants.BUILDMODE_GRID_INITIAL_Y;

				switch (brickCode) {
				case 0:
					bricks.add(null);
					break;

				case 1:
					bricks.add(BrickFactory.getInstance().createBrick(BrickType.HalfMetalBrick, posX,
							posY, velocityX, velocityY, brickWidth, brickHeight));

					break;

				case 2:
					bricks.add(BrickFactory.getInstance().createBrick(BrickType.SimpleBrick, posX,
							posY, velocityX, velocityY, brickWidth, brickHeight));

					break;
				case 3:
					bricks.add(BrickFactory.getInstance().createBrick(BrickType.WrapperBrick, posX,
							posY, velocityX, velocityY, brickWidth, brickHeight));

					break;
				case 4:
					int posXforMine = posX + brickWidth/2 - 10;
					bricks.add(BrickFactory.getInstance().createBrick(BrickType.MineBrick, posXforMine,
							posY, velocityX, velocityY, mineRadius, mineRadius));
					break;

				default:
					break;

				}

			}

		}

		return bricks;

	}

	/**
	 *
	 * @param posX: Selected column of buildModeGrid 2D Array.
	 * @param posY: Selected row of buildModeGrid 2D Array.
	 * @param updateValue: new value to set.
	 */
	public void updateGridArray(int gridPosX, int gridPosY, int updateValue) {

		if (gridPosX != -1 && gridPosY != -1)
			buildModeGrid[gridPosY][gridPosX] = updateValue;

	}

	public void addBrick(BrickType brickType) {

		int randPos;
		
		int positionStartForHalfMetal = avaliableGridPositions.size() - buildModeGridCol;
		
		if (brickType.equals(BrickType.HalfMetalBrick))
			randPos = (new Random()).nextInt(buildModeGridCol) + positionStartForHalfMetal;
		else
			randPos = (new Random()).nextInt(avaliableGridPositions.size());
					
		int[] avaiblePosition = getAvaliableGridPositions().remove(randPos);
		
		int gridPosX = avaiblePosition[1];
		int gridPosY = avaiblePosition[0];

		int posX = gridPosXtoPosX(gridPosX);
		int posY = gridPosYtoPosY(gridPosY);

		Brick brick = BrickFactory.getInstance().createBrick(brickType, posX, posY, 0, 0, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT);
		updateGridArray(gridPosX, gridPosY, brickTypetoInt(brickType));
		updateBrickList(gridPosX, gridPosY, brick);


	}

	private int brickTypetoInt(BrickType brickType) {

		switch (brickType) {
		case MineBrick:
			return 4;
		case WrapperBrick:
			return 3; 
		case SimpleBrick:
			return 2;
		case HalfMetalBrick:
			return 1;			
		default:
			return 0;
		}

	}

	/**
	 *
	 * @param mousePosX: X position of captured mouse click.
	 * @param mousePosY: Y position of captured mouse click
	 * @return removed: if there is a brick in the clicked position then it returns that Brick, otherwise returns null.
	 */

	public Brick removeBrick(int mousePosX, int mousePosY) {
		// TODO Auto-generated method stub

		Brick removed = null;

		int gridPosX = posXtoGridX(mousePosX);
		int gridPosY = posYtoGridY(mousePosY);

		if (gridPosX != -1 && gridPosY != -1) {

			if (firstClick && prevPosX == gridPosX && prevPosY == gridPosY) {

				if (bricks.get(gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX) != null) {
					removed = bricks.get(gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX);
				}

				if (removed != null) {
					updateGridArray(gridPosX, gridPosY, 0);
					bricks.set(gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX, null);
					this.totalBrickCount--;
				}

				firstClick = false;

			} else {

				prevPosX = gridPosX;
				prevPosY = gridPosY;
				firstClick = true;

			}

		}

		return removed;

	}

	/**
	 *
	 * @param gridPosX: Selected column of buildModeGrid 2D array.
	 * @param gridPosY: Selected row of buildModeGrid 2D array.
	 * @return value at the given index of bricks list.
	 */
	public Brick getBrick(int gridPosX, int gridPosY) {

		// @requires: gridPosX < Constants.BUILDMODE_GRID_COLS and
		// gridPosY < Constants.BUILDMODE_GRID_ROWS

		// @effects: if the position (gridPosX, gridPosY) points to out of buildModeGrid,
		// then it returns null, Otherwise it returns the value of bricks list at the corresponding index.
		// However, still it returns either a Brick or null.

		if (gridPosX != -1 && gridPosY != -1) {

			if (gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX < bricks.size())
				return bricks.get(gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX);

		}

		return null;

	}

	public boolean checkCollision(int gridPosX, int gridPosY) {

		// @requires: gridPosX < Constants.BUILDMODE_GRID_COLS and
		// gridPosY < Constants.BUILDMODE_GRID_ROWS

		// @effects: if the value of buildModeGrid array at that index is 0 returns false;
		// Otherwise returns true.

		if (gridPosX == -1 || gridPosY == -1)
			return false;

		return buildModeGrid[gridPosY][gridPosX] == 0 ? false : true;

	}

	public void brickDragged(int mousePosX, int mousePosY) {
		// TODO Auto-generated method stub

		if (movedBrick != null) {

			movedBrick.setPosX(mousePosX);
			movedBrick.setPosY(mousePosY);

		} else {

			int gridPosX = posXtoGridX(mousePosX);
			int gridPosY = posYtoGridY(mousePosY);

			prevGridPosX = gridPosX;
			prevGridPosY = gridPosY;

			movedBrick = getBrick(gridPosX, gridPosY);

		}

	}

	/**
	 *
	 * @param mousePosX: X position of captured mouse event.
	 * @param mousePosY: Y position of captured mouse event.
	 * Updates the position of dragged brick according to mouse
	 *                    drag event in the both bricks list and buildModeGrid array.
	 */

	public void updateDraggedBrick(int mousePosX, int mousePosY) {
		// TODO Auto-generated method stub

		int gridPosX = posXtoGridX(mousePosX);
		int gridPosY = posYtoGridY(mousePosY);

		if (movedBrick != null) {

			if (gridPosX == -1 || gridPosY == -1) {

				movedBrick.setPosX(gridPosXtoPosX(prevGridPosX));
				movedBrick.setPosY(gridPosYtoPosY(prevGridPosY));

			} else if (checkCollision(gridPosX, gridPosY)) {

				movedBrick.setPosX(gridPosXtoPosX(prevGridPosX));
				movedBrick.setPosY(gridPosYtoPosY(prevGridPosY));

			} else {

				updateGridArray(prevGridPosX, prevGridPosY, 0);
				updateBrickList(prevGridPosX, prevGridPosY, null);

				updateGridArray(gridPosX, gridPosY, 1);
				updateBrickList(gridPosX, gridPosY, movedBrick);

				movedBrick.setPosX(gridPosXtoPosX(gridPosX));
				movedBrick.setPosY(gridPosYtoPosY(gridPosY));

			}

		}

		movedBrick = null;

	}

	public Brick getMovedBrick() {
		return movedBrick;
	}


	public ArrayList<Brick> getBricksList() {
		return bricks;
	}

	private int posXtoGridX(int posX) {
		// TODO Auto-generated method stub
		int gridPosX = posX / Constants.BRICK_WIDTH - Constants.BUILDMODE_GRID_INITIAL_X / Constants.BRICK_WIDTH;
		return (gridPosX < 0 || gridPosX >= Constants.BUILDMODE_GRID_COLS) ? -1 : gridPosX;
	}

	private int posYtoGridY(int posY) {
		// TODO Auto-generated method stub
		int gridPosY = posY / Constants.BRICK_HEIGHT - Constants.BUILDMODE_GRID_INITIAL_Y / Constants.BRICK_HEIGHT;
		return (gridPosY < 0 || gridPosY >= Constants.BUILDMODE_GRID_ROWS) ? -1 : gridPosY;
	}

	private int gridPosXtoPosX(int gridPosX) {
		// TODO Auto-generated method stub
		return gridPosX * Constants.BRICK_WIDTH + Constants.BUILDMODE_GRID_INITIAL_X;
	}

	private int gridPosYtoPosY(int gridPosY) {
		// TODO Auto-generated method stub
		return gridPosY * Constants.BRICK_HEIGHT + Constants.BUILDMODE_GRID_INITIAL_Y;
	}

	private void updateBrickList(int gridPosX, int gridPosY, Brick brick) {
		// @requires: gridPosX > -1 and gridPosX < Constants.BUILDMODE_GRID_COLS
		// gridPosY < -1 and gridPosY < Constants.BUILDMODE_GRID_ROWS

		// @modifies: bricks list, sets the corresponding index to the given Brick.

		// @effects: corresponding index of bricks list is updated and set to a value Brick.

		bricks.set(findBrickListIndex(gridPosX, gridPosY), brick);

	}

	private int findBrickListIndex(int gridPosX, int gridPosY) {
		// @requires: gridPosX > -1 and gridPosX < Constants.BUILDMODE_GRID_COLS
		// gridPosY < -1 and gridPosY < Constants.BUILDMODE_GRID_ROWS
		// @effects: returns the correct index of the position (gridPosX,gridPosY) in the bricks list.
		return gridPosY * Constants.BUILDMODE_GRID_COLS + gridPosX;
	}

	public int getTotalBrickCount() {
		return totalBrickCount;
	}


	public ArrayList<Brick> getBricks() {
		return bricks;
	}


	public void setBricks(ArrayList<Brick> bricks) {
		this.bricks = bricks;
	}


	public int getSimpleBrickCount() {
		return simpleBrickCount;
	}


	public int getHalfMetalBrickCount() {
		return halfMetalBrickCount;
	}


	public int getWrapperBrickCount() {
		return wrapperBrickCount;
	}


	public int getMineBrickCount() {
		return mineBrickCount;
	}


	public int getBuildModeGridRow() {
		return buildModeGridRow;
	}


	public int getBuildModeGridCol() {
		return buildModeGridCol;
	}


	public LinkedList<int[]> getAvaliableGridPositions() {
		return avaliableGridPositions;
	}








}