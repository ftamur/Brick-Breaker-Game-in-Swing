package Services.Database;

public abstract class DatabaseManager {

    private LoaderAdapter loader;
    private SaverAdapter saver;

    public abstract void saveGame();
    public abstract void loadGame();
    public abstract boolean connect(String username, String password);
    public abstract boolean userHasSaveFile();
    public abstract void handleConnectionError();
    
}
