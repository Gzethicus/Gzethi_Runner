package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends ImageView implements Updatable{
    //Constant parameters
    private Image right;
    private Image left;
    private boolean mirror;
    private final int bWidth;
    private final int bHeight;
    private final long[][] durations;
    private final long[] totalDurations;
    private final int[] maxFrame;

    //Animation variables
    private int width;
    private int height;
    private long timeOrigin=0;
    private int defaultAnimation=0;
    private int animation=0;
    private boolean playsDefault=true;
    private long endAnimation=0;
    private int frame=0;
    private boolean changed=false;

    public AnimatedSprite(int width, int height, long[][] durations, String spritePathR, String spritePathL){
        super();
        this.right=new Image("sprites\\"+spritePathR);
        this.left=new Image("sprites\\"+spritePathL);
        this.setImage(this.right);

        this.bWidth=width;
        this.bHeight=height;
        this.width=width;
        this.height=height;
        this.setViewport(new Rectangle2D(0,0,width,height));

        this.durations=durations;
        this.totalDurations=new long[durations.length];
        this.maxFrame=new int[durations.length];
        for(int i=0;i<this.durations.length;i++){
            this.maxFrame[i]=durations[i].length;
            for(int j=0;j<this.durations[i].length;j++){this.totalDurations[i]+=this.durations[i][j];}
        }
    }

    public AnimatedSprite(int width, int height, long[][] durations, String spritePath){
        this(width,height,durations,spritePath,spritePath);
    }

    private AnimatedSprite(int width, int height, long[][] durations, long[] totalDurations, int[] maxFrame, Image right, Image left){
        super();
        this.right=right;
        this.left=left;
        this.setImage(right);

        this.bWidth=width;
        this.bHeight=height;
        this.width=width;
        this.height=height;
        this.setViewport(new Rectangle2D(0,0,width,height));

        this.durations=durations;
        this.totalDurations=totalDurations;
        this.maxFrame=maxFrame;
    }

    public void update(long time){
        //initialisation
        if(timeOrigin==0){this.timeOrigin=time;}

        //actuate current animation, time origin and frame counter
        if(!this.playsDefault){
            if(time>this.endAnimation){
                this.frame=0;
                this.animation=this.defaultAnimation;
                this.playsDefault=true;
                this.changed=true;
            }
        }
        while(time>Math.max(this.timeOrigin+this.durations[this.animation][this.frame],this.durations[this.animation][this.frame])){
            this.timeOrigin+=this.durations[this.animation][this.frame];
            this.frame=(this.frame+1)%this.maxFrame[this.animation];
            this.changed=true;
        }

        //select correct portion of spriteSheet
        this.setViewport(new Rectangle2D((this.bWidth +1)*this.frame,(this.bHeight +1)*this.animation,this.width,this.height));
    }

    public void playAnimation(int animation, long duration, long time){
        this.frame=0;
        this.animation=animation;
        this.timeOrigin=time;
        this.endAnimation=time+duration;
        this.playsDefault=false;
        this.changed=true;
    }
    public void playAnimation(int animation, int loops, long time){playAnimation(animation,loops*this.totalDurations[animation],time);}
    public void playAnimation(int animation, long time){playAnimation(animation, 1, time);}

    public boolean isChanged(){
        boolean changed=this.changed;
        this.changed=false;
        return changed;
    }

    public void setDefaultAnimation(int animation){
        this.defaultAnimation=animation;
        if(this.playsDefault){
            this.animation=animation;
            this.frame=this.frame%this.maxFrame[this.animation];
            this.changed=true;
        }
    }

    public void resetFrame(long time) {
        this.frame=0;
        this.timeOrigin=time;
    }

    public void mirror(){
        Image left=this.left;
        this.left=this.right;
        this.right=left;
        this.mirror^=true;
    }

    public void setFacingRight(boolean facingRight){this.setImage(facingRight?this.right:this.left);}
    public void setWidth(int width){this.width=width;}
    public void setHeight(int height){this.height=height;}
    public AnimatedSprite copy(){return new AnimatedSprite(this.bWidth, this.bHeight, this.durations, this.totalDurations, this.maxFrame, this.right, this.left);}
    public boolean playsDefault(){return this.playsDefault;}
    public void setMirror(boolean mirror){if(this.mirror!=mirror)this.mirror();}
    public int getAnim(){return this.animation;}
    public int getFrame(){return this.frame;}
}
