package gui.Panels;

import domain.BuildMode;
import domain.GameState;
import domain.Paddle;
import domain.Controllers.GameController;
import domain.brick.Brick;
import domain.brick.BrickType;
import domain.factories.BrickFactory;
import gui.Coordinator;
import gui.Drawer;
import gui.KeyBinding;
import gui.ScreenEnum;

import javax.swing.*;

import Services.Constants;
import Services.HelperFunctions;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class BuildModePanel extends GamePanel{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static BuildModePanel buildModePanel;
	private ArrayList<Brick> bricks;
	private int buttonsXOffset = 10;
	private int buttonsYOffset = 50;
	private int buttonsWidth = 90;
	private int buttonsHeight = 30;

	private PanelListener listener = new PanelListener();

	private BuildModePanel() {
		// TODO Auto-generated constructor stub
		bricks = BuildMode.getInstance().createBricks();
		setBackground(Color.black);

		this.setLayout(null);
		
		JButton simpleButton = new JButton();
		simpleButton.setBounds(new Rectangle(buttonsXOffset, Constants.SCREEN_HEIGHT - buttonsYOffset, buttonsWidth, buttonsHeight));
		simpleButton.setText("Simple");
		simpleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BuildMode.getInstance().addBrick(BrickType.SimpleBrick);
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});
		
		JButton mineButton = new JButton();
		mineButton.setBounds(new Rectangle(buttonsXOffset * 2 + buttonsWidth, Constants.SCREEN_HEIGHT - buttonsYOffset, buttonsWidth, buttonsHeight));
		mineButton.setText("Mine");
		mineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BuildMode.getInstance().addBrick(BrickType.MineBrick);
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});
		
		JButton wrapperButton = new JButton();
		wrapperButton.setBounds(new Rectangle(buttonsXOffset * 3 + buttonsWidth * 2, Constants.SCREEN_HEIGHT - buttonsYOffset, buttonsWidth, buttonsHeight));
		wrapperButton.setText("Wrapper");
		wrapperButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BuildMode.getInstance().addBrick(BrickType.WrapperBrick);
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});
		
		JButton halfMetalButton = new JButton();
		halfMetalButton.setBounds(new Rectangle(buttonsXOffset * 4 + buttonsWidth * 3, Constants.SCREEN_HEIGHT - buttonsYOffset, buttonsWidth, buttonsHeight));
		halfMetalButton.setText("HalfMetal");
		halfMetalButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BuildMode.getInstance().addBrick(BrickType.HalfMetalBrick);
				Coordinator.getInstance().getFrame().requestFocus();
			}
		});
		
		add(simpleButton);
		add(mineButton);
		add(wrapperButton);
		add(halfMetalButton);
	
	}


	public static BuildModePanel getInstance() {
		// TODO Auto-generated method stub

		if (buildModePanel == null)
			buildModePanel = new BuildModePanel();

		return buildModePanel;

	}

	@Override
    public void bindPanelListener(JFrame frame) {
		frame.addMouseMotionListener(listener);
		frame.addMouseListener(listener);
		frame.addKeyListener(listener);
	}
	@Override
    public void removePanelListener(JFrame frame) {
		frame.removeMouseListener(listener);
		frame.removeMouseMotionListener(listener);
		frame.removeKeyListener(listener);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);

		for (int i = 0; i <= 100; i+=2) {
			g.setColor(Color.YELLOW);
			g.fillRect(i * Constants.SCREEN_WIDTH/100, Constants.BUILDMODE_GRID_INITIAL_Y + Constants.BUILDMODE_GRID_HEIGHT + 50,
					Constants.SCREEN_WIDTH/100, 2);

		}

		bricks.forEach(brick -> {
			if (brick != null)
				Drawer.getInstance().draw(g, brick);
		});

		Drawer.getInstance().draw(g, Paddle.getInstance());

		Brick movedBrick = BuildMode.getInstance().getMovedBrick();
		if (movedBrick != null)
			Drawer.getInstance().draw(g, movedBrick);

		repaint();

	}

	@SuppressWarnings("unused")
	private class PanelListener implements MouseListener, MouseMotionListener, KeyListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

			int mousePosX = e.getX();
			int mousePosY = e.getY() - Constants.JFRAME_TITLE_HEIGHT;

			BuildMode.getInstance().removeBrick(mousePosX, mousePosY);

		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

			int mousePosX = e.getX();
			int mousePosY = e.getY() - Constants.JFRAME_TITLE_HEIGHT;

			BuildMode.getInstance().updateDraggedBrick(mousePosX, mousePosY);

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

			int mousePosX = e.getX();
			int mousePosY = e.getY() - Constants.JFRAME_TITLE_HEIGHT;

			BuildMode.getInstance().brickDragged(mousePosX, mousePosY);

		}


		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				GameState.getInstance().setBrickList(bricks);
				Coordinator.getInstance().openScreen(ScreenEnum.PLAY);
			}
		}


		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub



		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

	}




}
