package game.entities.assembly;

public class OriginPoint{
    private final double x;
    private final double y;
    protected AttachPoint linkedTo;
    
    public OriginPoint(double x, double y){
        this.x=x;
        this.y=y;
    }

    public void link(AttachPoint attachPoint){this.linkedTo=attachPoint;}
    public void unlink(){this.linkedTo=null;}
    public AttachPoint getLinkedTo(){return linkedTo;}
    public double getLocalX(){return this.x;}
    public double getLocalY(){return this.y;}
    public double getX(){return this.getLinkedTo()!=null?this.getLinkedTo().getX()+this.getLinkedTo().getOrigin().getX():this.x;}
    public double getY(){return this.getLinkedTo()!=null?this.getLinkedTo().getY()+this.getLinkedTo().getOrigin().getY():this.y;}

}
