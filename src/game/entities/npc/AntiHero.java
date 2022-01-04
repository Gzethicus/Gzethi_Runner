package game.entities.npc;

import game.GameScene;
import game.State;
import game.entities.Creature;
import game.entities.projectiles.LaserProjectile;
import game.entities.projectiles.Projectile;
import game.entities.Shot;
import game.environment.rooms.Room;
import javafx.geometry.Rectangle2D;

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

    public AntiHero(double x, double y, Room room, boolean isFacingRight) {
        super(x, y, room, isFacingRight, 2, 4, 300, 1, 1, 1, 1, 500, 2, new Rectangle2D(x+10,y,56,94), Sprites.ANTIHERO.get());
    }

    public void update(long time){
        Rectangle2D playerHitBox=GameScene.getPlayer().getHitBox();
        this.seesPlayer=playerHitBox.intersects(new Rectangle2D(this.getFacingRight()?this.x-50:this.x-700,0,750,500))
                |(this.seesPlayer&playerHitBox.intersects(new Rectangle2D(this.x-1000,0,2000,500)));
        if(seesPlayer){
            //combat routine
            this.setFacingRight((playerHitBox.getMinX()+playerHitBox.getMaxX())/2-(this.hitBox.getMinX()+this.hitBox.getMaxX())/2>0);
            if(this.vX==this.forcedSpeed){this.shoot(time);}
            if(!this.isShooting&Math.abs((playerHitBox.getMinX()+playerHitBox.getMaxX())/2-(this.hitBox.getMinX()+this.hitBox.getMaxX())/2)<300){
                this.run(!this.getFacingRight());
            }else if(playerHitBox.getMaxY()-this.hitBox.getMaxY()<-100|(playerHitBox.getMaxY()-this.hitBox.getMaxY()<0&this.vY<0)){this.jump(time);}
        }
        else {
            //out of combat routine
            if(time-this.lastTurned>2000+this.nextPauseDuration){
                this.setFacingRight(!this.getFacingRight());
                this.lastTurned=time;
                this.nextPauseDuration=(long)(random()*3000);
            }else if(time-this.lastTurned>2000){
                this.sprite.resetFrame(time);
            }else{this.walk();}
        }
        super.update(time);

        //attack handling
        if(time>this.startedShooting +this.shootCooldown){
            if(this.onCooldown&this.canShoot){
                this.startedShooting =time;
                this.canShoot =false;
                this.sprite.resetFrame(time);
            }else{
                canShoot =true;
            }
            this.onCooldown=false;
        }
        if(time>this.startedShooting +this.shootDuration&this.isShooting){
            this.isShooting =false;
            this.sprite.resetFrame(time);
            Projectile projectile=new LaserProjectile(this.x+(this.getFacingRight()?76:1), this.y+45, this.inRoom, (int)((playerHitBox.getMinX()+playerHitBox.getMaxX())/2),(int)((playerHitBox.getMinY()+playerHitBox.getMaxY())/2),State.RED, this.team);
            for(Shot listener:shotListeners){listener.onShot(projectile);}
        }

        //state
        int animation;
        if(this.isGrounded&this.vX==this.forcedSpeed){
            animation=0;
        }else if(this.isGrounded){
            animation=1;
        }else if(this.vY<0){
            animation=2;
        }else{
            animation=3;
        }
        if(this.isShooting){animation+=4;}
        this.sprite.setDefaultAnimation(animation);
        this.sprite.update(time);
    }

    public void shoot(long time){
        if(!this.onCooldown&this.canShoot){
            this.isShooting =true;
            this.onCooldown=true;
            this.sprite.playAnimation(4,time);
        }
    }
}
