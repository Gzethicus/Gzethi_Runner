package game.entities.npc;

import game.Camera;
import game.GameScene;
import game.entities.Creature;
import game.entities.projectiles.LaserProjectile;
import game.entities.projectiles.Projectile;
import game.entities.Shot;
import javafx.geometry.Rectangle2D;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Math.random;

public class AntiHero extends Creature {
    //status
    private boolean onCooldown=false;
    private boolean isShooting=false;
    private boolean canShoot=true;
    private boolean seesPlayer=false;

    //time handling
    private long startedShooting =0;
    private long lastTurned=0;
    private long nextPauseDuration=3000;

    //characteristics
    private long shootCooldown =2000;
    private long shootDuration =700;

    public AntiHero(int x, int y, boolean isFacingRight, Camera cam) {
        super(x, y, 77, 100, isFacingRight, 2, 4, 300, 1, 1, 1, 1, 500, 2, new Rectangle2D(x+10,y,56,94), cam, "antiHero.png");
        int[]ph1={1,6,1,1,4,6,1,1,6};
        long[]ph2={MAX_VALUE,150,MAX_VALUE,MAX_VALUE,200,150,MAX_VALUE,MAX_VALUE,150};
        this.maxFrame=ph1;
        this.durations=ph2;
    }

    public void update(long time){
        Rectangle2D playerHitBox=GameScene.getPlayer().getHitBox();
        this.seesPlayer=playerHitBox.intersects(new Rectangle2D(this.facingRight?this.x-50:this.x-700,0,750,500))
                |(this.seesPlayer&playerHitBox.intersects(new Rectangle2D(this.x-1000,0,2000,500)));
        if(seesPlayer){
            //combat routine
            this.facingRight=(playerHitBox.getMinX()+playerHitBox.getMaxX())/2-(this.hitBox.getMinX()+this.hitBox.getMaxX())/2>0;
            if(this.vX==this.forcedSpeed){this.shoot();}
            if(!this.isShooting&Math.abs((playerHitBox.getMinX()+playerHitBox.getMaxX())/2-(this.hitBox.getMinX()+this.hitBox.getMaxX())/2)<300){
                this.facingRight^=true;
                this.run();
            }else if(playerHitBox.getMaxY()-this.hitBox.getMaxY()<-100|(playerHitBox.getMaxY()-this.hitBox.getMaxY()<0&this.vY<0)){this.jump(time);}
        }
        else {
            //out of combat routine
            if(time-this.lastTurned>2000+this.nextPauseDuration){
                this.facingRight^=true;
                this.lastTurned=time;
                this.nextPauseDuration=(long)(random()*3000);
            }else if(time-this.lastTurned>2000){
                this.resetFrame(time);
            }else{this.walk();}
        }
        super.update(time);

        //attack handling
        if(time>this.startedShooting +this.shootCooldown){
            if(this.onCooldown&this.canShoot){
                this.startedShooting =time;
                this.canShoot =false;
                this.resetFrame(time);
            }else{
                canShoot =true;
            }
            this.onCooldown=false;
        }
        if(time>this.startedShooting +this.shootDuration&this.isShooting){
            this.isShooting =false;
            this.resetFrame(time);
            Projectile projectile=new LaserProjectile(this.x+(facingRight?76:1), this.y+45, (int)((playerHitBox.getMinX()+playerHitBox.getMaxX())/2),(int)((playerHitBox.getMinY()+playerHitBox.getMaxY())/2),1, this.team,this.cam);
            for(Shot listener:shotListeners){listener.onShot(projectile);}
        }

        //state
        if(this.isGrounded&this.vX==this.forcedSpeed){
            this.state=0;
        }else if(this.isGrounded){
            this.state=1;
        }else if(this.vY<0){
            this.state=2;
        }else{
            this.state=3;
        }
        if(this.isShooting){this.state+=4;}
    }

    public void shoot(){
        if(!this.onCooldown&this.canShoot){
            this.isShooting =true;
            this.onCooldown=true;
        }
    }
}
