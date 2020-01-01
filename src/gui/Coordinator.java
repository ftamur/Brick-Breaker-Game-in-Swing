package gui;

import Services.Constants;
import domain.GameExpert;
import gui.Panels.*;

import javax.swing.*;

public class Coordinator {
    private static Coordinator ourInstance;
    private MainFrame frame = MainFrame.getInstance();
    public ScreenEnum currentScreen;
    private static final ScreenEnum initialPage = ScreenEnum.INPUT_DIALOGUE;

    public static Coordinator getInstance() {
        if (ourInstance == null)
            ourInstance = new Coordinator();
        return ourInstance;
    }

    private Coordinator() {
    }

    public void start(){
        frame.setVisible(true);
        frame.setTitle("The Hateful 6");
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openScreen(ScreenEnum.LOGIN);
    }


    public void openScreen(ScreenEnum screen){
        cleanUp();
        GamePanel panel = getPanel(screen);
        panel.setBounds(0,0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        frame.setContentPane(panel);
        frame.revalidate();
        panel.bindPanelListener(frame);
        currentScreen = screen;
        if (screen == ScreenEnum.PLAY) {
            GameExpert.getInstance().startGame();
            PlayPanel.getInstance().startPainting();
        }
    }

    private void cleanUp(){
        if (currentScreen == null)
            return;
        GamePanel oldPanel = getPanel(currentScreen);
        oldPanel.removePanelListener(frame);
        frame.remove(oldPanel);

    }



    private GamePanel getPanel(ScreenEnum screen){
        switch (screen){
            case PLAY:
                frame.setBounds(Constants.SCREEN_X, Constants.SCREEN_Y,
                        Constants.SCREEN_WIDTH + 6, Constants.SCREEN_HEIGHT + Constants.JFRAME_TITLE_HEIGHT);

                return PlayPanel.getInstance();
            case BUILD:
                frame.setBounds(Constants.SCREEN_X, Constants.SCREEN_Y,
                        Constants.SCREEN_WIDTH + 6, Constants.SCREEN_HEIGHT + Constants.JFRAME_TITLE_HEIGHT);

                return BuildModePanel.getInstance();
            case INPUT_DIALOGUE:
            	frame.setBounds(Constants.BEFORE_FULL_SCREEN_X, Constants.BEFORE_FULL_SCREEN_Y,
            			Constants.BEFORE_FULL_SCREEN_WIDTH, Constants.BEFORE_FULL_SCREEN_HEIGHT);

            	return new InputPanel();
            case LOGIN:
                frame.setBounds(Constants.BEFORE_FULL_SCREEN_X, Constants.BEFORE_FULL_SCREEN_Y,
                		Constants.BEFORE_FULL_SCREEN_WIDTH, Constants.BEFORE_FULL_SCREEN_HEIGHT);

            	return new LoginPanel();
            case MAIN:
            	return new MainPanel();
            case GAME_OVER:
            	frame.setBounds(Constants.BEFORE_FULL_SCREEN_X, Constants.BEFORE_FULL_SCREEN_Y,
            			Constants.BEFORE_FULL_SCREEN_WIDTH, Constants.BEFORE_FULL_SCREEN_HEIGHT);
                return GameOverPanel.getInstance();

            case HELP:
            	frame.setBounds(Constants.BEFORE_FULL_SCREEN_X, Constants.BEFORE_FULL_SCREEN_Y,
            			Constants.BEFORE_FULL_SCREEN_WIDTH, Constants.BEFORE_FULL_SCREEN_HEIGHT);
                return new HelpPanel();

        }
        return null;
    }


    public MainFrame getFrame() {
        return frame;
    }

}
