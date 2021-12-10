package game.environment;

import game.Camera;
import game.WorldElement;
import javafx.geometry.Rectangle2D;


public class Walkable extends WorldElement{
    private final Rectangle2D hitBox;
    private final boolean isSolid;
    private double forcedSpeed;

    public Walkable(int x, int y, int width, int height, boolean isSolid, double forcedSpeed, Camera cam, String spriteName){
        super(x,y,width,height,cam,"walkable\\"+spriteName);
        this.hitBox=new Rectangle2D(x,y,width,height);
        this.isSolid=isSolid;
        this.forcedSpeed=forcedSpeed;
    }
    public Walkable(int x, int y, int width, int height, boolean isSolid, Camera cam, String spriteName){
        super(x,y,width,height,cam,"walkable\\"+spriteName);
        this.hitBox=new Rectangle2D(x,y,width,height);
        this.isSolid=isSolid;
        this.forcedSpeed=0;
    }

    public boolean isBelow(Rectangle2D hitBox){
        return hitBox.getMaxY()<=this.hitBox.getMinY()+20;
    }

    public boolean isAbove(Rectangle2D hitBox){
        return hitBox.getMinY()>=this.hitBox.getMaxY();
    }

    public boolean isLeft(Rectangle2D hitBox){
        return hitBox.getMinX()>=this.hitBox.getMaxX() & hitBox.getMinY()<this.hitBox.getMaxY() & hitBox.getMaxY()>this.hitBox.getMinY()+20;
    }

    public boolean isRight(Rectangle2D hitBox){
        return hitBox.getMaxX()<=this.hitBox.getMinX() & hitBox.getMinY()<this.hitBox.getMaxY() & hitBox.getMaxY()>this.hitBox.getMinY()+20;
    }

    public double getForcedSpeed(){return this.forcedSpeed;}
    public void setForcedSpeed(double forcedSpeed){this.forcedSpeed=forcedSpeed;}
    public boolean intersects(Rectangle2D hitBox){return hitBox.intersects(this.hitBox);}
    public boolean isSolid(){return this.isSolid;}
    public Rectangle2D getHitBox(){return hitBox;}
}
