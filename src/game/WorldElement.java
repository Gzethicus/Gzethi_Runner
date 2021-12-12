package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WorldElement extends Parent {
    protected int x;
    protected int y;
    private final int width;
    private final int height;
    protected int frame=0;
    protected int state=0;
    protected long timeOrigin=0;
    protected long[] durations;
    protected int[] maxFrame;
    protected final ImageView iv;
    protected final Camera cam;

    public WorldElement(int x, int y, int width, int height, Camera cam, String spritePath){
        super();
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.cam=cam;
        this.iv=new ImageView(new Image("sprites\\"+spritePath));
        this.iv.setViewport(new Rectangle2D(0,0,width,height));
        this.iv.setX(this.x);
        this.iv.setY(this.y);
        this.getChildren().add(this.iv);
    }

    public void update(long time){
        this.iv.setX(this.x);
        this.iv.setY(this.y);
        if(this.frame>this.maxFrame[this.state]){this.resetFrame(time);}
        this.iv.setViewport(new Rectangle2D(this.frame*this.width,this.state*this.height,this.width,this.height));
        if(time>Math.max(this.timeOrigin+this.durations[this.state],this.durations[this.state])){
            this.timeOrigin+=this.durations[this.state];
            this.frame=(this.frame+1)%this.maxFrame[this.state];
            if(this.timeOrigin==this.durations[this.state]){this.resetFrame(time);}
        }
    }

    protected void resetFrame(long time){
        this.frame=0;
        this.timeOrigin=time;
    }
}
