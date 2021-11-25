package game;

import javafx.geometry.Rectangle2D;

public class Terrain extends StaticThing {

    private int relX;
    private int relY;
    private final Rectangle2D hitBox;
    private final boolean isSolid;

    public Terrain(int x, int y,int width,int height,boolean isSolid, String fileName){
        super(x,y,width,height,fileName);
        this.hitBox=new Rectangle2D(this.x, this.y, this.width, this.height);
        this.isSolid=isSolid;
    }

    public void update(Camera cam){
        this.relX = this.x - cam.getX();
        this.image.setX(this.relX);
        this.relY = this.y - cam.getY();
        this.image.setY(this.relY);
    }

    public Rectangle2D getHitBox(){return this.hitBox;}

    public boolean isSolid() {
        return isSolid;
    }
}
