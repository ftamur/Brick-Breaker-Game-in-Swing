package Services;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {

	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = (int) (8 * screenSize.getWidth() / 10);
	public static final int SCREEN_HEIGHT = (int) (8 * screenSize.getHeight() / 10);

	// for login - menu - brick - count - gameover panels
	
	public static final int BEFORE_FULL_SCREEN_WIDTH = 750;
	public static final int BEFORE_FULL_SCREEN_HEIGHT = 500;

	public static final int L = SCREEN_WIDTH / 10;
	public static final int T = 20;
	public static final int NEXT_STATE_INTERVAL = 10;
	public static final int HOLD_THRESHOLD = 150;
	public static final int BRICK_WIDTH = SCREEN_WIDTH / 50;
	
	// buildmode and play panels
	
	public final static int SCREEN_X = (int) (screenSize.getWidth() / 10);
	public final static int SCREEN_Y = (int) (screenSize.getHeight() / 10);

	// for login - menu - brick - count - gameover panels
	
	public final static int BEFORE_FULL_SCREEN_X = (int) ((screenSize.getWidth()/2) - (BEFORE_FULL_SCREEN_WIDTH/2)) ;
	public final static int BEFORE_FULL_SCREEN_Y = (int) ((screenSize.getHeight()/2) - (BEFORE_FULL_SCREEN_HEIGHT/2));

	public static final int BRICK_HEIGHT = 20;
	public static final int MINE_BRICK_RADIUS = 20;
	public static final int BUILDMODE_GRID_ROWS = 3 * SCREEN_HEIGHT / 100;
	
	// Here - 2 for the give some space for sides.
	public static final int BUILDMODE_GRID_COLS = SCREEN_WIDTH / BRICK_WIDTH - 2;

	public static final int JFRAME_TITLE_HEIGHT = 26; // This is default comes from swing library.

	public static final int BUILDMODE_GRID_WIDTH = BUILDMODE_GRID_COLS * BRICK_WIDTH;
	public static final int BUILDMODE_GRID_HEIGHT = BUILDMODE_GRID_ROWS * BRICK_HEIGHT;

	public static final int BUILDMODE_GRID_INITIAL_X = BRICK_WIDTH;
	public static final int BUILDMODE_GRID_INITIAL_Y = BRICK_HEIGHT;
		
	public static final int ALIEN_WIDTH = 20;
	public static final int ALIEN_HEIGHT = 20;
	
	public static final int REPAIRING_ALIEN_TIMES = 10;

	public static final int POWERUP_OBJECT_EDGE_LENGTH = 30;
    public static final int POWERUP_OBJECT_VELOCITY_Y = 3;
	public static final int POWER_UP_ICON_EDGE_LENGTH = 50;
	public static final int POWERUP_PANEL_LEFT_EDGE_OFFSET = 50;
	public static final int POWERUP_PANEL_BOTTOM_EDGE_OFFSET = 130;
	public static final int POWERUP_PANEL_IN_BETWEEN_OFFSET = 10;

	public static final int LIFE_ICONS_LEFT_EDGE_OFFSET = 50;
	public static final int LIFE_ICONS_IN_BETWEEN_OFFSET = 40;
	public static final int LIFE_ICONS_BOTTOM_EDGE_OFFSET = 50;
	public static final int LIFE_ICON_EDGE_SIZE = 30;

	public static final int BUTTON_PANEL_RIGHT_EDGE_OFFSET = 200;
	public static final int BUTTON_BOTTOM_EDGE_OFFSET = 30;
	public static final int BUTTON_IN_BETWEEN_OFFSET = 10;
	public static final int BUTTON_WIDTH = 70;
	public static final int BUTTON_HEIGHT = 30;
	
	
	public static final int LASER_OFFSET = 5;



}
