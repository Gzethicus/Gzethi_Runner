package game.environment;

import game.AnimatedSprite;
import game.WorldElement;
import javafx.geometry.Rectangle2D;

import static java.lang.Math.PI;


public class Walkable extends WorldElement{
    private final Rectangle2D hitBox;
    private final boolean isSolid;
    private double forcedSpeed;
    private double cosA=1;
    private double sinA=0;

    public Walkable(double x, double y, int width, int height, boolean isSolid, double forcedSpeed, AnimatedSprite sprite){
        super(x,y, sprite);
        this.hitBox=new Rectangle2D(x,y,width,height);
        this.isSolid=isSolid;
        this.forcedSpeed=forcedSpeed;
        this.rotateProperty().addListener((obs,oldVal,newVal)-> {
            this.cosA=Math.cos(newVal.doubleValue()*PI/180);
            this.sinA=Math.sin(newVal.doubleValue()*PI/180);});
    }
    public Walkable(double x, double y, int width, int height, boolean isSolid, AnimatedSprite sprite){
        super(x,y, sprite);
        this.hitBox=new Rectangle2D(x,y,width,height);
        this.isSolid=isSolid;
        this.forcedSpeed=0;
        this.rotateProperty().addListener((obs,oldVal,newVal)-> {
            this.cosA=Math.cos(newVal.doubleValue()*PI/180);
            this.sinA=Math.sin(newVal.doubleValue()*PI/180);});
    }

    public double getTop(double x, double y){
        return 0;
    }

    public boolean isBelow(Rectangle2D hitBox){return hitBox.getMaxY()<=this.hitBox.getMinY()+20;}
    public boolean isAbove(Rectangle2D hitBox){return hitBox.getMinY()>=this.hitBox.getMaxY();}
    public boolean isLeft(Rectangle2D hitBox){return hitBox.getMinX()>=this.hitBox.getMaxX() & hitBox.getMinY()<this.hitBox.getMaxY() & hitBox.getMaxY()>this.hitBox.getMinY()+20;}
    public boolean isRight(Rectangle2D hitBox){return hitBox.getMaxX()<=this.hitBox.getMinX() & hitBox.getMinY()<this.hitBox.getMaxY() & hitBox.getMaxY()>this.hitBox.getMinY()+20;}

    public double getForcedSpeed(){return this.forcedSpeed;}
    public void setForcedSpeed(double forcedSpeed){this.forcedSpeed=forcedSpeed;}
    public boolean intersects(Rectangle2D hitBox){return hitBox.intersects(this.hitBox);}
    public boolean isSolid(){return this.isSolid;}
    public Rectangle2D getHitBox(){return hitBox;}
}
