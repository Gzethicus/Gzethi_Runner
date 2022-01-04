package game;

import game.entities.Entity;
import javafx.geometry.Point3D;
import javafx.scene.layout.Pane;

import static java.lang.Math.*;

public class Camera {
    private double x;
    private double y;
    private double vX=0;
    private double vY=0;
    private Entity target;
    private final Pane worldPane;

    private long rotationStarted;
    private long rotationDuration;
    private double rotationTarget;
    private double lastRotationValue;
    private double continuousRotationSpeed;


    public Camera(int x, int y, Pane worldPane){
        this.x=x;
        this.y=y;
        this.worldPane=worldPane;
    }

    public void update(long time) {
        double targetX=(this.target.getHitBox().getMinX()+this.target.getHitBox().getMaxX())/2;
        double targetY=(this.target.getHitBox().getMinY()+this.target.getHitBox().getMaxY())/2;

        //horizontal tracking
        double a=50*((2*targetX+GameScene.getMouseX())/3-this.x)-30*this.vX;
        this.vX=this.vX+a*0.01;
        this.x=(this.x+vX*0.1);

        //vertical tracking
        a=50*((2*targetY+GameScene.getMouseY())/3-this.y)-30*this.vY;
        this.vY=this.vY+a*0.01;
        this.y=(this.y+vY*0.1);

        if(worldPane.getRotate()!=this.rotationTarget|this.continuousRotationSpeed!=0){
            if(time-this.rotationStarted<this.rotationDuration) {
                worldPane.setRotate(this.lastRotationValue+(this.rotationTarget - this.lastRotationValue)*pow(sin(((time-this.rotationStarted)*PI)/(this.rotationDuration*2.)),2));
            }else{
                worldPane.setRotate(this.rotationTarget+(time-this.rotationStarted-this.rotationDuration)*this.continuousRotationSpeed);
            }
        }
        double angle=worldPane.getRotate()*PI/180;
        double leftBound=(this.x-worldPane.getWidth()/2)*cos(angle)-(this.y-worldPane.getHeight()/2)*sin(angle);
        double topBound=(this.y-worldPane.getHeight()/2)*cos(angle)+(this.x-worldPane.getWidth()/2)*sin(angle);
        this.worldPane.setTranslateX(-leftBound);
        this.worldPane.setTranslateY(-topBound);
    }

    public void rotate(long time, double rotation,long transitionTime){
        this.lastRotationValue=(worldPane.getRotate()+180.)%360.-180.;
        this.lastRotationValue+=this.lastRotationValue<=-180.?360.:0.;
        this.rotationTarget=(rotation-this.lastRotationValue+180.)%360.+this.lastRotationValue-180.;
        this.rotationStarted=time;
        this.rotationDuration=transitionTime;
    }

    public void setContinuousRotation(double speed){this.continuousRotationSpeed=speed/1000;}

    public double getX(){return(x);}
    public double getY(){return(y);}
    public void setTarget(Entity target){this.target=target;}
}
