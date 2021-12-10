package game.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GUIElement extends Parent {
    protected final int bWidth;
    protected final int bHeight;
    protected final ImageView iv;
    private final int[] maxFrames;
    private final int[] durations;
    protected int state;
    protected int frame;
    private long frameStart;

    public GUIElement(int x, int y, int width, int height, String spriteName){
        this.bWidth=width;
        this.bHeight=height;
        int[] ph={1};
        this.maxFrames=ph;
        this.durations=ph;
        this.frame=0;
        this.state=0;
        this.iv=new ImageView(new Image("sprites\\GUI\\"+spriteName));
        this.iv.setX(x);
        this.iv.setY(y);
        this.iv.setViewport(new Rectangle2D(0,0,width,height));
        this.getChildren().add(this.iv);
    }

    public void update(long time){
        if(time>this.frameStart+this.durations[this.state]){
            this.frame=this.frame+1%this.maxFrames[this.state];
            this.frameStart=time;
            this.iv.setViewport(new Rectangle2D(this.bWidth*this.frame,this.bHeight*this.state,this.bWidth,this.bHeight));
        }
    }

    public void setState(int state, boolean keepFrame){
        this.state=state;
        this.iv.setViewport(new Rectangle2D(keepFrame?this.bWidth*this.frame:0,this.bHeight*this.state,this.bWidth,this.bHeight));
    }

    public void setState(int state){this.setState(state,false);}
}
