package game.entities;

import game.AnimatedSprite;
import game.GameScene;
import game.WorldElement;
import game.environment.rooms.Room;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;

import java.util.ArrayList;

public class Entity extends WorldElement {
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
    private long orientationLockEnd;
    private boolean locked;
    private boolean desiredOrientation;
    private boolean facingRight;
    protected int team;
    protected ArrayList<Removal> removalListener=new ArrayList<>();
    protected int hitAWall=0;
    protected Room inRoom;

    public Entity(double x, double y, Room room, int team, boolean isFacingRight, Rectangle2D hitBox, Node sprite) {
        super(x, y, sprite);
        this.hitBox=hitBox;
        this.xOffset= hitBox.getMinX()-x;
        this.yOffset= hitBox.getMinY()-y;
        this.hitBoxWidth=hitBox.getWidth();
        this.hitBoxHeight=hitBox.getHeight();
        this.team=team;
        this.facingRight=isFacingRight;
        this.desiredOrientation=isFacingRight;
        this.inRoom=room;
    }

    public void update(long time){
        double dt=this.lastUpdated==0?0:(time-this.lastUpdated)/1000.;
        this.lastUpdated=time;
        this.locked=(time<this.orientationLockEnd);
        if(!locked){this.facingRight=this.desiredOrientation;}
        if(this.sprite instanceof AnimatedSprite)this.sprite.setScaleX(this.facingRight?1:-1);
        this.vX=this.hitAWall!=0?0:this.targetSpeed+this.forcedSpeed+this.dashSpeed;
        this.x+=this.vX*dt*GameScene.getPixelToMeter();
        this.y+=this.vY*dt*GameScene.getPixelToMeter();
        this.updateHitBox();
        if(!this.inRoom.contains(new Point2D(this.getCenterX(),this.getCenterY()))){this.inRoom=this.inRoom.leave(this);}
        this.speedUpdate();
        super.update(time);
    }

    public void setFacingRight(boolean facingRight){
        this.desiredOrientation=facingRight;
        if(!this.locked){this.facingRight=facingRight;}
    }

    public void lockOrient(long duration, long time){
        this.locked=true;
        this.orientationLockEnd=time+duration;
    }

    public boolean getFacingRight(){return this.facingRight;}
    protected void speedUpdate(){this.vX=this.targetSpeed+this.dashSpeed+this.forcedSpeed;}
    protected void updateHitBox(){this.hitBox=new Rectangle2D(this.x+this.xOffset,this.y+this.yOffset,this.hitBoxWidth,this.hitBoxHeight);}
    public int getTeam(){return this.team;}
    public Room getRoom(){return this.inRoom;}
    public Rectangle2D getHitBox(){return this.hitBox;}
    public void addRemovalListener(Removal listener){this.removalListener.add(listener);}
    public double getCenterX(){return (this.hitBox.getMinX()+this.hitBox.getMaxX())/2.;}
    public double getCenterY(){return (this.hitBox.getMinY()+this.hitBox.getMaxY())/2.;}
}
