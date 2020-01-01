package domain;

public class Score implements IObserver, DocumentSerializable {
	
	private static Score score;

	private int currentScore;
	private int brokenBrickCount;
	private int totalBrickCount;

	private Score() {
		
		this.currentScore = 0;
		this.brokenBrickCount = 0;
		totalBrickCount = 0;
		
	} 

	public static Score getInstance() {
		if(score==null) score=new Score();

		return score;
	} 


	@Override
	public void update() {

		currentScore +=  300 / GameTime.getInstance().timeSinceStart();
		++brokenBrickCount;

	}

    public void setScore(int score) {
        this.currentScore = score;
    }

	public int getCurrentScore() {
		return currentScore;
	}
	
	public int getBrokenBrickCount() {
		return brokenBrickCount;
	}
	
	public void setBrokenBrickCount(int brokenBrickCount) {
		this.brokenBrickCount = brokenBrickCount;
	}

	public int getTotalBrickCount() {
		return totalBrickCount;
	}

	public void setTotalBrickCount(int totalBrickCount) {
		this.totalBrickCount = totalBrickCount;
	}
 


}
