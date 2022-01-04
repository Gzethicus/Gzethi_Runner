package game.entities.assembly;

import static java.lang.Math.PI;

public class AttachPoint implements Cloneable{
    private double x;
    private double y;
    private double layer;
    private boolean mirroring;
    private boolean mirrored;
    private OriginPoint origin;
    private double r;
    private double baseAngle;
    private double angle;

    public AttachPoint(double x,double y, double layer, boolean mirroring){
        this.x=x;
        this.y=y;
        this.layer=layer;
        this.mirroring=mirroring;
    }

    public void setOrigin(OriginPoint origin){
        this.origin=origin;
        this.r=Math.sqrt(Math.pow(this.x-origin.getLocalX(),2)+Math.pow(this.y-origin.getLocalY(),2));
        this.baseAngle =Math.atan2(this.y-origin.getLocalY(),this.x-origin.getLocalX());
    }

    public void copy(AttachPoint target){
        this.x=target.x;
        this.y=target.y;
        this.layer=target.layer;
        this.mirroring=target.mirroring;
        this.r=target.r;
        this.baseAngle=target.baseAngle;
    }

    public AttachPoint clone(){
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        AttachPoint clone=new AttachPoint(this.x,this.y,this.layer,this.mirroring);
        if(this.origin!=null){clone.setOrigin(this.origin);}
        clone.setAngle(this.angle);
        return clone;
    }

    public void setAngle(double angle){this.angle=angle;}
    public double getAngle(){return this.angle;}
    public OriginPoint getOrigin(){return this.origin;}
    public double getX(){return this.r*Math.cos(this.baseAngle+this.angle*PI/180);}
    public double getY(){return this.r*Math.sin(this.baseAngle+this.angle*PI/180);}
    public double getLayer(){return this.layer;}
    public void setMirrored(boolean mirrored){this.mirrored=mirrored;}
    public boolean isMirroring(){return this.mirroring^this.mirrored;}
}
