package domain.alien;

import domain.GameState;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Services.Constants;

public class CooperativeAlien extends Alien {

	private static boolean isHit;
	private int selectedRow;
	private static List<Integer> availableRows = Stream.iterate(0, n -> n + 1).limit(Constants.BUILDMODE_GRID_ROWS)
			.collect(Collectors.toList());
	
	private boolean isFirst = true;

	// TODO: REMOVE IS HIT AND TARGET ROW FROM CONSTRUCTOR ARGS ???

	public CooperativeAlien(int posX, int posY, int velocityX, int velocityY, int width, int height,
			boolean isDrunk) {
		super(posX, posY, velocityX, velocityY, width, height, isDrunk);

		this.type = AlienType.COOPERATIVE;
		this.posX = 0;
		this.posY = 0;

	}
	

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		if (isFirst) {
			Collections.shuffle(availableRows);
			selectRow();
			isFirst = false;
		}
		
		int selectedHeight = selectedRow * Constants.BRICK_HEIGHT + Constants.BUILDMODE_GRID_INITIAL_Y;

		if (this.posY < selectedHeight && this.posX == 0) {
			this.posY += this.velocityY;

		} else if (this.posY == selectedHeight && this.posX == 0) {

			destroyBricks(selectedRow);
			this.posX += this.velocityY;

		} else if (this.posY == selectedHeight && this.posX < Constants.SCREEN_WIDTH - this.width) {

			this.posX += this.velocityY;

		} else if (this.posX >= Constants.SCREEN_WIDTH - this.width) {

			this.posY -= this.velocityY;

		}
		
		if (this.posY < 0) {
			
			if (!this.isDrunk())
				GameState.getInstance().getAliensList().remove(this);
			else
				setDone(true);
			
		}
		

	}

	private void selectRow() {
		// TODO Auto-generated method stub
		
		if (availableRows.isEmpty())
			return;

		selectedRow = availableRows.get(0);

		while (!checkAtLeastOne(selectedRow)) {
			selectedRow = availableRows.remove(0);
		}

		selectedRow = availableRows.remove(0);

	}

	private boolean checkAtLeastOne(int row) {
		// TODO Auto-generated method stub

		int startIndex = row * Constants.BUILDMODE_GRID_COLS;
		int endIndex = startIndex + Constants.BUILDMODE_GRID_COLS;

		for (int i = startIndex; i < endIndex; i++) {

			if (GameState.getInstance().getBrickList().get(i) != null)
				return true;

		}

		return false;

	}

	private void destroyBricks(int selectedRow) {

		int startIndex = selectedRow * Constants.BUILDMODE_GRID_COLS;
		int endIndex = startIndex + Constants.BUILDMODE_GRID_COLS;

		for (int i = startIndex; i < endIndex; i++) {

			if (GameState.getInstance().getBrickList().get(i) != null) {

				GameState.getInstance().getBrickList().get(i).destroy();
				GameState.getInstance().getBrickList().set(i, null);

			}

		}

	}

	public static void setHit(boolean hit) {
		isHit = hit;
	}
	
	public static boolean isHit() {
		return isHit;
	}
	
	

}
