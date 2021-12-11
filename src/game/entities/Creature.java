package game.entities;

import game.Camera;
import game.GameScene;
import game.entities.projectiles.Projectile;
import game.environment.Walkable;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Creature extends Entity{
    //status
    private int health;
    protected boolean isGrounded=true;
    private boolean running=false;
    private boolean walking=false;
    private boolean autoRun=false;
    protected boolean freeFall=false;
    protected boolean isInvulnerable=false;

    //time handling
    private long jumpStarted;
    private long lastDashed=0;
    protected long invulnStarted=0;

    //characteristics
    private long jumpDuration;
    protected double dashDistance=0;
    protected long dashDuration=0;
    protected long dashCooldown=1000;
    private int maxHealth;
    protected double walkSpeed;
    protected double runSpeed;
    private int damageOnContact;
    protected long invulnTimer;
    private final int toughness;

    //listeners
    private final ArrayList<DamageTaken> damageListeners=new ArrayList<>();
    protected final ArrayList<Shot> shotListeners=new ArrayList<>();

    public Creature(int x, int y, int width, int height, boolean isFacingRight,
    double walkSpeed, double runSpeed, long jumpDuration, int health, int maxHealth, int toughness,
    int damageOnContact, long invulnTimer, int team, Rectangle2D hitBox, Camera cam, String spritePath){
        super(x, y, width, height, team, isFacingRight, hitBox, cam, "creatures\\"+spritePath);
        this.walkSpeed=walkSpeed;
        this.runSpeed=runSpeed;
        this.jumpDuration=jumpDuration;
        this.health=health;
        this.maxHealth=Math.max(health,maxHealth);
        this.toughness=toughness;
        this.damageOnContact=damageOnContact;
        this.invulnTimer=invulnTimer;
    }

    public void update(long time) {
        //target speed
        this.targetSpeed=(this.facingRight?1:-1)*(this.running?this.runSpeed:this.walking?this.walkSpeed:0);
        this.running=this.autoRun;
        this.walking=false;

        //vertical speed
        this.vY = Math.min(this.vY + .5, 6);
        boolean falling=true;

        //dash handling
        if(this.dashSpeed!=0){
            if(time-this.lastDashed>this.dashDuration){
                this.dashSpeed=0;
            }
        }

        //terrain collision detection
        this.hitAWall=0;
        this.speedUpdate();
        Rectangle2D estHitBox = new Rectangle2D(this.hitBox.getMinX() + vX, this.hitBox.getMinY() + vY, this.hitBox.getWidth(), this.hitBox.getHeight());
        for (Walkable walkable : GameScene.getWalkables()) {
            if (walkable.isBelow(this.hitBox)
                    & walkable.intersects(estHitBox)
                    & this.vY > 0
                    & (!this.freeFall | walkable.isSolid())) {
                if(!this.isGrounded){this.resetFrame(time);}
                this.vY = 0;
                this.dY = (walkable.getHitBox().getMinY() - (this.yOffset + this.hitBoxHeight));
                this.forcedSpeed = walkable.getForcedSpeed();
                falling=false;
            } else if (walkable.isSolid()) {
                if (walkable.isAbove(this.hitBox)
                        & walkable.intersects(estHitBox)) {
                    this.vY = 0;
                    this.dY = walkable.getHitBox().getMaxY() - this.yOffset;
                } else if (walkable.isRight(this.hitBox)
                        & walkable.intersects(estHitBox)) {
                    this.dX = walkable.getHitBox().getMinX() - (this.hitBox.getWidth() + this.xOffset);
                    this.hitAWall=1;
                } else if (walkable.isLeft(this.hitBox)
                        & walkable.intersects(estHitBox)) {
                    this.dX = walkable.getHitBox().getMaxX() - this.xOffset;
                    this.hitAWall=-1;
                }
            }
        }
        if(this.isGrounded&falling){this.resetFrame(time);}
        this.isGrounded=!falling;
        this.freeFall=false;

        super.update(time);

        //invulnerability handling
        this.isInvulnerable = time - this.invulnStarted < this.invulnTimer;

        //hit detection
        if (!this.isInvulnerable){
            //from enemies
            for (Creature creature : GameScene.getCreatures()){
                if (creature.getTeam()!=this.team & this.hitBox.intersects(creature.getHitBox())){
                    this.hurt(creature.getDamageOnContact(),time);
                }
            }
            //from projectiles
            for (Projectile projectile : GameScene.getProjectiles()){
                if (projectile.getTeam()!=this.team & this.hitBox.intersects(projectile.getHitBox())){
                    this.hurt(projectile.getDamage(), time);
                    projectile.pierce(this.toughness);
                }
            }
        }
    }

    public void heal(int amount){this.health=Math.min(this.health+amount,this.maxHealth);}

    public void hurt(int amount, long time){
        this.health-=amount;
        this.invulnStarted=time;
        for(DamageTaken listener:this.damageListeners){listener.onDamageTaken(this.health+amount,this.health);}
        if(this.health<=0){for(Removal listener:this.removalListener){listener.onRemoval();}}
    }

    public void jump(long time){
        if(this.isGrounded){
            this.jumpStarted=time;
            this.vY=-7;
        }else if(time-this.jumpStarted<this.jumpDuration){
            this.vY-=.4;
        }
    }

    public void dash(long time){
        if(time-this.lastDashed>this.dashCooldown){
            if(this.dashDuration<10){
                double displacement=(this.facingRight?1:-1)*this.dashDistance;
                this.dX+=displacement;
                Rectangle2D tpHitBox = new Rectangle2D(this.hitBox.getMinX()+Math.min(displacement,0), this.hitBox.getMinY(), this.hitBox.getWidth()+Math.abs(displacement), this.hitBox.getHeight());
                for (Walkable walkable : GameScene.getWalkables()){
                    if (walkable.isSolid()) {
                        if (walkable.isRight(this.hitBox)
                                & walkable.intersects(tpHitBox)) {
                            this.dX = Math.min(walkable.getHitBox().getMinX() - (this.hitBox.getWidth() + this.xOffset),this.dX);
                            this.hitAWall=1;
                        } else if (walkable.isLeft(this.hitBox)
                                & walkable.intersects(tpHitBox)) {
                            this.dX = Math.max(walkable.getHitBox().getMaxX() - this.xOffset,this.dX);
                            this.hitAWall=-1;
                        }
                    }
                }
            }else{
                this.dashSpeed=(this.facingRight?1:-1)*this.dashDistance*16/this.dashDuration;
            }
            this.lastDashed=time;
        }
    }

    public void walk(){this.walking=!this.running;}

    public void run(){this.running=true;}

    public void autoRun(){this.autoRun=true;}
    public void stopAutoRun(){this.autoRun=false;}
    public void faceRight(){this.facingRight=true;}
    public void faceLeft(){this.facingRight=false;}
    public int getDamageOnContact(){return this.damageOnContact;}
    public void freeFall(){this.freeFall=true;}
    public int getHealth(){return this.health;}
    public int getMaxHealth(){return this.maxHealth;}
    public void addDamageListener(DamageTaken damageListener){this.damageListeners.add(damageListener);}
    public void addShotListener(Shot listener){this.shotListeners.add(listener);}
}
