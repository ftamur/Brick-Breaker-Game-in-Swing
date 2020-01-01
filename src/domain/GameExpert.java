package domain;


import java.util.concurrent.Semaphore;

import Services.Constants;

public class GameExpert {
	
	private Thread gameUpdateThread;
	private static GameExpert singleton;
	private Semaphore semaphore = new Semaphore(0);

	private GameExpert() {

		gameUpdateThread = new Thread(() -> {
			while (true) {
				if (GameState.getInstance().getBallList().size() != 0){
					GameState.getInstance().nextState();
				}else{
					GameState.getInstance().fail();
				}

				semaphore.release();

				try {
					Thread.sleep(Constants.NEXT_STATE_INTERVAL);
				} catch (InterruptedException e) {
                    while (true) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e2) {
                            break;
                        }
                    }
				}
			}
		});
	
	}


	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void startGame(){
		gameUpdateThread.start();
	}
	public static GameExpert getInstance(){
		if (singleton == null)
			singleton = new GameExpert();
		
		return singleton;
	}



    public void pause() {
        gameUpdateThread.interrupt();
    }
    public void resume() {
        gameUpdateThread.run();
    }
    public Thread.State getThreadState() {
        return gameUpdateThread.getState();
    }

	
}
