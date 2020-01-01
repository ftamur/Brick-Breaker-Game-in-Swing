package Services.Database.TxtFileDB;

import Services.Database.SaverAdapter;
import domain.*;
import domain.alien.*;
import domain.brick.*;
import domain.powerups.PowerUp;
import domain.powerups.PowerUpEnum;

public class TxtFileSaverAdapter extends SaverAdapter {

    private static TxtFileSaverAdapter mongoDBSaverAdapter;
    private TxtFileSaverAdapter(){}

    public static TxtFileSaverAdapter getInstance(){
        if (mongoDBSaverAdapter == null)
            mongoDBSaverAdapter= new TxtFileSaverAdapter();
        return mongoDBSaverAdapter;
    }

    private String createInitialDocument(DocumentSerializable obj){

        return getClassTypeString(obj.getClass());
    }


    private String physicalObjectToDocument(PhysicalObject obj){
        String line = createInitialDocument(obj)+
                " " + obj.getPosX() +
                " " + obj.getPosY() +
                " " + obj.getVelocityX() +
                " " + obj.getVelocityY() +
                " " + obj.getWidth() +
                " " + obj.getHeight();
        return line;
    }

    @Override
    protected String paddleToDocument(Paddle paddle) {
        String line = physicalObjectToDocument(paddle) +
                " " + paddle.getRotateAngle() +
                " " + paddle.isSticky() + "\n";
        return line;
    }

    @Override
    protected String playerToDocument(Player player){
        String line = createInitialDocument(player)+
                " " + player.getUserName() +
                " " + player.getPassword() + "\n";
        return line;
    }

    @Override
    protected Object powerUpToDocument(PowerUp powerUp) {
        String line = createInitialDocument(powerUp)+
                " " + PowerUpEnum.toInt(powerUp.getType()) + "\n";
        return line;
    }


    @Override
    protected String scoreToDocument(Score score) {
        String line = createInitialDocument(score)+
                " " + score.getCurrentScore() +
                " " + score.getBrokenBrickCount() +
                " " + score.getTotalBrickCount() +"\n";
        return line;
    }



    private String _brickToDocument(Brick brick) {
        String line = physicalObjectToDocument(brick) + " " + BrickType.toInt(brick.getType());
        return line;
    }

    @Override
    protected String simpleBrickToDocument(SimpleBrick brick){
        String line = _brickToDocument(brick) + "\n";
        return line;
    }

    @Override
    protected String mineBrickToDocument(MineBrick brick){
        String line = _brickToDocument(brick) + "\n";
        return line;
    }

    @Override
    protected String wrapperBrickToDocument(WrapperBrick brick){
        String line = _brickToDocument(brick) + "\n";
        return line;
    }

    @Override
    protected String halfMetalBrickToDocument(HalfMetalBrick brick){
        String line = _brickToDocument(brick) + "\n";
        return line;
    }

    private String _alienToDocument(Alien alien){
        String line = physicalObjectToDocument(alien) + " " + AlienType.toInt(alien.getType());
        return line;
    }

    @Override
    protected String cooperativeAlienToDocument(CooperativeAlien alien) {
        String line = _alienToDocument(alien) + "\n";
        return line;
    }

    @Override
    protected String drunkAlienToDocument(DrunkAlien alien) {
        String line = _alienToDocument(alien) + "\n";
        return line;
    }

    @Override
    protected String protectingAlienToDocument(ProtectingAlien alien) {
        String line = _alienToDocument(alien) + "\n";
        return line;
    }

    @Override
    protected String repairingAlienToDocument(RepairingAlien alien) {
        String line = _alienToDocument(alien) + "\n";
        return line;
    }

    @Override
    protected String timeToDocument(GameTime time) {
        String line = createInitialDocument(time)+
                " " + time.getStartTime() +
                " " + time.getCurrentTime() + "\n";
        return line;
    }

    @Override
    protected String ballToDocument(Ball ball){
        String line = physicalObjectToDocument(ball)+
                " " + ball.isFire() +
                " " + ball.isChemical() +
                " " + ball.isSticked()+ "\n";
        return line;
    }
}



