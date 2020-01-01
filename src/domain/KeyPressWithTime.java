package domain;

public class KeyPressWithTime {

	private Direction direction;
	private long time;

	public KeyPressWithTime(Direction direction) {
		this.direction = direction;
		time = System.currentTimeMillis();
	}


	public Direction getDirection() {
		return this.direction;
	}



	
	public long getTime() {
		return time;
	}
	



}
