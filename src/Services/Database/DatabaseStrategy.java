package Services.Database;

import Services.Database.MongoDB.MongoDBDatabaseManager;
import Services.Database.TxtFileDB.TxtFileDatabaseManager;

import java.util.Scanner;

public class DatabaseStrategy{
    private static DatabaseStrategy databaseStrategy;

    private static Scanner scanner = new Scanner(System.in);

    private DatabaseEnum currentDatabaseType;
    private DatabaseManager currentDatabase;


    private DatabaseStrategy() {
        setCurrentDatabaseType(DatabaseEnum.MONGO_DB);
    }


    public void saveGame(){
        currentDatabase.saveGame();
    }
    public void loadGame(){ currentDatabase.loadGame(); }
    public boolean connect(String username, String password){return currentDatabase.connect(username, password);}
    public boolean userHasSaveFile(){return currentDatabase.userHasSaveFile();}

    public static DatabaseStrategy getDatabaseStrategy() {
        return databaseStrategy;
    }

    public static void setDatabaseStrategy(DatabaseStrategy databaseStrategy) {
        DatabaseStrategy.databaseStrategy = databaseStrategy;
    }

    public synchronized void setCurrentDatabaseType(DatabaseEnum databaseType) {
        if(currentDatabaseType == databaseType){
            return;
        }
        switch (databaseType) {
            case TXT_DB:
                mountMySQL();
                break;
            case MONGO_DB:
                mountMongoDB();
                break;
        }
        this.currentDatabaseType = databaseType;
    }

    private void mountMySQL(){
        currentDatabase = TxtFileDatabaseManager.getInstance();
    }

    private void mountMongoDB(){
        currentDatabase = MongoDBDatabaseManager.getInstance();
    }

    public DatabaseEnum getCurrentDatabaseType() {
        return currentDatabaseType;
    }

    public static DatabaseStrategy getInstance(){
        if(databaseStrategy == null)
            databaseStrategy = new DatabaseStrategy();
        return databaseStrategy;
    }

}
