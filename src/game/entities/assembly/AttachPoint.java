package game.entities.assembly;

import game.physics.Point;

public class AttachPoint extends Point{
    private double layer;
    private final boolean mirroring;
    private boolean mirrored;

    public AttachPoint(double x,double y, double layer, boolean mirroring){
        super(x,y);
        this.layer=layer;
        this.mirroring=mirroring;
    }

    public void copy(AttachPoint target){
        super.copy(target);
        this.layer=target.layer;
    }

    public void setOrigin(OriginPoint origin){super.setOrigin(origin);}
    public double getLayer(){return this.layer;}
    public void setMirrored(boolean mirrored){this.mirrored=mirrored;}
    public boolean isMirroring(){return this.mirroring^this.mirrored;}
}
