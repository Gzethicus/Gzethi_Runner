package game.entities.assembly;

import game.WorldElement;
import game.physics.Point;

public class OriginPoint extends Point{
    private AttachPoint linkedTo;
    private WorldElement owner;
    
    public OriginPoint(double x, double y){super(x,y);}

    public void setOwner(WorldElement owner){this.owner=owner;}
    public WorldElement getOwner(){return (this.linkedTo!=null)?this.linkedTo.getOrigin().getOwner():this.owner;}
    public void link(AttachPoint attachPoint){this.linkedTo=attachPoint;}
    public void unlink(){this.linkedTo=null;}
    public AttachPoint getLinkedTo(){return linkedTo;}

    public double getLocalX(){return super.getLocalX();}
    public double getLocalY(){return super.getLocalY();}
    public double getX(){return this.linkedTo!=null?this.linkedTo.getX()+this.linkedTo.getOrigin().getX():this.getLocalX();}
    public double getY(){return this.getLinkedTo()!=null?this.getLinkedTo().getY()+this.getLinkedTo().getOrigin().getY():this.getLocalY();}
    public double getGlobalX(){return (this.linkedTo==null?this.owner.getX()+this.getLocalX():this.linkedTo.getGlobalX());}
    public double getGlobalY(){return (this.linkedTo==null?this.owner.getY()+this.getLocalY():this.linkedTo.getGlobalY());}
}
