package domain.powerups;

import Services.Constants;
import domain.PhysicalObject;
import domain.PhysicalObjectType;

public class PowerUpObject extends PhysicalObject {

    private PowerUp powerUp;

    public PowerUpObject(int posX, int posY, PowerUp powerUp){
        super(posX,posY,0,Constants.POWERUP_OBJECT_VELOCITY_Y,
                Constants.POWERUP_OBJECT_EDGE_LENGTH, Constants.POWERUP_OBJECT_EDGE_LENGTH,
                true, PhysicalObjectType.POWERUP);
        this.powerUp = powerUp;
    }
    public PowerUp getPowerUp(){return powerUp;}
}
