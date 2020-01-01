package gui.Panels;

import Services.Constants;
import domain.*;
import domain.Controllers.GameController;
import domain.brick.Brick;
import domain.powerups.PowerUpEnum;
import gui.Coordinator;
import gui.Drawer;
import gui.ScreenEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayPanel extends GamePanel{


	private static PlayPanel playPanel;
	private PanelListener listener = new PanelListener();
	private Thread painterThread;

	private PlayPanel (){
		setFocusTraversalKeysEnabled(false);
		super.setBackground(Color.BLACK);
		initialize();

		painterThread = new Thread(() -> {

			while (true) {
				try {
					GameExpert.getInstance().getSemaphore().acquire();
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		

	}

	private void initialize(){
		this.setLayout(null);
		drawPowerUpPanel();
		drawButtons();
		Score.getInstance().setTotalBrickCount(BuildMode.getInstance().getTotalBrickCount());
	}

	private void drawButtons(){
		int x_offset = Constants.BUTTON_PANEL_RIGHT_EDGE_OFFSET;
		int y_offset = Constants.BUTTON_BOTTOM_EDGE_OFFSET;
		int inbetween_offset = Constants.BUTTON_IN_BETWEEN_OFFSET;
		int buttonWidth = Constants.BUTTON_WIDTH;
		int buttonHeight = Constants.BUTTON_HEIGHT;

		// Save, Load, Pause
		JButton saveButton = new JButton();
		saveButton.setBounds(new Rectangle(Constants.SCREEN_WIDTH-x_offset,Constants.SCREEN_HEIGHT-y_offset,buttonWidth,buttonHeight));
		saveButton.setText("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameController.getInstance().save();
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});

		x_offset -= buttonWidth - inbetween_offset;

		JButton loadButton = new JButton();
		loadButton.setBounds(new Rectangle(Constants.SCREEN_WIDTH-x_offset ,Constants.SCREEN_HEIGHT-y_offset,buttonWidth,buttonHeight));
		loadButton.setText("Load");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameController.getInstance().load();
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});


		x_offset -= buttonWidth - inbetween_offset;

		JButton pauseButton = new JButton();
		pauseButton.setBounds(new Rectangle(Constants.SCREEN_WIDTH-x_offset ,Constants.SCREEN_HEIGHT-y_offset,buttonWidth,buttonHeight));
		pauseButton.setText("Pause");
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameController.getInstance().pause();
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});

		add(saveButton);
		add(loadButton);
		add(pauseButton);


	}


	private void drawPowerUpPanel() {
		int x_offset = Constants.POWERUP_PANEL_LEFT_EDGE_OFFSET;
		int inbetween_offset = Constants.POWERUP_PANEL_IN_BETWEEN_OFFSET;
		int edgeLength = Constants.POWER_UP_ICON_EDGE_LENGTH;

		for (PowerUpEnum powerUpType : PowerUpEnum.getKeyTriggeredPowerUps()) {
			addPowerUpIcon(powerUpType, x_offset);
			x_offset += inbetween_offset + edgeLength;
		}

	}

	private void addPowerUpIcon(PowerUpEnum type, int x_offset) {
		int y = Constants.SCREEN_HEIGHT - Constants.POWERUP_PANEL_BOTTOM_EDGE_OFFSET;
		int edgeLength = Constants.POWER_UP_ICON_EDGE_LENGTH;
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(new ImageIcon(getImageOfPowerUpIcon(type)).getImage().getScaledInstance(edgeLength, edgeLength, Image.SCALE_DEFAULT)));
		label.setBounds(x_offset, y, edgeLength, edgeLength);
		add(label);
	}
	
	//TODO: Find Images For PowerUp Icons
	private Image getImageOfPowerUpIcon(PowerUpEnum type) {
		try {
			switch (type) {
				case TALLER_PADDLE:
					return ImageIO.read(new File("Assets/Images/expand.png"));
				case MAGNET:
					return ImageIO.read(new File("Assets/Images/magnetism.png"));
				case DESTRUCTIVE_LASER_GUN:
					return ImageIO.read(new File("Assets/Images/laser-pen.png"));
				case CHEMICAL_BALL:
					return ImageIO.read(new File("Assets/Images/flask.png"));
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}



	public void startPainting() {
		painterThread.start();
	}

	@Override
    public void bindPanelListener(JFrame frame) {
		frame.addKeyListener(listener);
		frame.addMouseListener(listener);
	}
	@Override
    public void removePanelListener(JFrame frame) {
		frame.removeKeyListener(listener);
		frame.removeMouseListener(listener);
	}

	public static PlayPanel getInstance() {

		if (playPanel == null)
			playPanel = new PlayPanel();

		return playPanel;

	}


	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		boolean cnt = false;
		super.paint(g);

		// Paddle.getInstance()
        Drawer.getInstance().draw(g, Paddle.getInstance());

		// ball
        GameState.getInstance().getBallList().forEach(ball -> Drawer.getInstance().draw(g, ball));

        ArrayList<Brick> bricks = GameState.getInstance().getBrickListWithoutNullValues();
		bricks.forEach(brick -> Drawer.getInstance().draw(g,brick));
        GameState.getInstance().getAliensList().forEach(alien -> Drawer.getInstance().draw(g, alien));

		GameState.getInstance().getBrickExplosionList().forEach(animation -> Drawer.getInstance().drawExplosion(g,animation));
		GameState.getInstance().getMovingMineBricks().forEach(b ->Drawer.getInstance().draw(g,b));

		GameState.getInstance().getPowerUpObjectsList().forEach(powerUpObject -> Drawer.getInstance().draw(g, powerUpObject));


		// display score
		Drawer.getInstance().drawScore(g, Score.getInstance());


		Drawer.getInstance().drawPowerUpCounts(g, GameState.getInstance().getPowerUps());


		// display lives
		Drawer.getInstance().drawLives(g);
		
		// display paddle target laser
		if (Paddle.getInstance().getDestructiveLaserGunShotsLeft() > 0)
			Drawer.getInstance().drawLaserTargetLines(g);

		if(bricks.size()==0) {
			GameOverPanel.getInstance().setGameWon(true);
			Coordinator.getInstance().openScreen(ScreenEnum.GAME_OVER);
		}

	}


	private class PanelListener implements KeyListener, MouseListener {

		private PanelListener(){

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()){
				case KeyEvent.VK_W:
					GameController.getInstance().shoot();
					break;
				case KeyEvent.VK_A:
					GameController.getInstance().rotate(Direction.RIGHT);
					break;
				case KeyEvent.VK_D:
					GameController.getInstance().rotate(Direction.LEFT);
					break;
				case KeyEvent.VK_LEFT:
					GameController.getInstance().movePaddle(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					GameController.getInstance().movePaddle(Direction.RIGHT);
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e){
				// TODO Auto-generated method stub
				switch (e.getKeyCode()) {
					case KeyEvent.VK_P:
						GameController.getInstance().pause();
						break;
					case KeyEvent.VK_S:
						GameController.getInstance().save();
						break;
					case KeyEvent.VK_L:
						GameController.getInstance().load();
						break;
					case KeyEvent.VK_A:
					case KeyEvent.VK_D:
						GameController.getInstance().stopRotate();
						break;
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
						GameController.getInstance().stopPaddle();
						break;
					case KeyEvent.VK_T:
						GameController.getInstance().activateTallerPaddle();
						break;
					case KeyEvent.VK_M:
						GameController.getInstance().activateMagnet();
						break;
					case KeyEvent.VK_C:
						GameController.getInstance().activateChemicalBall();
						break;
					case KeyEvent.VK_SPACE:
						GameController.getInstance().useDestructiveLaserGun();
						break;

				}
			}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			int x = e.getX() - 2;
			int y = e.getY() - 26;
			checkPowerUps(x,y);
		}

		private void checkPowerUps(int x, int y){
			int bottomOffset = Constants.SCREEN_HEIGHT - Constants.POWERUP_PANEL_BOTTOM_EDGE_OFFSET;
			int leftEdgeOffset = Constants.POWERUP_PANEL_LEFT_EDGE_OFFSET;
			int edgeLength = Constants.POWER_UP_ICON_EDGE_LENGTH;
			int inbetweenOffset = Constants.POWERUP_PANEL_IN_BETWEEN_OFFSET;


			if (x < leftEdgeOffset || x > leftEdgeOffset + 3 * inbetweenOffset + 4 * edgeLength)
				return;

			if (y < bottomOffset || y > bottomOffset + edgeLength)
				return;

			x -= leftEdgeOffset;

			if (x < edgeLength) {
				GameController.getInstance().activateMagnet();
				return;
			}
			x -= edgeLength;

			if (x > inbetweenOffset && x < edgeLength + inbetweenOffset) {
				GameController.getInstance().activateChemicalBall();
				return;
			}

			x -= edgeLength + inbetweenOffset;
			if (x > inbetweenOffset && x < edgeLength + inbetweenOffset) {
				GameController.getInstance().activateTallerPaddle();
				return;
			}
			x -= edgeLength + inbetweenOffset;

			if (x > inbetweenOffset && x < edgeLength + inbetweenOffset) {
				GameController.getInstance().useDestructiveLaserGun();
				return;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}
	}


}
