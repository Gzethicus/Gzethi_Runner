public class Room extends StaticThing{
    protected int relX;
    protected int relY;

    public Room(Room neighbour,char direction, int width, int height, String fileName){
        super(0,0,width,height,fileName);
        if(neighbour!=null){
            this.x=neighbour.getX()+neighbour.getWidth()*(((direction=='r')?1:0)-((direction=='l')?1:0));
            this.y=neighbour.getY()+neighbour.getHeight()*(((direction=='d')?1:0)-((direction=='u')?1:0));
        }
        this.relX=this.x;
        this.relY=this.y;
    }

    public void update(Camera cam){
        this.relX = this.x - cam.getX();
        this.image.setX(this.relX);
        this.relY = this.y - cam.getY();
        this.image.setY(this.relY);
    }
}
