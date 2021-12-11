package game.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GUIElement extends Parent {
    private final int bWidth;
    private final int bHeight;
    protected int width;
    protected int height;
    protected final ImageView iv;
    protected int[] maxFrame;
    protected long[] durations;
    protected int state;
    protected int frame;
    private long timeOrigin;

    public GUIElement(int x, int y, int width, int height, String spriteName){
        this.bWidth=width;
        this.bHeight=height;
        this.width=width;
        this.height=height;
        this.frame=0;
        this.state=0;
        this.iv=new ImageView(new Image("sprites\\GUI\\"+spriteName));
        this.iv.setX(x);
        this.iv.setY(y);
        this.iv.setViewport(new Rectangle2D(0,0,width,height));
        this.getChildren().add(this.iv);
    }

    public void update(long time){
        if(time>this.timeOrigin +this.durations[this.state]){
            this.frame=this.frame+1%this.maxFrame[this.state];
            this.timeOrigin =time;
            this.iv.setViewport(new Rectangle2D(this.bWidth*this.frame,this.bHeight*this.state,this.width,this.height));
        }
        if(this.frame>this.maxFrame[this.state]){this.resetFrame(time);}
        this.iv.setViewport(new Rectangle2D(this.frame*this.bWidth,this.state*this.bHeight,this.width,this.height));
        if(time>Math.max(this.timeOrigin+this.durations[this.state],this.durations[this.state])){
            this.timeOrigin+=this.durations[this.state];
            this.frame=(this.frame+1)%this.maxFrame[this.state];
            if(this.timeOrigin==this.durations[this.state]){this.resetFrame(time);}
        }
    }

    public void setState(int state, boolean keepFrame){
        this.state=state;
        this.frame=keepFrame?this.frame:0;
        this.iv.setViewport(new Rectangle2D(this.bWidth*this.frame,this.bHeight*this.state,this.bWidth,this.bHeight));
    }

    public void setState(int state){this.setState(state,false);}

    private void resetFrame(long time){
        this.frame=0;
        this.timeOrigin=time;
    }
}
