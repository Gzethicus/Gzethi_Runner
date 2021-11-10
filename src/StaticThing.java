import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StaticThing {
    protected int x;
    protected int y;
    protected int width;
    private final int bWidth;
    protected int height;
    private int frame=0;
    protected final ImageView image;
    protected Rectangle2D hitBox;

    public StaticThing(int x, int y,int width,int height, String fileName) {
        this.x=x;
        this.y=y;
        this.width=width;
        this.bWidth=width;
        this.height=height;
        this.hitBox=new Rectangle2D(this.x, this.y, this.width, this.height);
        Image img = new Image(fileName);
        this.image =new ImageView(img);
        this.image.setViewport(new Rectangle2D(this.bWidth*this.frame, 0, this.width, this.height));
        this.image.setX(x);
        this.image.setY(y);
    }

    public ImageView getImage(){
        return this.image;
    }

    public void setFrame(int frame){
        this.frame=frame;
        this.image.setViewport(new Rectangle2D(this.bWidth*this.frame, 0, this.width, this.height));
    }

    public void setWidth(int width) {
        this.width = width;
        this.image.setViewport(new Rectangle2D(this.bWidth*this.frame, 0, this.width, this.height));
    }

    public Rectangle2D getHitBox(){return this.hitBox;}

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}
