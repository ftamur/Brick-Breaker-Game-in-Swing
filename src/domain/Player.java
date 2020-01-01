package domain;

public class Player implements DocumentSerializable {

	private String userName;
	private String password;
	private int numLives = 3;

	private static Player player = new Player();

	private Player(){}

	public static Player getInstance(){return player;}


	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}



	public void setCredentials(String username, String password){
		this.userName = username;
		this.password = password;
	}




	public int getNumLives() {
		return numLives;
	}

	public void setNumLives(int numLives) {
		this.numLives = numLives;
	}

}
