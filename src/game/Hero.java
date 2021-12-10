package game;

import game.environment.Walkable;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Hero extends AnimatedThing {

    private double vX;
    private double vY=0;
    private int isAttacking=0;
    private int health;
    private double energy=40;
    private long invulnStarted=0;
    private boolean isJumping=false;
    private boolean isGrounded = false;
    private boolean freeFall=false;
    private long jumpStarted=0;
    private final int maxHealth=3;
    private final boolean isCheated;

    public Hero(int x, int y,int spriteWidth,int spriteHeight,int duration,int runningFrames,int height,boolean cheated,String fileName) {
        super(x, y, spriteWidth, spriteHeight, duration,0, fileName);
        this.image.setPreserveRatio(true);
        this.image.setFitHeight(height);
        this.isCheated=cheated;
        this.vX=(cheated?0:3);
        this.maxFrames=new int[]{runningFrames,1,1,runningFrames,1,1,runningFrames};
        this.health=maxHealth;
    }
    public void update(long time, Camera cam, ArrayList<Walkable> terrains){
        super.update(time,cam);

        //vertical speed
        this.vY=Math.min(this.vY+.5,6);
        this.isGrounded=false;

        //terrain collision detection
        for(Walkable terrain:terrains){
            Rectangle2D estHitBox=new Rectangle2D(this.dX+vX,this.dY+vY,this.width,this.height);
            if(terrain.isBelow(this.hitBox)
            & terrain.intersects(estHitBox)
            & this.vY>0
            & (!this.freeFall|terrain.isSolid())){
                this.vY=0;
                this.dY=terrain.getHitBox().getMinY()-this.height;
                this.isGrounded=true;
            } else
            if(terrain.isSolid()){
                if(terrain.isAbove(this.hitBox)
                & terrain.intersects(estHitBox)) {
                    this.vY=0;
                    this.dY=terrain.getHitBox().getMaxY();
                }else
                if(terrain.isRight(this.hitBox)
                & terrain.intersects(estHitBox)) {
                    this.dX=terrain.getHitBox().getMinX()-this.width;
                    this.vX=0;
                }else
                if(terrain.isLeft(this.hitBox)
                & terrain.intersects(estHitBox)) {
                    this.dX = terrain.getHitBox().getMaxX();
                    this.vX = 0;
                }
            }
        }

        //horizontal movement
        this.dX=this.dX+vX;
        this.x=(int)this.dX;

        //vertical movement
        this.dY=this.dY+vY;
        this.y=(int)this.dY;

        //jump handling
        if(this.isJumping) {
            if (isGrounded){
                this.jumpStarted = time;
                this.frame = 0;
                this.vY=-7;
            }
            if (time-this.jumpStarted>300){
                this.stopJumping();
            }
            this.vY -= .4;
        }
        this.hitBox=new Rectangle2D(this.x+25,this.y,this.width-50,this.height);

        //attitude
        if(this.isGrounded){
            this.attitude=0;
        }else if(this.vY<0){
            this.attitude=1;
            this.resetFrame(time);
        }else{
            this.attitude=2;
            this.resetFrame(time);
        }
        if(this.isAttacking>0){this.attitude+=3;}
        if(((time-this.invulnStarted)/150)%2==0&&(time-this.invulnStarted)<1000){this.attitude=6;}
        this.isAttacking-=1;

        //energy regeneration
        if(this.energy<40) {
            this.energy += (this.isCheated?.2:.02);
        }
    }
    public void stopJumping(){
        this.isJumping = false;
    }
}
