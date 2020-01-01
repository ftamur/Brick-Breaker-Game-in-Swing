package domain.Controllers;

import Services.Database.DatabaseEnum;
import Services.Database.DatabaseStrategy;

public class LoginScreenController {
    private static LoginScreenController loginScreenController;

    private LoginScreenController(){

    }

    public boolean connect(String username, String password){
       return DatabaseStrategy.getInstance().connect(username,password);
    }

    public void changeDatabase(int value){
        DatabaseEnum chosenDB = value == 1 ? DatabaseEnum.MONGO_DB : DatabaseEnum.TXT_DB;
        DatabaseStrategy.getInstance().setCurrentDatabaseType(chosenDB);
    }

    public static LoginScreenController getInstance() {
        // TODO Auto-generated method stub

        if (loginScreenController == null)
            loginScreenController = new LoginScreenController();

        return loginScreenController;

    }
}
