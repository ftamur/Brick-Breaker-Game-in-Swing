package domain.Controllers;

import Services.Database.DatabaseStrategy;
import domain.GameExpert;
import gui.Coordinator;
import gui.ScreenEnum;

public class MainScreenController {
    private static MainScreenController mainScreenController;


    private MainScreenController(){}
    public void newGame(){
        Coordinator.getInstance().openScreen(ScreenEnum.INPUT_DIALOGUE);
    }

    public void loadGame(){
        DatabaseStrategy.getInstance().loadGame();
        Coordinator.getInstance().openScreen(ScreenEnum.PLAY);
        GameExpert.getInstance().pause();
    }

    // TODO: IMPLEMENT THIS
    public void help(){
    	 Coordinator.getInstance().openScreen(ScreenEnum.HELP);
    }

    public static MainScreenController getInstance() {
        // TODO Auto-generated method stub

        if (mainScreenController == null)
            mainScreenController = new MainScreenController();

        return mainScreenController;

    } 
}
