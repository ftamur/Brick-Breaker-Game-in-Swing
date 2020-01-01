package Services.Database.TxtFileDB;

import Services.Database.DBConstants;
import Services.Database.DatabaseManager;
import Services.Database.LoaderAdapter;
import Services.Database.SaverAdapter;
import domain.*;
import domain.alien.Alien;
import domain.brick.Brick;
import domain.powerups.PowerUp;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TxtFileDatabaseManager extends DatabaseManager {

    private LoaderAdapter loader = TxtFileLoaderAdapter.getInstance();
    private SaverAdapter saver = TxtFileSaverAdapter.getInstance();
    private static TxtFileDatabaseManager databaseManager;
    PrintWriter printWriter;

    private TxtFileDatabaseManager(){
        File file = new File(DBConstants.TXT_SAVED_FILES_FOLDER_PATH);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public void handleConnectionError(){
        System.out.println("No Save Files Directory");
    }


    public void saveGame(){
        String line = saveSingleObject(Player.getInstance()) +
                      saveListOfObjects(GameState.getInstance().getBallList()) +
                      saveListOfObjects(GameState.getInstance().getBrickList()) +
                      saveListOfObjects(GameState.getInstance().getAliensList()) +
                      saveListOfObjects(GameState.getInstance().getPowerUps()) +
                      saveSingleObject(Paddle.getInstance()) +
                      saveSingleObject(GameTime.getInstance()) +
                      saveSingleObject(Score.getInstance());

        writeToUserFile(line);
    }

    public String saveSingleObject(DocumentSerializable obj){
        return (String) saver.retrieveSaveEnconding(obj);
    }

    public <T extends DocumentSerializable> String saveListOfObjects(List<T> list){
        return (String) list.stream()
                        .filter(obj -> obj != null)
                        .map(obj -> saver.retrieveSaveEnconding(obj))
                .reduce("", (obj1, obj2) -> (String)obj1 + (String)obj2);
    }

    public boolean userHasSaveFile(){
        return getSingleLineFromSaveFile(3) != null;
    }

    public void loadGame(){
        ArrayList<Brick> bricksList = new ArrayList<Brick>();
        ArrayList<Ball> ballsList = new ArrayList<Ball>();
        ArrayList<Alien> aliensList = new ArrayList<Alien>();
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        List<String> lines = readUserFile();
        for (String line: lines){
            DocumentSerializable obj = loader.createInstance(line);
            if(obj == null)
                continue;
            if(Brick.class.isAssignableFrom(obj.getClass()))
                bricksList.add((Brick) obj);
            if(Ball.class.equals(obj.getClass()))
                ballsList.add((Ball) obj);
            if(Alien.class.isAssignableFrom(obj.getClass()))
                aliensList.add((Alien) obj);
            if(PowerUp.class.isAssignableFrom(obj.getClass()))
                powerUps.add((PowerUp) obj);
        }

        GameState.getInstance().setBrickListFromDB(bricksList);
        GameState.getInstance().setBallList(ballsList);
        GameState.getInstance().setAlienList(aliensList);
        GameState.getInstance().setPowerUps(powerUps);
        loader.createStickedBalls();
    }

    public static TxtFileDatabaseManager getInstance(){
        if (databaseManager == null)
            databaseManager =  new TxtFileDatabaseManager();
        return databaseManager;
    }


    public boolean connect(String username, String password){
        ArrayList<String> savedGameFiles = getSavedGameFiles();
        if(!savedGameFiles.contains(username+".txt")) {
            Player.getInstance().setCredentials(username,password);
            writeToUserFile((String) saver.retrieveSaveEnconding(Player.getInstance()));
            return true;
        }

        String [] firstLineWords = getSingleLineFromSaveFile(username,0).split(" ");
        String passwordSaved = firstLineWords[firstLineWords.length-1];
        if(passwordSaved.equals(password)){
            Player.getInstance().setCredentials(username,password);
            return true;
        }
        return false;
    }

    private String getSingleLineFromSaveFile(int lineNumber){
        return getSingleLineFromSaveFile(Player.getInstance().getUserName(), lineNumber);
    }

    private String getSingleLineFromSaveFile(String username,int lineNumber){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getSaveFilePath(username)));
            return (String) reader.lines().toArray()[lineNumber];
        } catch (FileNotFoundException e) {
            return null;
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }


    private void writeToUserFile(String line){
        try {
            printWriter = new PrintWriter(getSaveFile(Player.getInstance().getUserName()));
            printWriter.write(line);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        printWriter.close();
    }

    private List<String> readUserFile(){
        List<String> lines = null;
        try{
            lines =  Files.readAllLines(Paths.get(getSaveFilePath(Player.getInstance().getUserName())), StandardCharsets.UTF_8);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    private File getSaveFile(String username){
        return new File(getSaveFilePath(username));
    }

    private String getSaveFilePath(String username) {
        return DBConstants.TXT_SAVED_FILES_FOLDER_PATH + File.separator + username + ".txt";
    }

    private ArrayList<String> getSavedGameFiles() {

        File folder =  new File(DBConstants.TXT_SAVED_FILES_FOLDER_PATH);
        ArrayList<String> savedGameFiles = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            if (getFileExtension(fileEntry.getName()).equals("txt")){
                savedGameFiles.add(fileEntry.getName());
            }
        }
        return savedGameFiles;
    }

    private String getFileExtension(String fileName){
        return fileName.substring(fileName.length()-3);
    }

}
