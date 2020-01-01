package domain.powerups.timedPowerups;

import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimedPowerUp extends PowerUp {
    private int duration;

    public TimedPowerUp(PowerUpEnum type, int durationInSeconds) {
        super(type);
        this.duration = durationInSeconds;
    }

    public void activate(){
        int stopDelay = duration*1000; //milliseconds

        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                deactivate();
            }
        };


        Timer stopTimer = new Timer();
        stopTimer.schedule(stopTask,stopDelay);
    }

    public abstract void deactivate();

    public abstract boolean isInUse();

}
