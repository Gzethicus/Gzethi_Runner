import javafx.geometry.Rectangle2D;

public class Projectile extends AnimatedThing{
    private final double vX;
    private boolean deletable=false;

    public Projectile(int x,int y,double vX,int type){
        super(x,y,23,8,1000,type,"sprites\\projectile.png");
        this.dX=x;
        this.vX=vX;
        this.maxFrames=new int[]{1,1};
    }

    public boolean update(long time, Camera cam) {
        super.update(time, cam);
        this.hitBox=new Rectangle2D(this.x,this.y,this.width,this.height);

        //horizontal movement
        this.dX = this.dX + vX;
        this.x = (int) this.dX;
        return this.relX>1600||this.relX<-this.width||this.deletable;
    }
    public void requestDelete(){this.deletable=true;}
    public boolean isDeletable(){return this.deletable;}
}
