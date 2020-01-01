package domain;

public class GameTime implements DocumentSerializable {
	private static GameTime gameTime;
	private int startTime ;
	private int currentTimeInSeconds;
	
	
	private GameTime() {
		startTime = (int) (System.currentTimeMillis()/1000);
	}


	public int getStartTime() {
		return startTime;
	}


	public int getCurrentTime() {
		currentTimeInSeconds=(int) (System.currentTimeMillis()/1000);
		return currentTimeInSeconds;
	}

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }



	public static GameTime getInstance() {

		if(gameTime==null) {
			gameTime=new GameTime();
		}
		return gameTime;

	} 
	public int timeSinceStart() {

        return (getCurrentTime() - getStartTime()) == 0 ? 1 : (getCurrentTime() - getStartTime());
    }






}
