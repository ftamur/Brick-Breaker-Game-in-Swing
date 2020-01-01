package domain.powerups;

import domain.DocumentSerializable;

public abstract class PowerUp implements DocumentSerializable {

    public PowerUpEnum type;

    public PowerUp(PowerUpEnum type) {
        this.type = type;
    }
    public abstract void activate();


    public PowerUpEnum getType() {
        return type;
    }
}
