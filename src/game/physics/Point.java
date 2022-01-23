package game.physics;

import game.entities.assembly.OriginPoint;

import static java.lang.Math.PI;

public class Point implements Cloneable{
    private double x;
    private double y;
    private double scaleX=1;
    private double scaleY=1;
    private double r;
    private double baseAngle;
    private double angle;
    private OriginPoint origin;

    public Point(double x, double y){
        this.x=x;
        this.y=y;
        this.r=Math.sqrt(x*x+y*y);
        this.baseAngle=Math.atan2(this.y,this.x);
    }

    private Point(Point original){
        this.x=original.x;
        this.y=original.y;
        this.scaleX=original.scaleX;
        this.scaleY=original.scaleY;
        this.r=original.r;
        this.baseAngle=original.baseAngle;
        this.angle=original.angle;
        this.origin=original.origin;
    }

    protected void setOrigin(OriginPoint origin){
        this.origin=origin;
        Point pOrigin=origin;
        double x=(this.x-pOrigin.x)*this.scaleX;
        double y=(this.y-pOrigin.y)*this.scaleY;
        this.r=Math.sqrt(x*x+y*y);
        this.baseAngle=Math.atan2(y,x);
    }

    protected void copy(Point target){
        this.x=target.x;
        this.y=target.y;
        this.scaleX=target.scaleX;
        this.scaleY=target.scaleY;
        this.r=target.r;
        this.baseAngle=target.baseAngle;
    }

    public void setScaleX(double x){
        this.scaleX=x;
        if(this.origin!=null)this.setOrigin(this.origin);
    }
    public void setScaleY(double y){
        this.scaleY=y;
        if(this.origin!=null)this.setOrigin(this.origin);
    }

    public Point clone(){return new Point(this);}
    public void setAngle(double angle){this.angle=angle;}
    public double getAngle(){return this.angle;}
    public OriginPoint getOrigin(){return this.origin;}
    protected double getLocalX(){return this.x;}
    protected double getLocalY(){return this.y;}
    public double getX(){return this.r*Math.cos(this.baseAngle+this.angle*PI/180);}
    public double getY(){return this.r*Math.sin(this.baseAngle+this.angle*PI/180);}
    public double getGlobalX(){return ((this.origin==null)?0:this.origin.getGlobalX()+(this.origin.getOwner().getFacingRight()?1:-1)*this.getX());}
    public double getGlobalY(){return ((this.origin==null)?0:this.origin.getGlobalY()+this.getY());}

    @Override
    public String toString(){return "Point{x="+this.getGlobalX()+", y="+this.getGlobalY()+'}';}
}
