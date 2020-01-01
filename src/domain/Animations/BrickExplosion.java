package domain.Animations;

public class BrickExplosion {

    private int posX;
    private int posY;
    private int width;
    private int height;
    private int gifCounter;

    public BrickExplosion(int posX,int posY,int width,int height){
        this.posX=posX;
        this.posY=posY;
        this.width=width;
        this.height=height;
        gifCounter=0;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGifCounter() {
        return gifCounter;
    }

    public void setGifCounter(int gifCounter) {
        this.gifCounter = gifCounter;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
}

