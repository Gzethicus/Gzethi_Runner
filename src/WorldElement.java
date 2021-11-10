public class WorldElement extends StaticThing{
    protected int relX;
    protected int relY;

    public WorldElement(int x, int y, int width, int height, String fileName){
        super(x,y,width,height,fileName);
        this.relX=x;
        this.relY=y;
    }

    public void update(Camera cam){
        this.relX = this.x - cam.getX();
        this.image.setX(this.relX);
        this.relY = this.y - cam.getY();
        this.image.setY(this.relY);
    }
}
