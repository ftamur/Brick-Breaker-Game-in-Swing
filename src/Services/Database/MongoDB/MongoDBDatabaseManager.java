package Services.Database.MongoDB;

import Services.Database.*;
import com.mongodb.*;
import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;
import domain.*;
import domain.alien.Alien;
import domain.brick.Brick;
import domain.powerups.PowerUp;

import java.util.ArrayList;
import java.util.List;

public class MongoDBDatabaseManager extends DatabaseManager implements ServerMonitorListener {

    private static MongoDBDatabaseManager databaseManager;

    DB db;
    private LoaderAdapter loader = MongoDBLoaderAdapter.getInstance();
    private SaverAdapter saver = MongoDBSaverAdapter.getInstance();

    private DBCollection usersCollection;


    private MongoDBDatabaseManager(){
        connectDatabase();
    }

    private void connectDatabase(){
        MongoClientOptions clientOptions = new MongoClientOptions.Builder()
                .addServerMonitorListener(this).connectTimeout(3000).build();
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017),clientOptions);
        db = mongoClient.getDB(DBConstants.MONGO_DB_NAME);
        usersCollection = db.getCollection("Users");
    }

    public void handleConnectionError(){
        DatabaseStrategy.getInstance().setCurrentDatabaseType(DatabaseEnum.TXT_DB);
        System.out.println("MongoDB is unavailable switched to Text File DB");
    }

    public boolean connect(String username, String password){
        DBObject userDocument = getUserDocument(username);
        if (userDocument == null) {
            createUser(username, password);
            Player.getInstance().setCredentials(username,password);
            return true;
        }
        String passwordSaved = (String) userDocument.get("password");
        if(passwordSaved.equals(password)){
            Player.getInstance().setCredentials(username,password);
            return true;
        }
        return false;
    }

    public boolean userHasSaveFile(){
        BasicDBObject userDoc = (BasicDBObject) db.getCollection("Users").findOne(new BasicDBObject()
                .append("username",Player.getInstance().getUserName()));
        DBCollection savedFile = db.getCollection(userDoc.get("_id").toString());
        return savedFile.find().count() > 1;
    }

    public void loadGame() {
        String username = Player.getInstance().getUserName();

        DBCollection savedGameCollection = getUserSavedGameCollection(username);
        DBCursor cursor = savedGameCollection.find();
        ArrayList<Ball> balls = new ArrayList<>();
        ArrayList<Brick> bricks = new ArrayList<>();
        ArrayList<Alien> aliens = new ArrayList<>();
        ArrayList<PowerUp> powerUps = new ArrayList<>();
        while(cursor.hasNext()){
            DBObject object = cursor.next();
            DocumentSerializable loadedObject = loader.createInstance(object);
            if(loadedObject == null)
                continue;
            if (Ball.class.equals(loadedObject.getClass()))
                balls.add((Ball) loadedObject);
            else if(Brick.class.isAssignableFrom(loadedObject.getClass()))
                bricks.add((Brick) loadedObject);
            else if(Alien.class.isAssignableFrom(loadedObject.getClass()))
                aliens.add((Alien) loadedObject);
            else if(PowerUp.class.isAssignableFrom(loadedObject.getClass()))
                powerUps.add((PowerUp) loadedObject);
        }

        GameState.getInstance().setBallList(balls);
        GameState.getInstance().setBrickListFromDB(bricks);
        GameState.getInstance().setAlienList(aliens);
        GameState.getInstance().setPowerUps(powerUps);
        loader.createStickedBalls();

    }

    public void saveGame() {
        deleteAllInCollection(getUserSavedGameCollection(Player.getInstance().getUserName()));
        savePhysicalObjectList(GameState.getInstance().getBallList());
        savePhysicalObjectList(GameState.getInstance().getBrickList());
        savePhysicalObjectList(GameState.getInstance().getAliensList());
        savePhysicalObjectList(GameState.getInstance().getPowerUps());
        saveSingleton(Score.getInstance());
        saveSingleton(GameTime.getInstance());
        saveSingleton(Paddle.getInstance());
        saveSingleton(Player.getInstance());
    }


    private DBObject getUserDocument(String username){
        return usersCollection.findOne(new BasicDBObject().append("username",username));
    }

    private DBCollection getUserSavedGameCollection(String username){
        DBObject userDocument = getUserDocument(username);
        return db.getCollection(userDocument.get("_id").toString());
    }

    private void createUser(String username, String password){
        BasicDBObject newUserDocument = new BasicDBObject().append("username", username).append("password", password);
        usersCollection.insert(newUserDocument);
        DBCollection collection = db.createCollection(newUserDocument.get("_id").toString(),null);
        collection.insert((DBObject) saver.retrieveSaveEnconding(Player.getInstance()));
    }



    private <T extends DocumentSerializable> void savePhysicalObjectList(List<T> list) {

        if (list.isEmpty())
            return;
        DBCollection collection = getUserSavedGameCollection(Player.getInstance().getUserName());

        list.stream().filter((el) -> el != null).forEach((el) -> collection.save((DBObject) saver.retrieveSaveEnconding(el)));
    }

    private <T extends DocumentSerializable> void saveSingleton(T singleton) {
        getUserSavedGameCollection(Player.getInstance().getUserName()).save((DBObject) saver.retrieveSaveEnconding(singleton));
    }



    private void deleteAllInCollection(DBCollection collection){

        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            collection.remove(cursor.next());
        }
    }


    public static MongoDBDatabaseManager getInstance(){
        if (databaseManager == null)
            databaseManager =  new MongoDBDatabaseManager();
        return databaseManager;
    }


    @Override
    public void serverHearbeatStarted(ServerHeartbeatStartedEvent serverHeartbeatStartedEvent) {
        // Ping Started
    }

    @Override
    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent serverHeartbeatSucceededEvent) {
        // Ping Succeed, Connected to server

    }

    @Override
    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent serverHeartbeatFailedEvent) {
        // Ping failed, server down or connection lost
        handleConnectionError();
    }


}
