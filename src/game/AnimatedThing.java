package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedThing {
    protected int x;
    protected double dX;
    protected int y;
    protected double dY;
    protected int relX;
    protected int relY;
    protected int width;
    protected int height;
    protected int attitude;
    protected int frame;
    private long timeOrigin;
    protected int duration;
    protected int[] maxFrames;
    protected final ImageView image;
    protected Rectangle2D hitBox;

    public AnimatedThing(int x, int y, int width, int height,int duration,int firstFrame, String fileName){
        this.x=x;
        this.y=y;
        this.dX=this.x;
        this.dY=this.y;
        this.width=width;
        this.height=height;
        this.hitBox=new Rectangle2D(this.x, this.y, this.width, this.height);
        this.attitude=firstFrame;
        this.frame=0;
        this.duration=duration;
        Image img = new Image(fileName);
        this.image =new ImageView(img);
        this.image.setViewport(new Rectangle2D(this.width*this.frame, this.height*this.attitude, this.width-1, this.height));
        this.image.setX(x);
        this.image.setY(y);
    }

    public boolean update(long time, Camera cam){
        if(time>=this.timeOrigin+2.*duration){this.resetFrame(time);}
        if(time>=this.timeOrigin+duration){
            this.frame=(this.frame+1)%this.maxFrames[this.attitude];
            this.timeOrigin+=duration;
            if(this.timeOrigin==duration){
                this.timeOrigin=time;
            }
        }
        this.image.setViewport(new Rectangle2D(this.width*this.frame, this.height*this.attitude, this.width, this.height));
        this.relX=this.x-cam.getX();
        this.image.setX(this.relX);
        this.relY=this.y-cam.getY();
        this.image.setY(this.relY);
        return false;
    }

    protected void resetFrame(long time){
        this.frame=0;
        this.timeOrigin=time;
    }

    public ImageView getImage(){return this.image;}

    public Rectangle2D getHitBox(){return this.hitBox;}

    public int getX(){return this.x;}

    public int getY(){return this.y;}

    public int getWidth() {return this.width;}

    public int getHeight() {return this.height;}
}
