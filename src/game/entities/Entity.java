package game.entities;

import game.Camera;
import game.WorldElement;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Entity extends WorldElement {
    protected double dX;
    protected double dY;
    protected double vX=0;
    protected double vY=0;
    protected double targetSpeed=0;
    protected double forcedSpeed=0;
    protected double dashSpeed=0;
    protected Rectangle2D hitBox;
    protected final double xOffset;
    protected final double yOffset;
    protected final double hitBoxWidth;
    protected final double hitBoxHeight;
    protected boolean facingRight;
    protected int team;
    protected ArrayList<Removal> removalListener=new ArrayList<>();
    protected int hitAWall=0;

    public Entity(int x, int y, int width, int height, int team, boolean isFacingRight, Rectangle2D hitBox, Camera cam, String spritePath) {
        super(x, y, width, height, cam, "entities\\"+spritePath);
        this.dX=x;
        this.dY=y;
        this.hitBox=hitBox;
        this.xOffset= hitBox.getMinX()-x;
        this.yOffset= hitBox.getMinY()-y;
        this.hitBoxWidth=hitBox.getWidth();
        this.hitBoxHeight=hitBox.getHeight();
        this.team=team;
        this.facingRight=isFacingRight;
    }

    public void update(long time){
        this.iv.setScaleX(this.facingRight?1:-1);
        this.vX=this.hitAWall!=0?0:this.targetSpeed+this.forcedSpeed+this.dashSpeed;
        this.dX+=this.vX;
        this.dY+=this.vY;
        this.speedUpdate();
        this.hitBox=new Rectangle2D(this.dX+this.xOffset,this.dY+this.yOffset,this.hitBoxWidth,this.hitBoxHeight);
        this.x=(int)this.dX;
        this.y=(int)this.dY;
        super.update(time);
    }

    protected void speedUpdate(){this.vX=this.targetSpeed+this.dashSpeed+this.forcedSpeed;}
    protected int getTeam(){return this.team;}
    public Rectangle2D getHitBox(){return this.hitBox;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public void addRemovalListener(Removal listener){this.removalListener.add(listener);}
    public boolean isFacingRight(){return this.facingRight;}
}
